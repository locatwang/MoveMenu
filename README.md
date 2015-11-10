# MoveMenu
从下向上移动的一组按钮 类似新浪微博的
有些地方不合适那样写 希望大家一起给补起来
![imageview](https://github.com/locatwang/MoveMenu/raw/master/descPic/move.gif)


使用属性动画来做移动

使用 ViewTreeObserver 来计算 按钮的坐标，（如果谁有更好的方法可以提出大家学习）
    ViewTreeObserver vto = reContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initMovePoint(reContainer.getWidth(), reContainer.getHeight());
            }
        });


