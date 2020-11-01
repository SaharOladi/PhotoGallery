package org.maktab.photogallery.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;

import androidx.annotation.NonNull;

import org.maktab.photogallery.network.FlickrFetcher;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ThumbnailDownloader<T> extends HandlerThread {

    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    private static final int MESSAGE_PRELOAD = 1;

    private Handler mRequestHandler;
    private ConcurrentMap<T, String> mRequestMap = new ConcurrentHashMap<>();
    private Handler mResponseHandler;
    private ThumbnailDownloadListener<T> mThumbnailDownloaderListener;
    private LruCache<String, Bitmap> mBitmapCache = new LruCache<>(50);

    public interface ThumbnailDownloadListener<T> {
        void onThumbnailDownloaded(T target, Bitmap thumbnail);
    }

    public void setThumbnailDownloaderListener(ThumbnailDownloadListener listener) {
        mThumbnailDownloaderListener = listener;
    }

    public ThumbnailDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }

    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    T target = (T) msg.obj;
                    Log.i(TAG, "Got a request for URL: " + mRequestMap.get(target));
                    handleDownloadRequest(target);
                } else if (msg.what == MESSAGE_PRELOAD) {
                    String url = (String) msg.obj;
                    Log.i(TAG, "Got a request to prelaod URL: " + url);
                    handlePreloadRequest(url);
                }
            }
        };
    }

    public void queueThumbnail(T target, String url) {
        Log.i(TAG, "Got a URL: " + url);

        if (url == null) {
            mRequestMap.remove(target);
        } else {
            mRequestMap.put(target, url);
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
                    .sendToTarget();
        }
    }

    public void preloadThumbnail(String url) {
        Log.i(TAG, "Got a URL to preload: " + url);

        mRequestHandler.obtainMessage(MESSAGE_PRELOAD, url)
                .sendToTarget();
    }

    public void clearQueue() {
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
    }

    private Bitmap retrieveBitmap(String url) throws IOException {
        Bitmap bitmap = mBitmapCache.get(url);
        if (bitmap != null) {
            Log.i(TAG, "Bitmap retrieved from cache");
        } else {
            byte[] bitmapBytes = new FlickrFetcher().getUrlBytes(url);
            bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            mBitmapCache.put(url, bitmap);
            Log.i(TAG, "Bitmap created");
        }
        return bitmap;
    }

    private void handleDownloadRequest(final T target) {
        try {
            final String url = mRequestMap.get(target);
            if (url == null) {
                return;
            }

            final Bitmap bitmap = retrieveBitmap(url);
            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mRequestMap.get(target) != url) {
                        return;
                    }

                    mRequestMap.remove(target);
                    mThumbnailDownloaderListener.onThumbnailDownloaded(target, bitmap);
                }
            });
        } catch (IOException ioe) {
            Log.e(TAG, "Error downloading image", ioe);
        }
    }

    private void handlePreloadRequest(String url) {
        try {
            retrieveBitmap(url);
        } catch (IOException ioe) {
            Log.e(TAG, "Error preloading image", ioe);
        }
    }

    /**
     public static final String TAG = "ThumbnailDownloader";
     private static final int WHAT_THUMBNAIL_DOWNLOAD = 1;

     private Handler mHandlerRequest;
     private Handler mHandlerResponse;


     private ConcurrentHashMap<T, String> mRequestMap = new ConcurrentHashMap<>();

     private ThumbnailDownloaderListener mListener;

     public ThumbnailDownloaderListener getListener() {
     return mListener;
     }

     public void setListener(ThumbnailDownloaderListener listener) {
     mListener = listener;
     }

     public ThumbnailDownloader(Handler uiHandler) {
     super(TAG);

     mHandlerResponse = uiHandler;
     }

     @Override protected void onLooperPrepared() {
     super.onLooperPrepared();

     mHandlerRequest = new Handler() {
     //run message
     @Override public void handleMessage(@NonNull Message msg) {
     super.handleMessage(msg);

     //download url from net
     try {
     if (msg.what == WHAT_THUMBNAIL_DOWNLOAD) {
     if (msg.obj == null)
     return;

     T target = (T) msg.obj;
     String url = mRequestMap.get(target);

     handleDownloadMessage(target, url);
     }
     } catch (IOException e) {
     Log.e(TAG, e.getMessage(), e);
     }
     }
     };
     }

     private void handleDownloadMessage(T target, String url) throws IOException {
     if (url == null)
     return;

     FlickrFetcher flickrFetcher = new FlickrFetcher();
     byte[] bitmapBytes = flickrFetcher.getUrlBytes(url);

     Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);

     mHandlerResponse.post(new Runnable() {
     @Override public void run() {
     if (mRequestMap.get(target) != url)
     return;

     mListener.onThumbnailDownloaded(target, bitmap);
     //                mRequestMap.remove(target);
     }
     });
     }

     public void queueThumbnail(T target, String url) {
     mRequestMap.put(target, url);

     //create a message and send it to looper (to put in queue)
     Message message = mHandlerRequest.obtainMessage(WHAT_THUMBNAIL_DOWNLOAD, target);
     message.sendToTarget();
     }

     public void clearQueue() {
     mHandlerRequest.removeMessages(WHAT_THUMBNAIL_DOWNLOAD);
     }

     public interface ThumbnailDownloaderListener<T> {
     void onThumbnailDownloaded(T target, Bitmap bitmap);
     }
     **/
}
