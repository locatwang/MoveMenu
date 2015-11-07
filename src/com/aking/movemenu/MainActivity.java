package com.aking.movemenu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.*;

import javax.security.auth.PrivateCredentialPermission;
import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private List<MovePoint> listPoint = new ArrayList<MovePoint>();
    private List<Integer> resList = new ArrayList<Integer>();
    private List<ImageView> listImage = new ArrayList<ImageView>();
    private int iconCount = 6;
    private float density;
    private RelativeLayout reContainer;
    private LinearLayout bgLogoImage;
    private TextView helpDescTv;
    private boolean isExpand;
    private Button btnHanler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        density = metric.density;
        assignViews();
        getMovePoint();
        initMenuImageview();

    }

    private void getMovePoint() {
        //当在一个视图树中的焦点状态发生改变时，所要调用的回调函数的接口类
        // at method onCreate all widget width height is 0
        ViewTreeObserver vto = reContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initMovePoint(reContainer.getWidth(), reContainer.getHeight());
            }
        });
    }

    private void assignViews() {
        reContainer = (RelativeLayout) findViewById(R.id.reContainer);
        bgLogoImage = (LinearLayout) findViewById(R.id.bg_logo_image);
        helpDescTv = (TextView) findViewById(R.id.help_desc_tv);
        btnHanler = (Button)findViewById(R.id.btn_hanler);
    }


    /**
     * init imageview
     */
    private void initMenuImageview() {
        resList.add(R.drawable.ic_launcher);
        resList.add(R.drawable.ic_launcher);
        resList.add(R.drawable.ic_launcher);
        resList.add(R.drawable.ic_launcher);
        resList.add(R.drawable.ic_launcher);
        resList.add(R.drawable.ic_launcher);
        for (int i = 0; i < iconCount; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setOnClickListener(new MenuOnclickListener(i));
            imageView.setImageResource(resList.get(i));
            imageView.setX(0);
            imageView.setY(2500);// 一个不好的方案 把控件防止屏幕的最下方开外屏幕上不显示 who can change it
            listImage.add(imageView);
            reContainer.addView(imageView);
        }


    }


    /**
     * @param width  父容器的 宽度
     * @param height 父容器的 高度
     *               根据父容器 来计算每个图片按钮的位置
     */
    private void initMovePoint(int width, int height) {
        int rowNum = 3;// 一列有几个
        int x = 0;
        int y = 0;
        int distance = (width - 3 * listImage.get(0).getWidth()) / 4;// imageview直接的间隔
        for (int i = 0; i < iconCount; i++) {
            int spaceNum = i % rowNum;
            int lineNum = i / rowNum;
            // 计算x轴坐标
            x = (spaceNum + 1) * distance + spaceNum * listImage.get(spaceNum).getWidth();
            // 第一排的Y坐标 5*density 多加间隔
            y = bgLogoImage.getHeight() + helpDescTv.getHeight() + (int) ((lineNum + 1) * 20 * density) + lineNum * listImage.get(i).getHeight();
            MovePoint point = new MovePoint(x, x, height, y);
            listPoint.add(point);
        }
    }


    class MenuOnclickListener implements View.OnClickListener {

        private int position;


        public MenuOnclickListener(int position) {
            this.position = position;
        }

        public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click the position:" + position, Toast.LENGTH_SHORT).show();
        }
    }

    private void startAnimator() {
        for (int i = 0; i < iconCount; i++) {
            listImage.get(i).setClickable(false);//移动期间不让点击
            MovePoint point = listPoint.get(i);
            ObjectAnimator animatorX = ObjectAnimator.ofFloat(listImage.get(i), "translationX", point.fromX, point.getToX());
            final ObjectAnimator animatorYD = ObjectAnimator.ofFloat(listImage.get(i),
                    "translationY", point.getToY() - 3 * density, point.getToY());
            // 需要向上再提一段距离 方便下落动画
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(listImage.get(i),
                    "translationY", point.getFromY(), point.getToY() - 3 * density);

            AnimatorSet animSet = new AnimatorSet();
            animSet.setDuration(500);
            animSet.playTogether(animatorX, animatorY);
            animSet.setStartDelay(i * 50);
            animSet.start();
            animSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isExpand = true;
                    // 按钮回落动画
                    animatorYD.setDuration(100);
                    animatorYD.start();
                    for (int j = 0; j < iconCount; j++) {
                        listImage.get(j).setClickable(true);// 移动结束允许点击
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }
            });
        }

    }

    /**
     * 【关闭按钮动画】
     * startAnimator
     *
     * @author Aking
     * @date 2015-5-7 下午2:33:24
     */
    public void closeAnimator() {

        for (int i = 0; i < iconCount; i++) {
            listImage.get(i).setClickable(false);// 移动过程中不允许点击
            MovePoint point = listPoint.get(i);
            ObjectAnimator animatorX = ObjectAnimator.ofFloat(listImage.get(i),
                    "translationX", point.getToX(), point.getFromX());
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(listImage.get(i),
                    "translationY", point.getToY(), point.getFromY());
            AnimatorSet animSet = new AnimatorSet();
            animSet.setDuration(450);
            animSet.play(animatorX).with(animatorY);
            animSet.setStartDelay(i * 50);
            animSet.start();
            animSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    isExpand = false;
                }
                @Override
                public void onAnimationCancel(Animator animation) {

                }
                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

    public void handleAnimtiontor(View view) {
        if (!isExpand) {
            startAnimator();
            btnHanler.setText("CLOSR");
        } else {
            closeAnimator();
            btnHanler.setText("OPEN");
        }

    }

}
