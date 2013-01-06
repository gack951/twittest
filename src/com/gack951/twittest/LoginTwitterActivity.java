package com.gack951.twittest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginTwitterActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        WebView webView = (WebView)findViewById(R.id.login);
                webView.setWebViewClient(new TwitterWebViewClient());
        webView.loadUrl(this.getIntent().getExtras().getString("auth_url"));
    }

    private class TwitterWebViewClient extends WebViewClient {
           public void onPageFinished(WebView view, String url) {
         super.onPageFinished(view, url);

         if (url != null && url.startsWith(TwitterConst.CALLBACK_URL)) {

           String[] urlParameters = url.split("\\?")[1].split("&");
           String oauthToken      = "";
           String oauthVerifier   = "";

           if (urlParameters[0].startsWith(TwitterConst.PARAM_OAUTH_TOKEN)) {
             oauthToken = urlParameters[0].split("=")[1];
           }
           else if (urlParameters[1].startsWith(TwitterConst.PARAM_OAUTH_TOKEN)) {
             oauthToken = urlParameters[1].split("=")[1];
           }
           if (urlParameters[0].startsWith(TwitterConst.PARAM_OAUTH_VERIFIER)) {
             oauthVerifier = urlParameters[0].split("=")[1];
           }
           else if (urlParameters[1].startsWith(TwitterConst.PARAM_OAUTH_VERIFIER)) {
             oauthVerifier = urlParameters[1].split("=")[1];
           }

           Intent intent = getIntent();
           intent.putExtra(TwitterConst.PARAM_OAUTH_TOKEN, oauthToken);
           intent.putExtra(TwitterConst.PARAM_OAUTH_VERIFIER, oauthVerifier);
           intent.putExtra("status","available");

           setResult(Activity.RESULT_OK, intent);
           finish();
         }
      }
   }
}