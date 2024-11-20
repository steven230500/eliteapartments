package com.example.eliteapartments.data

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream

fun Context.saveImageToAppStorage(uri: Uri): String? {
    return try {
        val inputStream = contentResolver.openInputStream(uri)
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val file = File(filesDir, fileName)
        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        file.absolutePath
    } catch (e: Exception) {
        Log.e("FileUtils", "Error saving image to storage: $uri", e)
        null
    }
}
