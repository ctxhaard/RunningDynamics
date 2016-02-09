package com.example.ctomasin.runningdynamics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by ctomasin on 04/02/16.
 */
public class AccTrack extends SurfaceView {

    private static final String TAG = "AccTrack";

    private static final int SAMPLES_NUM = 600;
    private float SAMPLE_STEP;

    private int mWidth, mHeight;

    private Paint[] mPaint;
    private int[] mColor;

    private ArrayList<float[]> mPoints;

    public AccTrack(Context context,AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint[LinearAcceleration.AXIS_NUM];
        mColor = new int[LinearAcceleration.AXIS_NUM];

        for(int i = 0; i < LinearAcceleration.AXIS_NUM; ++i) {
            mPaint[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
        }

        mColor[0] = Color.RED;
        mColor[1] = Color.BLUE;
        mColor[2] = Color.GREEN;

        mPoints = new ArrayList<>(SAMPLES_NUM);
        setWillNotDraw(false);
        init();

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");

        Path[] p = new Path[3];
        for(int na = 0; na < LinearAcceleration.AXIS_NUM; ++na) {
            p[na] = new Path();
        }
        for(int i = 1; i < SAMPLES_NUM; ++i) {
         try {
             float[] from = mPoints.get(i - 1);
             float[] to = mPoints.get(i);

             for(int j = 0; j < LinearAcceleration.AXIS_NUM; ++j) {
                 p[j].moveTo(SAMPLE_STEP * i - SAMPLE_STEP, from[j]);
                 p[j].lineTo(SAMPLE_STEP * i, to[j]);
             }
         } catch(Exception e) {
             break;
         }
        }
        for(int na = 0; na < LinearAcceleration.AXIS_NUM; ++na) {
            p[na].close();
            canvas.drawPaint(mPaint[na]);
            canvas.drawPath(p[na], mPaint[na]);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged");

        mWidth = w;
        mHeight = h;

        SAMPLE_STEP = 1.0f * w / SAMPLES_NUM;

    }

    public final native void addValues(float[] vals);

    private void init() {
        for(int i = 0; i < mPaint.length; ++i) {
            mPaint[i].setColor(mColor[i]);
            mPaint[i].setStyle(Paint.Style.STROKE);
        }
    }
}
