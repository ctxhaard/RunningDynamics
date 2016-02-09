//
// Created by Carlo Tomasin on 09/02/16.
//
#include "AccTrack.h"

//    public final void addValues(float[] vals) {
//        if(mPoints.size() >= SAMPLES_NUM) mPoints.remove(0);
//
//        float[] points = new float[LinearAcceleration.AXIS_NUM];
//        for(int i = 0; i < LinearAcceleration.AXIS_NUM; ++i) {
//
//            points[i] = mHeight / 2 + mHeight * (vals[i] / (LinearAcceleration.G_VAL * 4));
//        }
//        mPoints.add(points);
//        invalidate();
//    }

JNIEXPORT void JNICALL Java_com_example_ctomasin_runningdynamics_AccTrack_addValues
        (JNIEnv *env, jobject jAccTrack, jfloatArray jVals)
{
    // TODO: implementare

}

