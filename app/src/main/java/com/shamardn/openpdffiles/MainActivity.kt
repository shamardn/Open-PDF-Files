package com.shamardn.openpdffiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shamardn.openpdffiles.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpOnClickListener()
    }

    private fun setUpOnClickListener() {
        binding.btnWebView.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }
        binding.btnAssets.setOnClickListener {
            val intent = Intent(this, PdfViewActivity::class.java)
            intent.putExtra("ViewType", "assets")
            startActivity(intent)
        }
        binding.btnStorage.setOnClickListener {
            val intent = Intent(this, PdfViewActivity::class.java)
            intent.putExtra("ViewType", "storage")
            startActivity(intent)
        }
        binding.btnInternet.setOnClickListener {
            val intent = Intent(this, PdfViewActivity::class.java)
            intent.putExtra("ViewType", "internet")
            startActivity(intent)
        }
    }
}