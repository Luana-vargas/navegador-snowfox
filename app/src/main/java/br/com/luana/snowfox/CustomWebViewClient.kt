package br.com.luana.snowfox

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

private const val CUSTOMWEBVIEWCLIENT = "CWVC"

class CustomWebViewClient constructor(private val activity: WebActivity, private val progressBar: ProgressBar, private val editText: EditText) : WebViewClient(){

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean = false

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean = false

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
//        activity.showMessage("Erro recebido: ${error?.description}")
        Log.d(CUSTOMWEBVIEWCLIENT, "receivedError: ${error?.description}")
        super.onReceivedError(view, request, error)
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
//        activity.showMessage("erro http: ${errorResponse.toString()}")
        Log.d(CUSTOMWEBVIEWCLIENT, "receivedHttpError: ${errorResponse?.statusCode} ${errorResponse?.toString()}")
        super.onReceivedHttpError(view, request, errorResponse)
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

    @TargetApi(Build.VERSION_CODES.M)
    override fun onPageCommitVisible(view: WebView?, url: String?) {
        super.onPageCommitVisible(view, url)
        progressBar.visibility = View.GONE
    }


}