# Disaster Management
___

This app determines the percentage of disaster by comparing two
photographs.

### Installation
___

1) Enter this command in the shell

    `$ git clone https://github.com/sh-zam/Khasmer.git`

2) We will require both [OpenCV](https://github.com/opencv/opencv) and [OpenCV-contrib](
https://github.com/opencv/opencv_contrib) for this. You can either build them individually
or use [opencv3-android-sdk-with-contrib](https://github.com/chaoyangnz/opencv3-android-sdk-with-contrib)
or [opencv-android-sdk-with-contrib](https://github.com/Mainvooid/opencv-android-sdk-with-contrib).
I used the latter because that is what I found first.

3) Now use `$ git clone https://github.com/Mainvooid/opencv-android-sdk-with-contrib.git` or download the zip
and extract it.

4) In `CMakeLists.txt` file on line 8 change the directory to the place where you saved your OpenCV libs.

    `include_directories(<path-to-your-directory>/native/jni/include)`

5) Build and run :)