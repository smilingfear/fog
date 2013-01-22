/*******************************************************************************
 * Forwarding on Gates Simulator/Emulator - Video and audio Gates
 * Copyright (c) 2012, Integrated Communication Systems Group, TU Ilmenau.
 *
 * This part of the Forwarding on Gates Simulator/Emulator is free software.
 * Your are allowed to redistribute it and/or modify it under the terms of
 * the GNU General Public License version 2 as published by the Free Software
 * Foundation.
 *
 * This source is published in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License version 2 for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program. Otherwise, you can write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02111, USA.
 * Alternatively, you find an online version of the license text under
 * http://www.gnu.org/licenses/gpl-2.0.html.
 ******************************************************************************/
/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class jniImports_VideoTranscoder */

#ifndef _Included_jniImports_VideoTranscoder
#define _Included_jniImports_VideoTranscoder
#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     jniImports_VideoTranscoder
 * Method:    InitLogger
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_jniImports_VideoTranscoder_InitLogger(JNIEnv *, jobject, jint);

/*
 * Class:     jniImports_VideoTranscoder
 * Method:    open
 * Signature: (ILjava/lang/String;ZLjava/lang/String;III)V
 */
JNIEXPORT void JNICALL Java_jniImports_VideoTranscoder_open(JNIEnv *, jobject, jint, jstring, jboolean, jstring, jint, jint, jfloat);

/*
 * Class:     jniImports_VideoTranscoder
 * Method:    getInstance
 * Signature: (V)I
 */
JNIEXPORT jint JNICALL Java_jniImports_VideoTranscoder_getInstance(JNIEnv *, jobject);

/*
 * Class:     jniImports_VideoTranscoder
 * Method:    addDataInput
 * Signature: (I[BI)V
 */
JNIEXPORT void JNICALL Java_jniImports_VideoTranscoder_addDataInput(JNIEnv *, jobject, jint, jbyteArray, jint);

/*
 * Class:     jniImports_VideoTranscoder
 * Method:    getOutputPacket
 * Signature: (I)[B
 */
JNIEXPORT jbyteArray JNICALL Java_jniImports_VideoTranscoder_getOutputPacket(JNIEnv *, jobject, jint);

/*
 * Class:     jniImports_VideoTranscoder
 * Method:    getFrame
 * Signature: (I)[B
 */
JNIEXPORT jbyteArray JNICALL Java_jniImports_VideoTranscoder_getFrame(JNIEnv *, jobject, jint);

/*
 * Class:     jniImports_VideoTranscoder
 * Method:    close
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_jniImports_VideoTranscoder_close(JNIEnv *, jobject, jint);

/*
 * Class:     jniImports_VideoTranscoder
 * Method:    stop
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_jniImports_VideoTranscoder_stop(JNIEnv *, jobject, jint);

/*
 * Class:     jniImports_VideoTranscoder
 * Method:    getStats
 * Signature: (I[I)V
 */
JNIEXPORT void JNICALL Java_jniImports_VideoTranscoder_getStats(JNIEnv *, jobject, jint, jintArray);

#ifdef __cplusplus
}
#endif
#endif
