#include <jni.h>
#include <string>

const char * returnURL() {
    std::string restEndpoint = "https://restcountries.eu/rest/v2";
    return restEndpoint.c_str();
}

extern "C"
JNIEXPORT jstring JNICALL
Java_shankhadeepghoshal_org_countrieslistapp_application_CentralApplication_getBaseUrl(JNIEnv *env,
                                                                                       jobject instance) {

    return env->NewStringUTF(returnURL());
}