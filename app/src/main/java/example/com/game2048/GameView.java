package example.com.game2048;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liule on 2017/4/7.
 */

public class GameView extends GridLayout {
    //构造方法
    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initGameView();
    }

    //初始化游戏并监视操作
    private void initGameView(){
        setColumnCount(4);
        //设置背景
        setBackgroundColor(getResources().getColor(R.color.gameview_background));
        //监听游戏操作
        setOnTouchListener(new View.OnTouchListener(){
            private float startx,starty,offsetx,offsety;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startx = motionEvent.getX();
                        starty = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetx = motionEvent.getX() - startx;
                        offsety = motionEvent.getY() - starty;
                        if(Math.abs(offsetx) > Math.abs(offsety)){
                            if(offsetx > 5){
                                swipeRight();
                            }
                            else if(offsetx < -5){
                                swipeLeft();
                            }
                        }else{
                            if(offsety > 5){
                                swipeDown();
                            }else if(offsety < -5){
                                swipeUp();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    //屏幕变化是运行。即游戏一开始运行
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w,h) - 10) / 4;
        addCard(cardWidth,cardWidth);
        Log.d("test","adsa");
        startGame();
    }

    //开始游戏时的一些初始化操作
    private void startGame(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                cardMap[j][i].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }
    //map存放空的片的位置
    Card cardMap[][] = new Card[4][4];
    //添加卡片方法
    private void addCard(int length,int width){
        Card c;
        int count = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                c = new Card(getContext());
                c.setNum(count++);
                cardMap[i][j] = c;
                addView(c,length,width);
            }
        }
    }

    //保存空位置对应的点
    private List<Point> emptypoints = new ArrayList<Point>();

    private void addRandomNum(){
        emptypoints.clear();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(cardMap[i][j].getNum() <= 0){
                    emptypoints.add(new Point(i,j));
                }
            }
        }
        Point p = emptypoints.remove((int)(Math.random()*emptypoints.size()));
        cardMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }

    private void swipeUp(){
        boolean add = false;
        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 4; i++){
                if(cardMap[i][j].getNum() <= 0)
                    continue;
                int i1 = i - 1;
                while(i1 >= 0 && (cardMap[i1][j].getNum() <= 0))
                    i1--;
                if(i1 >= 0){
                    if(cardMap[i1][j].equals(cardMap[i][j])) {
                        cardMap[i1][j].setNum(cardMap[i][j].getNum() * 2);
                        cardMap[i][j].setNum(0);
                        MainActivity.getMainActivity().addScore(cardMap[i1][j].getNum());
                        add = true;
                    }
                }
                i1++;
                if(i1 != i){
                    cardMap[i1][j].setNum(cardMap[i][j].getNum());
                    cardMap[i][j].setNum(0);
                    add = true;
                }
            }
        }
        if(add){
            addRandomNum();
            checkGameOver();
        }
    }

    private void swipeDown(){
        boolean add = false;
        for(int j = 0; j < 4; j++){
            for(int i = 3; i >= 0; i--){
                if(cardMap[i][j].getNum() <= 0)
                    continue;
                int i1 = i + 1;
                while(i1 <= 3 && cardMap[i1][j].getNum() <= 0)
                    i1++;
                if(i1 <= 3){
                    if(cardMap[i1][j].equals(cardMap[i][j])){
                        cardMap[i1][j].setNum(cardMap[i][j].getNum() * 2);
                        cardMap[i][j].setNum(0);
                        MainActivity.getMainActivity().addScore(cardMap[i1][j].getNum());
                        add = true;
                    }
                }
                i1--;
                if(i1 != i){
                    cardMap[i1][j].setNum(cardMap[i][j].getNum());
                    cardMap[i][j].setNum(0);
                    add = true;
                }
            }
        }
        if(add){
            addRandomNum();
            checkGameOver();
        }
    }

    private void swipeLeft(){
        boolean add = false;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(cardMap[i][j].getNum() <= 0)
                    continue;
                int j1 = j - 1;
                while(j1 >= 0 && cardMap[i][j1].getNum() <= 0)
                    j1--;
                if(j1 >= 0){
                    if(cardMap[i][j1].equals(cardMap[i][j])){
                        cardMap[i][j1].setNum(cardMap[i][j].getNum() * 2);
                        MainActivity.getMainActivity().addScore(cardMap[i][j1].getNum());
                        cardMap[i][j].setNum(0);
                        add = true;
                    }
                }
                j1++;
                if(j1 != j){
                    cardMap[i][j1].setNum(cardMap[i][j].getNum());
                    cardMap[i][j].setNum(0);
                    add = true;
                }
            }
        }
        if(add){
            addRandomNum();
            checkGameOver();
        }
    }

    private void swipeRight(){
        boolean add = false;
        for(int i = 0; i < 4; i++){
            for(int j = 3; j >= 0; j--){
                if(cardMap[i][j].getNum() <= 0)
                    continue;
                int j1 = j + 1;
                while(j1 <= 3 && cardMap[i][j1].getNum() <= 0)
                    j1++;
                if(j1 <= 3){
                    if(cardMap[i][j1].equals(cardMap[i][j])){
                        cardMap[i][j1].setNum(cardMap[i][j].getNum() * 2);
                        cardMap[i][j].setNum(0);
                        MainActivity.getMainActivity().addScore(cardMap[i][j1].getNum());
                        add = true;
                    }
                }
                j1--;
                if(j1 != j){
                    cardMap[i][j1].setNum(cardMap[i][j].getNum());
                    cardMap[i][j].setNum(0);
                    add = true;
                }
            }
        }
        if(add){
            addRandomNum();
            checkGameOver();
        }
    }

    private void checkGameOver(){
        boolean gameover = true;
        ALL:
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if((emptypoints.size() > 0)||
                        (i >= 1 && cardMap[i - 1][j].equals(cardMap[i][j])) ||
                        (i <= 2 && cardMap[i + 1][j].equals(cardMap[i][j])) ||
                        (j >= 1 && cardMap[i][j - 1].equals(cardMap[i][j])) ||
                        (j <= 2 && cardMap[i][j + 1].equals(cardMap[i][j]))){
                    gameover = false;
                    break ALL;
                }
            }
        }
        if(gameover){
            AlertDialog.Builder  builder=  new AlertDialog.Builder(getContext());
                    builder.setTitle("GameOver");
                    builder.setMessage("游戏结束");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startGame();
                            MainActivity.getMainActivity().clearScore();
                        }
                    });
                    builder.show();
        }
    }
}
