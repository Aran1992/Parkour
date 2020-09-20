package com.example.parkour;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            new App(getAssets());
            String url = "http://localhost:8080/index.html";
            WebView webView = (WebView) findViewById(R.id.web_view);
            WebView.setWebContentsDebuggingEnabled(true);
            WebSettings websettings = webView.getSettings();
            websettings.setDomStorageEnabled(true);  // 开启 DOM storage 功能
            websettings.setAppCacheMaxSize(1024 * 1024 * 8);
            String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
            websettings.setAppCachePath(appCachePath);
            websettings.setAllowFileAccess(true);    // 可以读取文件缓存
            websettings.setAppCacheEnabled(true);    //开启H5(APPCache)缓存功能
            websettings.setJavaScriptEnabled(true);
            webView.loadUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}