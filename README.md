# CountDownView
Android倒计时

CountDownView:
view.startTime(lastTime); //开始倒计时
view.setOnTimeoutListener(new CountDownView.OnTimeOutListener() {
     @Override
     public void onFinish() {
        Toast.makeText(MainActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();
     }
});
view.cancel();//销毁倒计时

MyClockView:
view.startTime(lastTime); //开始倒计时
view.setOnTimeoutListener(new CountDownView.OnTimeOutListener() {
     @Override
     public void onFinish() {
        Toast.makeText(MainActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();
     }
});
view.cancel();//销毁倒计时

![Image text](https://github.com/thkcheng/CountDownView/blob/bb1274655c2cb833b8c39ec631a2f28297ec3405/Untitled.gif)
