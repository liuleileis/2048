package example.com.game2048;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.widget.TextViewCompat;
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
        //设置字体
        gamepattern = (TextView)findViewById(R.id.GamePattern_name);
        Typeface font = Typeface.createFromAsset(getAssets(),"Fonts/华康少女字体.ttf");
        gamepattern.setTypeface(font);
        survival = (Button)findViewById(R.id.gamepattern_survival);
        time = (Button)findViewById(R.id.gamepattern_time);
        //事件监听
        survival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(GamePattern.this,MainActivity.class);
                GamePattern.this.startActivity(next);
//                GamePattern.this.finish();
            }
        });
    }
}
