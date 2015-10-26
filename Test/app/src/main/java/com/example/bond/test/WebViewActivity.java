package com.example.bond.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
 
public class WebViewActivity extends ActionBarActivity {
 public String param;
	private WebView webView;
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null)
		{
			param=extras.getString("wwurl");
			
		}
 
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.loadUrl(param);
 
	}
 
}