package br.com.luana.snowfox

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        this.wbvNavegador.webViewClient = CustomWebViewClient()
        this.wbvNavegador.loadUrl("http://br.cellep.com")

    }
}
