package com.shamardn.openpdffiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.shamardn.openpdffiles.databinding.ActivityWebViewBinding
import com.shamardn.openpdffiles.utils.FileUtils

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.setSupportZoom(true)
            settings.javaScriptEnabled = true
            val url = FileUtils.getPdfUrl()
            loadUrl("https://docs.google.com/gview?embedded=true&url=$url")
        }
    }
}