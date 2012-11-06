#ifndef LOG_MACROS_H
#define LOG_MACROS_H

#include <android/log.h>

#define  LOG_TAG    "libraycast"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#endif
