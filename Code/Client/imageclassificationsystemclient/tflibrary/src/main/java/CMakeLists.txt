cmake_minimum_required(VERSION 3.4.1)

set_target_properties(${TARGET} PROPERTIES LINKER_LANGUAGE C)

add_library( tensorflow-lib
             SHARED
             ../jni/yuv2rgb.cc
             ../jni/rgb2yuv.cc
             ../jni/imageutils_jni.cc )

find_library( log-lib
              log )

target_link_libraries( tensorflow-lib
                       ${log-lib} )