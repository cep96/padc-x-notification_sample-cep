package com.cep.notificationsample.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

fun getBitmap(context: Context, imgResId: Int): Bitmap?{

    val drawable: Drawable? = ContextCompat.getDrawable(context, imgResId)

    return drawable?.toBitmap()
}