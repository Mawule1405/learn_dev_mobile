package com.ing.helloing3.shared

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class  FileStorage{

    fun saveImageToInternalStorage(context: Context, uri: Uri, fileName: String): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val file = File(context.filesDir, fileName) // Nom du fichier dans le stockage interne
            val outputStream = FileOutputStream(file)

            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream?.read(buffer).also { bytesRead = it ?: -1 } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.close()
            inputStream?.close()

            file.absolutePath // Retourne le chemin où l'image est sauvegardée
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
