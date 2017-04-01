package com.example.wxx.apostil;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by wxx on 2017/2/28.
 */

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mContext;

    private MySingleton(Context mContext){
        this.mContext=mContext;
        mRequestQueue=getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private LruCache<String,Bitmap> cache=new LruCache<String, Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });
    }

    public static synchronized MySingleton getInstance(Context mContext){
        if(mInstance == null){
            mInstance = new MySingleton(mContext);
        }
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null){
            mRequestQueue= Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }
    public <T> void addToRequsetQueue(Request<T> req){
        getRequestQueue().add(req);
    }
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
