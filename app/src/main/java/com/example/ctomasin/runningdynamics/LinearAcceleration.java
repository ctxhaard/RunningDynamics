package com.example.ctomasin.runningdynamics;

/**
 * Created by ctomasin on 03/02/16.
 */
public class LinearAcceleration {

    public static final int AXIS_NUM = 3;
    public static final float G_VAL = 9.18f;

    private static final int GRAVITY_SAMPLES_NUM = 10;
    private static final float ALPHA = 0.8f;

    private static LinearAcceleration sharedInstance;

    private int mNumGravitySamples;
    private float[] mGravity;

    public LinearAcceleration() {
        mNumGravitySamples = 0;
        mGravity = new float[LinearAcceleration.AXIS_NUM];
    }

    public static final LinearAcceleration shared() {
        if(null == sharedInstance) {
            sharedInstance = new LinearAcceleration();
        }
        return sharedInstance;
    }

    public final void resetGravity() {
        mNumGravitySamples = 0;
        for(int i = 0; i < AXIS_NUM; ++i) {
            mGravity[i] = 0.0f;
        }
    }

    public native final float[] linearAcceleration(float[] values);

    /*public final float[] linearAcceleration(float[] values) {
        for(int i = 0; i < AXIS_NUM; ++i) {
            mGravity[i] =  ALPHA * mGravity[i] + (1 - ALPHA) * values[i];
            values[i] = values[i] - mGravity[i];
        }
        if(mNumGravitySamples < GRAVITY_SAMPLES_NUM) ++mNumGravitySamples;
        return values;
    }*/

    public final boolean hasValues() {
        return (mNumGravitySamples >= GRAVITY_SAMPLES_NUM);
    }
}
