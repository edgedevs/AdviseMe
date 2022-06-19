package com.advisemetech.adviseme;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class AdviseMeTerms extends Activity {

    public class ViewWeb extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            WebView wb = new WebView(this);
          //  wb.loadUrl("file:///android_asset/index.html");
            wb.loadUrl("file:///asset/adviseMeTermsAndPolicies.html");
            setContentView(wb);
        }
    }
}
