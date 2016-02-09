//
// Created by Carlo Tomasin on 09/02/16.
//
#include "AccTrack.h"
#include "LinearAcceleration.h"

#define SAMPLES_NUM com_example_ctomasin_runningdynamics_AccTrack_SAMPLES_NUM
#define AXIS_NUM com_example_ctomasin_runningdynamics_LinearAcceleration_AXIS_NUM
#define G_VAL 9.18f

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
        (JNIEnv *env, jobject self, jfloatArray jVals)
{
    jclass clazz = env->GetObjectClass(self);
    jobject mPoints = env->GetObjectField(self,env->GetFieldID(clazz,"mPoints","Ljava/util/ArrayList"));
    jint mHeight = env->GetIntField(self,env->GetFieldID(clazz,"mHeight","I"));

    jboolean isCopy;
    jfloat *vals = env->GetFloatArrayElements(jVals,&isCopy);

    jint size = env->CallIntMethod(mPoints,env->GetMethodID(clazz,"size","()I"));
    if(size > SAMPLES_NUM) {
        env->CallVoidMethod(mPoints,env->GetMethodID(clazz,"remove","(I)V"),0);
    }

    float points[AXIS_NUM];
    for(int i = 0; i < AXIS_NUM; ++i) {
        points[i] = mHeight / 2 + mHeight * (vals[i] / (G_VAL * 4));
    }

    env->ReleaseFloatArrayElements(jVals,vals,JNI_ABORT);

    jfloatArray jpoints = env->NewFloatArray(AXIS_NUM);
    env->SetFloatArrayRegion(jpoints,0,AXIS_NUM,points);
    env->CallVoidMethod(mPoints,env->GetMethodID(clazz,"add","(Ljava/lang/Object)V"),jpoints);

    env->CallVoidMethod(self,env->GetMethodID(clazz,"invalidate","()V"));
}

