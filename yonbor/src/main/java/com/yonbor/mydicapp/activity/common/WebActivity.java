package com.yonbor.mydicapp.activity.common;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yonbor.baselib.log.AppLogger;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;

/**
 * yonbor605
 */
public class WebActivity extends BaseActivity {
    public static final String URL = "url";
    public static final String TITLE = "title";
    public static final String SHOW_CLOSE = "show_close";
    protected WebView web;
    protected ProgressBar emptyProgress;
    protected String url;
    protected String title;
    protected boolean showClose;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        url = getIntent().getStringExtra(URL);
        AppLogger.d(url);
        title = getIntent().getStringExtra(TITLE);
        showClose = getIntent().getBooleanExtra(SHOW_CLOSE, false);
        findView();
        if (!TextUtils.isEmpty(url)) {
            web.loadUrl(url);
        }
    }

    public void findView() {
        findActionBar();
        actionBar.setTitle(TextUtils.isEmpty(title) ? "" : title);
        actionBar.setBackAction(new AppActionBar.Action() {

            @Override
            public int getDrawable() {
                return R.drawable.btn_back;
            }

            @Override
            public String getText() {
                return null;
            }

            @Override
            public void performAction(View view) {
                if (web.canGoBack()) {
                    web.goBack();
                    return;
                }

                finish();
            }
        });
        if (showClose) {
            actionBar.addAction(new AppActionBar.Action() {
                @Override
                public int getDrawable() {
                    return 0;
                }

                @Override
                public String getText() {
                    return "关闭";
                }

                @Override
                public void performAction(View view) {
                    finish();
                }
            });
        }
        emptyProgress = (ProgressBar) findViewById(R.id.emptyProgress);
        web = (WebView) findViewById(R.id.webview);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);

        addJavascriptInterface(web);
        // web.addJavascriptInterface(new InJavaScript(), "health");
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.requestFocus();
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    emptyProgress.setVisibility(View.GONE);
                }
            }
        });

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
//                view.loadUrl("javascript:window.local_obj.showSource(document.head.innerHTML" +
//                        " || document.getElementsByTagName('head')[0].innerHTML);");
//                view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
//                        + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                super.onPageFinished(view, url);
            }
        });
    }

    protected void addJavascriptInterface(WebView webView) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

}
