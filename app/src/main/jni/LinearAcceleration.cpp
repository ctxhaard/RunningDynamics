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

JNIEXPORT jfloatArray JNICALL Java_com_example_ctomasin_runningdynamics_LinearAcceleration_linearAcceleration
        (JNIEnv *env, jobject jobj, jfloatArray jvals) {

    // TODO: implementare qui
    for(int i = 0; i < com_example_ctomasin_runningdynamics_LinearAcceleration_AXIS_NUM; ++i) {


    }

}
