//============================================================================
// Name        : Card Classifier
// Author      : Mustafa Qamar-ud-Din
// Version     : 1.0
// Copyright   : Copyright @ 2018 All Rights Reserved
// Description : Image Processing & Pattern Recognition in C++, Ansi-style
//============================================================================

// Import Libraries

// Android
#include <jni.h>
#include <android/log.h>
#include <android/bitmap.h>
#include <android/asset_manager.h>
// C++
#include <iostream>
#include <vector>
#include <string>
#include <sstream>
// C
#include <math.h>
#include "stdio.h"
// OpenCV
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/features2d.hpp>
#include <opencv2/objdetect.hpp>
#include <opencv2/face.hpp>
#include <opencv2/img_hash.hpp>

// Register Namespaces
using namespace cv;
using namespace std;
using namespace cv::face;
using namespace cv::img_hash;


// Define Signatures
Mat preprocess_image(Mat);
double match_standards(Mat&, Mat&);
double computeHash(Ptr<ImgHashBase>, const Mat&, const Mat&);
double calc_dis_pct(const Mat&, const Mat&);


// NDK JNI Connector
extern "C" {
JNIEXPORT jdouble JNICALL Java_abs_khasmer_disastermanagement_DetailsActivity_calcDisasterPCT(
		JNIEnv* env,
		jobject _this,
		jobject bitmap_src,
		jobject bitmap_dst
)
{
    // Define C++ Variables
    // Left Image
    AndroidBitmapInfo  src_im_info;
    void*              src_im_pixels;
    int src_im_width;
    int src_im_height;
    int src_im_stride;

    // Right Image
    AndroidBitmapInfo  dst_im_info;
    void*              dst_im_pixels;
    int dst_im_width;
    int dst_im_height;
    int dst_im_stride;

    // Read Bitmaps
    // Left Image
    AndroidBitmap_getInfo(env, bitmap_src, &src_im_info);
    src_im_width = src_im_info.width;
    src_im_height = src_im_info.height;
    src_im_stride = src_im_info.stride;

    // Left Image
    AndroidBitmap_getInfo(env, bitmap_dst, &dst_im_info);
    dst_im_width = dst_im_info.width;
    dst_im_height = dst_im_info.height;
    dst_im_stride = dst_im_info.stride;

    // Lock Bitmaps
    AndroidBitmap_lockPixels(env, bitmap_src, &src_im_pixels);
    AndroidBitmap_lockPixels(env, bitmap_dst, &dst_im_pixels);

    // Convert Bitmaps to OpenCV Matrix
    Mat src(src_im_height, src_im_width, CV_8UC4, (unsigned char*)src_im_pixels);
    Mat dst(dst_im_height, dst_im_width, CV_8UC4, (unsigned char*)dst_im_pixels);

	// Release Bitmaps
	AndroidBitmap_unlockPixels(env, bitmap_src);
	AndroidBitmap_unlockPixels(env, bitmap_dst);

    // Invoke Core C++
    return calc_dis_pct(src, dst);
}
}


// C++ Functions Defintions

/**
 * Preprocess Image
 * Convert RGBA to GRAY
 * Gaussian Blur
 */
Mat preprocess_image(Mat img) {
	Mat temp, ret;
	cvtColor(img, ret, CV_BGRA2GRAY);
	GaussianBlur(ret, temp, Size(7, 7), 1.5);
	return ret;
}

/**
 * Applies Similarity Metric on Given Test Image
 * Returns Integer
 */
double match_standards(
        Mat& tgt,
        Mat& dst
        ) {
	// Compare Images
	double dist_val_hash = computeHash(img_hash::BlockMeanHash::create(0), tgt, dst);

	// Max Val

	double max_val = 100;

	// Percentage Val
	double ret = (dist_val_hash / max_val) * 100;

	// Avoid > 100
	if ( ret > 100) {
		ret = 100;
	}

	return ret;
}

/**
 * Given 2 Images and Hash Algorithm,
 * Computes and compares image hashes
 * returns distance
 */
double computeHash(Ptr<ImgHashBase> algo, const Mat& input, const Mat& target)
{
	Mat inHash; //hash of input image
	Mat targetHash; //hash of target image

	//comupte hash of input and target
	algo->compute(input, inHash);
	algo->compute(target, targetHash);
	//Compare the similarity of inHash and targetHash
	//recommended thresholds are written in the header files
	//of every classes
	return algo->compare(inHash, targetHash);
}

Mat read_cv_image(string file_path) {
	Mat image;
	image = imread(file_path, CV_LOAD_IMAGE_COLOR);   // Read the file

	if (!image.data)                              // Check for invalid input
	{
		cout << "Could not open or find the image" << std::endl;
		return image;
	}

	return image;
}

double calc_dis_pct(const Mat& src, const Mat& dst) {
	// Preprocess Images
	Mat prepr_src = preprocess_image(src);
	Mat prepr_dst = preprocess_image(dst);

	// Hash & Compare
	return match_standards(prepr_src, prepr_dst);
}
