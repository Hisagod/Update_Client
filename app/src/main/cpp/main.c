//
// Created by Android on 2018/10/30.
//
#include <syslog.h>
#include <jni.h>
#include "include/update.h"

//差分
JNIEXPORT jint JNICALL
Java_com_aib_update_MainActivity_diff(JNIEnv *env, jobject instance, jstring oldpath_,
                                      jstring newpath_, jstring patch_) {
    int argc = 4;
    char *argv[argc];
    argv[0] = "bsdiff";
    argv[1] = (char *) ((*env)->GetStringUTFChars(env, oldpath_, 0));
    argv[2] = (char *) ((*env)->GetStringUTFChars(env, newpath_, 0));
    argv[3] = (char *) ((*env)->GetStringUTFChars(env, patch_, 0));

    int ret = diff(argc, argv);

    (*env)->ReleaseStringUTFChars(env, oldpath_, argv[1]);
    (*env)->ReleaseStringUTFChars(env, newpath_, argv[2]);
    (*env)->ReleaseStringUTFChars(env, patch_, argv[3]);

    return ret;
}

//合并
JNIEXPORT jint JNICALL
Java_com_aib_update_MainActivity_patch(JNIEnv *env, jobject instance, jstring oldpath_,
                                       jstring newpath_, jstring patch_) {
    int argc = 4;
    char *argv[argc];
    argv[0] = "bspatch";
    argv[1] = (char *) ((*env)->GetStringUTFChars(env, oldpath_, 0));
    argv[2] = (char *) ((*env)->GetStringUTFChars(env, newpath_, 0));
    argv[3] = (char *) ((*env)->GetStringUTFChars(env, patch_, 0));

    int ret = patch(argc, argv);

    (*env)->ReleaseStringUTFChars(env, oldpath_, argv[1]);
    (*env)->ReleaseStringUTFChars(env, newpath_, argv[2]);
    (*env)->ReleaseStringUTFChars(env, patch_, argv[3]);
    return ret;
}