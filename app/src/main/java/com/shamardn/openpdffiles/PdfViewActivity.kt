package com.shamardn.openpdffiles

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.shamardn.openpdffiles.databinding.ActivityPdfViewActivityBinding
import com.shamardn.openpdffiles.utils.FileUtils
import java.io.File

class PdfViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPdfViewActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PRDownloader.initialize(applicationContext)

        checkPdfAction(intent)
    }

    companion object {
        private const val PDF_SELECTION_CODE = 99
    }

    private fun downloadPdfFromInternet(url: String, dirPath: String, fileName: String) {
        PRDownloader.download(
            url,
            dirPath,
            fileName
        ).build()
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    Toast.makeText(this@PdfViewActivity, "downloadComplete", Toast.LENGTH_LONG)
                        .show()
                    val downloadedFile = File(dirPath, fileName)
                    binding.progressBar.visibility = View.GONE
                    showPdfFromFile(downloadedFile)
                }

                override fun onError(error: com.downloader.Error?) {
                    Toast.makeText(
                        this@PdfViewActivity,
                        "Error in downloading file : $error",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }

    private fun showPdfFromFile(file: File) {
        binding.pdfView.fromFile(file)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageError { page, _ ->
                Toast.makeText(
                    this@PdfViewActivity,
                    "Error at page: $page", Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }

    private fun selectPdfFromStorage() {
        Toast.makeText(this, "selectPDF", Toast.LENGTH_LONG).show()
        val browseStorage = Intent(Intent.ACTION_GET_CONTENT)
        browseStorage.type = "application/pdf"
        browseStorage.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(
            Intent.createChooser(browseStorage, "Select PDF"), PDF_SELECTION_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PDF_SELECTION_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedPdfFromStorage = data.data
            showPdfFromUri(selectedPdfFromStorage)
        }
    }

    private fun showPdfFromUri(uri: Uri?) {
        binding.pdfView.fromUri(uri)
            .defaultPage(0)
            .spacing(10)
            .load()
    }

    private fun checkPdfAction(intent: Intent) {
        when (intent.getStringExtra("ViewType")) {
            "assets" -> {
                showPdfFromAssets(FileUtils.getPdfNameFromAssets())
            }
            "storage" -> {
                selectPdfFromStorage()
            }
            "internet" -> {
                binding.progressBar.visibility = View.VISIBLE
                val fileName = "myFile.pdf"
                downloadPdfFromInternet(
                    FileUtils.getPdfUrl(),
                    FileUtils.getRootDirPath(this),
                    fileName
                )
            }
        }
    }

    private fun showPdfFromAssets(pdfName: String) {
        binding.pdfView.fromAsset(pdfName)
            .password(null) // if password protected, then write password
            .defaultPage(0) // set the default page to open
            .onPageError { page, _ ->
                Toast.makeText(
                    this@PdfViewActivity,
                    "Error at page: $page", Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }
}