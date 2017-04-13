package example.com.game2048;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView tvscore;
    private GridLayout layout = null;
    private static MainActivity mainActivity = null;
    public MainActivity(){
        mainActivity = this;
    }
    private int score = 0;
    //清零上次游戏的数据
    public void clear(){
        score = 0;
        if(getIntent().getStringExtra("pattern").equals("time")){
            countdowntimer.cancel();
            countTime();
        }
        showScore();
    }
    //显示得分
    public void showScore(){
        tvscore.setText(score+"");
    }
    //加分
    public void addScore(int score){
        this.score += score;
        showScore();
    }
    //返回MainActivity,便于全局使用
    public static MainActivity getMainActivity() {
        return mainActivity;
    }
    //监听返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(getIntent().getStringExtra("pattern").equals("time"))
                countdowntimer.cancel();
            MainActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    //计时器
    private CountDownTimer countdowntimer = null;
    //intent及其信息
    private Intent intent = null;
    private String message = null;
    //创建的显示时间的textview
    private TextView time = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //获取游戏模式
        intent = getIntent();
        String message = intent.getStringExtra("pattern");
        //判断游戏模式,动态添加控件
        tvscore = (TextView)findViewById(R.id.tvscore);
        showScore();
        layout = (GridLayout) findViewById(R.id.game_state);
        layout.setColumnCount(2);
        //判断游戏模式,决定是否添加时间
        if(message.equals("time")) {
            time = new TextView(this);
            time.setText("time: ");
            time.setTextSize(32);
            countTime();
            layout.addView(time);
        }
    }
    //计时
    private void countTime(){
        countdowntimer = new CountDownTimer(122000,1000) {
            private int count = 0;
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText("time: "+count);
                count++;
            }

            @Override
            public void onFinish() {
                AlertDialog.Builder  builder=  new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("GameOver");
                builder.setCancelable(false);
                builder.setMessage("Score:"+score);
                builder.setPositiveButton("重来", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
            }
        }.start();
    }
}