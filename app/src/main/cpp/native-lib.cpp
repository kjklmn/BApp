#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_bdhs_bossapp_TestActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
