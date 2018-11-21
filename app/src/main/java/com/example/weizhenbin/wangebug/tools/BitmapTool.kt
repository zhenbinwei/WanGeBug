package com.example.weizhenbin.wangebug.tools

import android.graphics.Bitmap
import android.graphics.Matrix
import android.opengl.GLES10
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext

/**
 * Created by weizhenbin on 2018/8/21.
 */

object BitmapTool {
    /**获取绘制最大限度 */
    // TROUBLE! No config found.
    // missing in EGL10
    val glesTextureLimitEqualAboveLollipop: Int
        get() {
            try {
                val egl = EGLContext.getEGL() as EGL10
                val dpy = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY)
                val vers = IntArray(2)
                egl.eglInitialize(dpy, vers)
                val configAttr = intArrayOf(EGL10.EGL_COLOR_BUFFER_TYPE, EGL10.EGL_RGB_BUFFER, EGL10.EGL_LEVEL, 0, EGL10.EGL_SURFACE_TYPE, EGL10.EGL_PBUFFER_BIT, EGL10.EGL_NONE)
                val configs = arrayOfNulls<EGLConfig>(1)
                val numConfig = IntArray(1)
                egl.eglChooseConfig(dpy, configAttr, configs, 1, numConfig)
                if (numConfig[0] == 0) {
                }
                val config = configs[0]
                val surfAttr = intArrayOf(EGL10.EGL_WIDTH, 64, EGL10.EGL_HEIGHT, 64, EGL10.EGL_NONE)
                val surf = egl.eglCreatePbufferSurface(dpy, config, surfAttr)
                val EGL_CONTEXT_CLIENT_VERSION = 0x3098
                val ctxAttrib = intArrayOf(EGL_CONTEXT_CLIENT_VERSION, 1, EGL10.EGL_NONE)
                val ctx = egl.eglCreateContext(dpy, config, EGL10.EGL_NO_CONTEXT, ctxAttrib)
                egl.eglMakeCurrent(dpy, surf, surf, ctx)
                val maxSize = IntArray(1)
                GLES10.glGetIntegerv(GLES10.GL_MAX_TEXTURE_SIZE, maxSize, 0)
                egl.eglMakeCurrent(dpy, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE,
                        EGL10.EGL_NO_CONTEXT)
                egl.eglDestroySurface(dpy, surf)
                egl.eglDestroyContext(dpy, ctx)
                egl.eglTerminate(dpy)
                return maxSize[0]
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return 0
        }

    /**压缩到绘制限度临界 */
    fun compressFromMaxSize(srcBmp: Bitmap?, maxSize: Int): Bitmap? {
        if (srcBmp != null) {
            val w = srcBmp.width
            val h = srcBmp.height
            if (maxSize <= 0) {
                return srcBmp
            }
            if (w > maxSize || h > maxSize) {
                var multiple = 1f
                //压缩
                val matrix = Matrix()
                multiple = if (maxSize in (h + 1)..(w - 1)) {
                    //宽度超过了
                    maxSize.toFloat() / w
                } else if (maxSize in (w + 1)..(h - 1)) {
                    //高度超过了
                    maxSize.toFloat() / h
                } else {
                    //宽高都超过了
                    if (maxSize.toFloat() / w > maxSize.toFloat() / h) maxSize.toFloat() / w else maxSize.toFloat() / h
                }
                // 缩放图片动作
                matrix.postScale(multiple, multiple)
                return Bitmap.createBitmap(srcBmp, 0, 0, w,
                        h, matrix, true)
            }
            return srcBmp
        }
        return srcBmp
    }


}
