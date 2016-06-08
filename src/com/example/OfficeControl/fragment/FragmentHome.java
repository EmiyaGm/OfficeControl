package com.example.OfficeControl.fragment;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.OfficeControl.commom.T;

public class FragmentHome extends Fragment {
	private WebView webview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, null);
		setupView(view);
		return view;

	}
	private class webViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
	private void setupView(View view) {

		webview = (WebView) view.findViewById(R.id.webview);
		WebSettings webSettings = webview.getSettings();
		//设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		//设置可以访问文件
		webSettings.setAllowFileAccess(true);
		//设置支持缩放
		webSettings.setBuiltInZoomControls(true);
		//加载需要显示的网页
		webview.loadUrl("http://182.254.218.192/OfficeControl/phoneIndex.html");
		//设置Web视图
		webview.setWebViewClient(new webViewClient ());


	}
}
