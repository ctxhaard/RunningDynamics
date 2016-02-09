//
// Created by ctomasin on 06/02/16.
//

#include "LinearAcceleration.h"
#include <android/log.h>

#define NULL ((void *)0)
#define AXIS_NUM com_example_ctomasin_runningdynamics_LinearAcceleration_AXIS_NUM
#define ALPHA com_example_ctomasin_runningdynamics_LinearAcceleration_ALPHA
#define GRAVITY_SAMPLES_NUM com_example_ctomasin_runningdynamics_LinearAcceleration_GRAVITY_SAMPLES_NUM

JNIEXPORT jfloatArray JNICALL Java_com_example_ctomasin_runningdynamics_LinearAcceleration_linearAcceleration
        (JNIEnv *env, jobject jobj, jfloatArray jvals) {

    __android_log_write(ANDROID_LOG_DEBUG, __FILE__, "calculating acc value");

    jclass clazz = env->GetObjectClass(jobj);

    jfieldID mGravityFieldID = env->GetFieldID(clazz,"mGravity","[F");

    jfloatArray jgravs = reinterpret_cast<jfloatArray>(
            env->GetObjectField(jobj, mGravityFieldID)
    );

    jboolean isCopy;
    float *gravs = env->GetFloatArrayElements(jgravs,&isCopy);
    float *vals = env->GetFloatArrayElements(jvals,&isCopy);

    for(int i = 0; i < AXIS_NUM; ++i) {
        gravs[i] = ALPHA * gravs[i] + (1 - ALPHA) * vals[i];
        vals[i] = vals[i] - gravs[i];
    }

    env->ReleaseFloatArrayElements(jgravs,gravs,JNI_COMMIT);
    env->ReleaseFloatArrayElements(jvals,vals,JNI_COMMIT);

    jfieldID mNumGravitySamplesFieldID = env->GetFieldID(clazz,"mNumGravitySamples","I");

    jint jival = env->GetIntField(jobj,mNumGravitySamplesFieldID);

    if(jival < GRAVITY_SAMPLES_NUM) {
        jival = jival + 1;
        env->SetIntField(jobj,mNumGravitySamplesFieldID,jival);
    }

    //env->SetFloatArrayRegion(jvals,0,AXIS_NUM,vals);
    return jvals;

}
