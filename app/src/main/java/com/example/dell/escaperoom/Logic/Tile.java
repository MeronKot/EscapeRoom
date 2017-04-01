package com.example.dell.escaperoom.Logic;

import android.content.Context;
import android.widget.ImageButton;
import com.example.dell.escaperoom.R;

/**
 * Created by yaelgersh on 17/03/2017.
 */

public class Tile extends android.support.v7.widget.AppCompatImageButton {

    private int value;

    public static  int[] btnImg = {R.drawable.p0, R.drawable.p1, R.drawable.p2,
                                    R.drawable.p3, R.drawable.p4, R.drawable.p5,
                                    R.drawable.p6, R.drawable.p7, R.drawable.p8};

    public Tile(Context context, int value) {
        super(context);
        this.value = value;
        this.setBackgroundResource(btnImg[value]);
    }



    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        setBackgroundResource(btnImg[value]);
        this.value = value;
    }
}
