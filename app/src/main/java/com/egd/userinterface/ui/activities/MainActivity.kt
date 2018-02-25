package com.egd.userinterface.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.egd.userinterface.R
import com.egd.userinterface.utils.PermissionsUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!PermissionsUtil.areRecordAudioPermissionsGranted(this)) {
            requestPermissions()
        } else {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        request_permissions_button.setOnClickListener {
            requestPermissions()
        }
        close_application_button.setOnClickListener {
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MainActivity.PERMISSIONS_RECORD_AUDIO_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(Intent(this, MenuActivity::class.java))
                }
            }
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                MainActivity.PERMISSIONS_RECORD_AUDIO_REQUEST_CODE
        )
    }

    companion object {
        private const val PERMISSIONS_RECORD_AUDIO_REQUEST_CODE = 0
    }
}
