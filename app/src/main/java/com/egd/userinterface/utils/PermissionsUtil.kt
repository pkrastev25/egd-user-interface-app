package com.egd.userinterface.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

/**
 * @author Petar Krastev
 */
object PermissionsUtil {

    fun areRecordAudioPermissionsGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }
}