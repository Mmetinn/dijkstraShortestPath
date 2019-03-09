package com.example.mmetin.shortestpath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DrawView extends View {
    Paint paint = new Paint();
    int Sx,Sy,Ex,Ey;
    boolean ilkmi;

    public DrawView(Context context,int Sx,int Sy,int Ex,int Ey,boolean ilkmi) {
        super(context);
        this.Sx=Sx;
        this.Sy=Sy;
        this.Ex=Ex;
        this.Ey=Ey;
        this.ilkmi=ilkmi;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(ilkmi){
            super.onDraw(canvas);
            paint.setColor(getResources().getColor(R.color.select_color));
            paint.setStrokeWidth(10);
            canvas.drawLine(Sx, Sy, Ex, Ey, paint);
        }else{
            super.onDraw(canvas);
            paint.setColor(getResources().getColor(R.color.select_color_2));
            paint.setStrokeWidth(10);

            canvas.drawLine(Sx, Sy, Ex, Ey, paint);
        }


    }
   /* Paint paint = new Paint();

    private void init() {
        paint.setColor(Color.BLACK);
    }

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawLine(0, 0, 20, 120, paint);
        canvas.drawLine(120, 0, 0, 120, paint);
    }
**/
}

