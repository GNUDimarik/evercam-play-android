package io.evercam.androidapp.video;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

import org.freedesktop.gstreamer.GStreamer;

import io.evercam.androidapp.EvercamPlayApplication;

/**
 * Created by valerii76 on 11/7/15.
 */
public class MediaPlayer implements Callback {
    private final static String TAG = "MediaPlayer";

    private static native boolean nativeClassInit(); // Initialize native class: cache Method IDs for callbacks
    private native void nativeRequestSample(String format); // supported values are png and jpeg
    private native void nativeSetUri(String uri, int connectionTimeout);
    private native void nativeInit();     // Initialize native code, build pipeline, etc
    private native void nativeFinalize(); // Destroy pipeline and shutdown native code
    private native void nativePlay();     // Set pipeline to PLAYING
    private native void nativePause();    // Set pipeline to PAUSED
    private native void nativeSurfaceInit(Object surface);
    private native void nativeSurfaceFinalize();
    private native void nativeExpose();

    private long native_custom_data;      // Native code will use this to keep private data
    private VideoActivity activity;
    private GStreamerSurfaceView view;

    static
    {
        System.loadLibrary("gstreamer_android");
        System.loadLibrary("evercam");
        nativeClassInit();
    }

    MediaPlayer(VideoActivity activity, GStreamerSurfaceView view) {
        this.activity = activity;
        this.view = view;
        try
        {
            GStreamer.init(activity);
        } catch (Exception e)
        {
            Log.e(TAG, e.getLocalizedMessage());
            return;
        }
        nativeInit();
    }

    void setUri(String uri, int connectionTimeout) {
        nativeSetUri(uri, connectionTimeout);
    }

    void requestSample(String format) {
        nativeRequestSample(format);
    }

    void play() {
        nativePlay();
    }

    void pause() {
        nativePause();
    }

    void setSurface(Object surface) {
        nativeSurfaceInit(surface);
        nativeExpose();
    }

    private void onVideoLoaded()
    {
        activity.onVideoLoaded();
    }

    private void onVideoLoadFailed()
    {
        activity.onVideoLoadFailed();
    }

    private void onSampleRequestSuccess(byte[] data, int size)
    {
        activity.onSampleRequestSuccess(data, size);
    }

    private void onSampleRequestFailed()
    {
        activity.onSampleRequestFailed();
    }

    /**
     * **********
     * Surface
     * ***********
     */

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        Log.d("GStreamer", "Surface created: " + surfaceHolder.getSurface());
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceholder, int format, int width, int height)
    {
        Log.d("GStreamer", "Surface changed to format " + format + " width " + width + " height "
                + height);

        view.media_width = width;
        view.media_height = height;
        activity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                view.requestLayout();
            }
        });

        setSurface(surfaceholder.getSurface());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceholder)
    {
        Log.d("GStreamer", "Surface destroyed");
        nativeSurfaceFinalize();
    }

}
