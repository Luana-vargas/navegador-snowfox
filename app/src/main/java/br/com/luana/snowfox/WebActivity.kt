package br.com.luana.snowfox

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebSettings
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_web.*

private const val WEBACTIVITY = "WA"

class WebActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)


        this.wbvNavegador.setBackgroundColor(getColor(android.R.color.transparent))
        this.wbvNavegador.settings.javaScriptEnabled = true
        this.wbvNavegador.settings.allowFileAccess = true
        this.wbvNavegador.settings.setSupportZoom(true)
        this.wbvNavegador.settings.domStorageEnabled = true
        this.wbvNavegador.settings.setAppCacheEnabled(true)
        this.wbvNavegador.settings.allowContentAccess = true
        this.wbvNavegador.webViewClient = CustomWebViewClient(this@WebActivity, pbLoading, edtUrl)
        this.wbvNavegador.loadUrl(getString(R.string.url_padrao))

        ibtnPesquisar.setOnClickListener {
            pesquisar()
        }

        ibtnAvancar.setOnClickListener {
            avancar()
        }

        ibtnHome.setOnClickListener {
            home()
        }

        ibtnVoltar.setOnClickListener {
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

        if (!Patterns.WEB_URL.matcher(url).matches()){
            showMessage("Url não é valida")
            wbvNavegador.loadUrl("${getString(R.string.url_padrao)}/search?q=$url")
        } else {

            if (!url.startsWith("http") && !url.startsWith("https")) {
                url = "http://$url"
            }
            Log.d(WEBACTIVITY, "host: $url")
            wbvNavegador.loadUrl(url)
        }
    }

    fun showMessage(msg: String){
        Toast.makeText(
            this@WebActivity,
            msg,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun voltar() {
        if (wbvNavegador.canGoBack()) {
            wbvNavegador.goBack()
        } else {
            showMessage("Ops! Você não tem para onde voltar ;)")
        }
    }

    private fun avancar(){
        if(wbvNavegador.canGoForward()) {
            wbvNavegador.goForward()
        } else {
            showMessage("Ops! Você não tem para onde avançar :/")
        }
    }

    private fun home(){
        wbvNavegador.loadUrl(getString(R.string.url_padrao))
    }

    override fun onBackPressed() {
        if (wbvNavegador.canGoBack()){
            wbvNavegador.goBack()
        } else {
            super.onBackPressed()
        }
    }
}

fun View.hideKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
