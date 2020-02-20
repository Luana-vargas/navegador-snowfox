package br.com.luana.snowfox

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ProgressBar

class CustomWebViewClient constructor(private val progressBar: ProgressBar, private val editText: EditText) : WebViewClient(){

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        view?.loadUrl(url)
        editText.setText(url)
        return false
    }



    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        editText.setText(url)
        progressBar.visibility = View.VISIBLE
        view?.visibility = View.GONE
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        progressBar.visibility = View.GONE
        view?.visibility = View.VISIBLE
    }
}