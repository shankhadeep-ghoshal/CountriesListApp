#include <jni.h>
#include <string>

const char * returnURLData() {
    std::string restEndpoint = "https://restcountries.eu/rest/v2/";
    return restEndpoint.c_str();
}

const char * returnURLBin() {
    std::string restEndpoint = "https://restcountries.eu/data/";
    return restEndpoint.c_str();
}

extern "C"
JNIEXPORT jstring JNICALL
Java_shankhadeepghoshal_org_countrieslistapp_application_CentralApplication_getBaseUrlData(JNIEnv *env, jobject instance) {

    return env->NewStringUTF(returnURLData());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_shankhadeepghoshal_org_countrieslistapp_utilitiespackage_ImageFetcher_getBaseUrlBin(JNIEnv *env, jobject instance) {
    return env->NewStringUTF(returnURLBin());
}