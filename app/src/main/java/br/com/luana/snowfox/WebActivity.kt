package br.com.luana.snowfox

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebSettings
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        this.wbvNavegador.settings.javaScriptEnabled = true
        this.wbvNavegador.settings.pluginState = WebSettings.PluginState.ON
        this.wbvNavegador.settings.allowFileAccess = true
        this.wbvNavegador.webViewClient = CustomWebViewClient(pbLoading, edtUrl)
        this.wbvNavegador.loadUrl(getString(R.string.url_padrao))

        imvPesquisar.setOnClickListener {
            pesquisar()
        }

        imvAvancar.setOnClickListener {
            avancar()
        }

        imvHome.setOnClickListener {
            home()
        }

        imvVoltar.setOnClickListener {
            voltar()
        }

        edtUrl.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event.action == KeyEvent.ACTION_DOWN &&
                    event.keyCode == KeyEvent.KEYCODE_ENTER){
                pesquisar()
                v.hideKeyBoard()
                true
            }
            false
        }

        edtUrl.setSelectAllOnFocus(true)

    }

    private fun pesquisar(){
        var url = edtUrl.text.toString().trim()
        if (!url.startsWith("http") || !url.startsWith("https")){
            url = "https://" + url
        }
        wbvNavegador.loadUrl(url)
    }

    private fun voltar() {
        if (wbvNavegador.canGoBack()) {
            wbvNavegador.goBack()
        }
    }

    private fun avancar(){
        if(wbvNavegador.canGoForward()) {
            wbvNavegador.goForward()
        }
    }

    private fun home(){
        wbvNavegador.loadUrl(getString(R.string.url_padrao))
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && wbvNavegador.canGoBack()){
            wbvNavegador.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}

fun View.hideKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
