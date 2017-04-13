package example.com.game2048;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by liule on 2017/4/7.
 */

public class Card extends FrameLayout {
    //游戏中的每一个方框都是一个FrameLayout加一个Textview显示数字。label为显示数字的Textview
    private TextView label;
    //card的构造方法
    public Card(@NonNull Context context) {
        super(context);
        //创建textview
        label = new TextView(getContext());
        //设置textview文字大小
        label.setTextSize(32);
        //textview在frameLayou中居中
        label.setGravity(Gravity.CENTER);
        //textview背景颜色
        label.setBackgroundColor(getResources().getColor(R.color.Card_color));
        //textview的属性
        LayoutParams lp = new LayoutParams(-1,-1);
        //textview的边框
        lp.setMargins(10,10,0,0);
        //把textview加入到FrameLayout
        addView(label,lp);
        //初始化textview的文字
        setNum(0);
    }

    //该card的num，以及num的get set方法
    private int num = 0;
    public int getNum(){
        return this.num;
    }

    public void setNum(int num){
        this.num = num;
        if(num <= 0){
            label.setText("");
        }else{
            label.setText(num+"");
        }

    }
    //判断传入的card的num和该card的num是否相同。游戏中遇到相同时做合并
    public boolean equals(Card card){
        return getNum() == card.getNum();
    }
}
