package example.com.game2048;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class Welcome extends AppCompatActivity {
    private Drawable welcome_image = null;
    private ImageView imageview = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        welcome_image = getDrawable(R.drawable.welcome_page);
        imageview = (ImageView) findViewById(R.id.welcome);
        imageview.setBackground(welcome_image);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(Welcome.this, MainActivity.class);
                Welcome.this.startActivity(mainIntent);
                Welcome.this.finish();
            }
        }, 3000);
    }
}
