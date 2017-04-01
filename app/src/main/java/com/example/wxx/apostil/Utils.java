package com.example.wxx.apostil;

import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by wxx on 2017/3/6.
 */

public class Utils {
    public static CookieManager getCookiesFromURLConnection(HttpURLConnection urlConnection){
        Map<String,List<String >> headers= urlConnection.getHeaderFields();
        CookieManager cookieManager=new CookieManager();
        List<String> cookiesHeader=headers.get("Set-Cookie");

        if(cookiesHeader!=null){
            for(String cookie: cookiesHeader){
                cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }
        }

        return cookieManager;
    }
}
