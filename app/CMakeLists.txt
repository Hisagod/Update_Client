cmake_minimum_required(VERSION 3.4.1)

add_library(
#       动态库的名字
        update

        SHARED

#       需要参与编译成动态库的C源文件
        src/main/cpp/bsdiff.c
        src/main/cpp/bspatch.c
        src/main/cpp/main.c
        src/main/cpp/bzip2/bzip2.c
        src/main/cpp/bzip2/bzlib.c
        src/main/cpp/bzip2/compress.c
        src/main/cpp/bzip2/decompress.c
        src/main/cpp/bzip2/blocksort.c
        src/main/cpp/bzip2/huffman.c
        src/main/cpp/bzip2/crctable.c
        src/main/cpp/bzip2/randtable.c
        )

find_library(
        log-lib
        log)

target_link_libraries(
#       目标链接动态库
        update
        ${log-lib})