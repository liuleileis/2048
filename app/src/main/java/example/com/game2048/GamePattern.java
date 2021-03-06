package example.com.game2048;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GamePattern extends AppCompatActivity {
    private TextView gamepattern = null;
    private Button survival = null;
    private Button time = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game_pattern);
        survival = (Button)findViewById(R.id.gamepattern_survival);
        time = (Button)findViewById(R.id.gamepattern_time);
        //事件监听
        time.setOnClickListener(new ButtonListener());
        survival.setOnClickListener(new ButtonListener());

    }
    //监视button,判断游戏模式
    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent next = new Intent(GamePattern.this,MainActivity.class);
            switch (v.getId()){
                case R.id.gamepattern_survival:
                    next.putExtra("pattern","survival");
                    break;
                case R.id.gamepattern_time:
                    next.putExtra("pattern","time");
            }
            GamePattern.this.startActivity(next);
//            GamePattern.this.finish();
        }
    }
}
