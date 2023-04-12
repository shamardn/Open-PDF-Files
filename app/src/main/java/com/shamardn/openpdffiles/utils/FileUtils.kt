package com.shamardn.openpdffiles.utils

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat
import java.io.File

object FileUtils {
    fun getPdfUrl(): String {
        return "https://www.awqaf.gov.ae/DigitalLibrary/%D8%A7%D8%B0%D9%83%D8%A7%D8%B1%20%D8%A7%D9%84%D8%B5%D8%A8%D8%A7%D8%AD%20%D9%88%D8%A7%D9%84%D9%85%D8%B3%D8%A7%D8%A1.pdf"
    }

    fun getPdfNameFromAssets(): String {
        return "Azkar.pdf"
    }

    fun getRootDirPath(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val file: File = ContextCompat.getExternalFilesDirs(
                context.applicationContext,
                null
            )[0]
            file.absolutePath
        } else {
            context.applicationContext.filesDir.absolutePath
        }
    }
}