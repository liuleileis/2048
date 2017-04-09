package example.com.game2048;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvscore;
    private static MainActivity mainActivity = null;
    public MainActivity(){
        mainActivity = this;
    }
    private int score = 0;
    public void clearScore(){
        score = 0;
        showScore();
    }
    public void showScore(){
        tvscore.setText(score+"");
    }
    public void addScore(int score){
        this.score += score;
        showScore();
    }
    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        tvscore = (TextView)findViewById(R.id.tvscore);
        showScore();
    }

}
