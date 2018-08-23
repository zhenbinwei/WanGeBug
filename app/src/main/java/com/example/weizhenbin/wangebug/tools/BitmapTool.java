package com.example.weizhenbin.wangebug.tools;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.opengl.GLES10;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/**
 * Created by weizhenbin on 2018/8/21.
 */

public class BitmapTool {
    /**压缩到绘制限度临界*/
    public static Bitmap compressFromMaxSize(Bitmap srcBmp, int maxSize){
        if(srcBmp!=null){
            int w=srcBmp.getWidth();
            int h=srcBmp.getHeight();
            if(maxSize<=0){
                return srcBmp;
            }
            if(w>maxSize||h>maxSize){
                float multiple=1;
                //压缩
                Matrix matrix = new Matrix();
                if(w>maxSize&&h<maxSize){
                    //宽度超过了
                    multiple=(float)maxSize/w;
                }else if(w<maxSize&&h>maxSize){
                    //高度超过了
                    multiple=(float)maxSize/h;
                }else {
                    //宽高都超过了
                    multiple=(float)maxSize/w>(float)maxSize/h?(float)maxSize/w:(float)maxSize/h;
                }
                // 缩放图片动作
                matrix.postScale(multiple, multiple);
                Bitmap bitmap = Bitmap.createBitmap(srcBmp, 0, 0, w,
                        h, matrix, true);
                return bitmap;
            }
            return srcBmp;
        }
        return srcBmp;
    }
    /**获取绘制最大限度*/
    public static int getGLESTextureLimitEqualAboveLollipop() {
        try {
            EGL10 egl = (EGL10) EGLContext.getEGL();
            EGLDisplay dpy = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            int[] vers = new int[2];
            egl.eglInitialize(dpy, vers);
            int[] configAttr = {
                    EGL10.EGL_COLOR_BUFFER_TYPE, EGL10.EGL_RGB_BUFFER,
                    EGL10.EGL_LEVEL, 0,
                    EGL10.EGL_SURFACE_TYPE, EGL10.EGL_PBUFFER_BIT,
                    EGL10.EGL_NONE
            };
            EGLConfig[] configs = new EGLConfig[1];
            int[] numConfig = new int[1];
            egl.eglChooseConfig(dpy, configAttr, configs, 1, numConfig);
            if (numConfig[0] == 0) {// TROUBLE! No config found.
            }
            EGLConfig config = configs[0];
            int[] surfAttr = {
                    EGL10.EGL_WIDTH, 64,
                    EGL10.EGL_HEIGHT, 64,
                    EGL10.EGL_NONE
            };
            EGLSurface surf = egl.eglCreatePbufferSurface(dpy, config, surfAttr);
            final int EGL_CONTEXT_CLIENT_VERSION = 0x3098;  // missing in EGL10
            int[] ctxAttrib = {
                    EGL_CONTEXT_CLIENT_VERSION, 1,
                    EGL10.EGL_NONE
            };
            EGLContext ctx = egl.eglCreateContext(dpy, config, EGL10.EGL_NO_CONTEXT, ctxAttrib);
            egl.eglMakeCurrent(dpy, surf, surf, ctx);
            int[] maxSize = new int[1];
            GLES10.glGetIntegerv(GLES10.GL_MAX_TEXTURE_SIZE, maxSize, 0);
            egl.eglMakeCurrent(dpy, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE,
                    EGL10.EGL_NO_CONTEXT);
            egl.eglDestroySurface(dpy, surf);
            egl.eglDestroyContext(dpy, ctx);
            egl.eglTerminate(dpy);
            return  maxSize[0];
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }



}
