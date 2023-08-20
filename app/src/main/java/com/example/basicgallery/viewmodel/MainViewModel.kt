package com.example.basicgallery.viewmodel

import android.app.Application
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.basicgallery.model.Image

class MainViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val IMAGE_FOLDER = "%Pictures/%"
    }

    val allImage: MutableLiveData<ArrayList<Image>?> = MutableLiveData(null)

    fun getAllImage(): ArrayList<Image> {
        val images = ArrayList<Image>()
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val selection = MediaStore.Images.ImageColumns.RELATIVE_PATH + " like ? "
        val projection =
            arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)
        val selectionArgs = arrayOf(IMAGE_FOLDER)
        val sortOrder = MediaStore.MediaColumns.DATE_ADDED + " COLLATE NOCASE DESC"
        val cursor = getApplication<Application>().contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        try {
            cursor!!.moveToFirst()
            do {
                val image = Image()
                image.imagePath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                images.add(image)
            } while (cursor.moveToNext())
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return images
    }


}

