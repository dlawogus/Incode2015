package dev.woody.ext.incode2015;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;


public class it_bw extends Activity {
    WebView webs;
    WebBackForwardList list;
    String first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_it_bw);

        Intent intent = getIntent();
        String vcode = intent.getStringExtra("urls");
        String UserID = intent.getStringExtra("userid");
        first = "";
        ImageView btnback = (ImageView)findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webs = (WebView)findViewById(R.id.webs);
        webs.getSettings().setJavaScriptEnabled(true);
        webs.getSettings().setDefaultTextEncodingName("UTF-8");
        webs.getSettings().setDisplayZoomControls(true);
        webs.getSettings().setSupportZoom(true);
        webs.getSettings().setBuiltInZoomControls(true);
        webs.setWebViewClient(new WebViewClientClass());
        webs.setWebChromeClient(new MyWebChromeClientClass());
        webs.loadUrl("http://incoders.co.kr/incode/goto.php?vcode=" + vcode + "&user_id=" + UserID);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        list = webs.copyBackForwardList();
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(webs.canGoBack()){
                first = webs.getUrl();
                webs.goBack();
                return true;
            }else{
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(first != "" && first.equals(url)){
                finish();
            }
            else
                view.loadUrl(url);

            return true;
        }
    }

    private class MyWebChromeClientClass extends WebChromeClient {
        @Override
        public boolean onJsAlert(final WebView view, final String url, final String message, final JsResult result){
            new AlertDialog.Builder(getApplicationContext())
                    .setTitle("경고")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which){
                                    result.confirm();
                                }
                            })
                    .setCancelable(false)
                    .create().show();

            webs.goBack();
            return true;
        }
    }
}