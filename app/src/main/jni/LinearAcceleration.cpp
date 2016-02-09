//
// Created by ctomasin on 06/02/16.
//

#include "LinearAcceleration.h"

/*public final float[] linearAcceleration(float[] values) {
    for(int i = 0; i < AXIS_NUM; ++i) {
        mGravity[i] =  ALPHA * mGravity[i] + (1 - ALPHA) * values[i];
        values[i] = values[i] - mGravity[i];
    }
    if(mNumGravitySamples < GRAVITY_SAMPLES_NUM) ++mNumGravitySamples;
    return values;
}*/

#define NULL ((void *)0)
#define AXIS_NUM com_example_ctomasin_runningdynamics_LinearAcceleration_AXIS_NUM
#define ALPHA com_example_ctomasin_runningdynamics_LinearAcceleration_ALPHA
#define GRAVITY_SAMPLES_NUM com_example_ctomasin_runningdynamics_LinearAcceleration_ALPHA

JNIEXPORT jfloatArray JNICALL Java_com_example_ctomasin_runningdynamics_LinearAcceleration_linearAcceleration
        (JNIEnv *env, jobject jobj, jfloatArray jvals) {

    jclass clazz = env->GetObjectClass(jobj);

    jfieldID mGravityFieldID = env->GetFieldID(clazz,"mGravity","[F");

    jfloatArray jgavs = reinterpret_cast<jfloatArray>(
            env->GetObjectField(jobj, mGravityFieldID)
    );

    jboolean isCopy;
    float *gravs = env->GetFloatArrayElements(jgavs,&isCopy);
    float *vals = env->GetFloatArrayElements(jvals,&isCopy);

    for(int i = 0; i < AXIS_NUM; ++i) {
        gravs[i] = ALPHA * gravs[i] + (1 - ALPHA) * vals[i];
        vals[i] = vals[i] - gravs[i];
    }

    jfieldID mNumGravitySamplesFieldID = env->GetFieldID(clazz,"mNumGravitySamples","I");

    jint jival = env->GetIntField(jobj,mNumGravitySamplesFieldID);

    if(jival < GRAVITY_SAMPLES_NUM) env->SetIntField(jobj,mNumGravitySamplesFieldID,++jival);

    env->SetFloatArrayRegion(jvals,0,AXIS_NUM,vals);
    return jvals;

}
