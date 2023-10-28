package com.example.proyecto

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.provider.AlarmClock
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var btnCallPhone: ImageButton
    private lateinit var btnOpenUrl: ImageButton
    private lateinit var btnSetAlarm: ImageButton
    private lateinit var btnOpenEmail: ImageButton

    companion object {
        const val PHONE = "684101238"
        const val URL = "https://www.elmundo.es/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvent()
    }

    private fun initEvent() {
        btnCallPhone = findViewById(R.id.btn_call)
        btnCallPhone.setOnClickListener {
            requestPermissions()
        }

        btnOpenUrl = findViewById(R.id.btn_internet)
        btnOpenUrl.setOnClickListener {
            openUrl()
        }

        btnSetAlarm = findViewById(R.id.btn_alarm)
        btnSetAlarm.setOnClickListener {
            setAlarm()
        }

        btnOpenEmail = findViewById(R.id.btn_email)
        btnOpenEmail.setOnClickListener {
            openEmail()
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                openCallActivity()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        } else {
            openCallActivity()
        }
    }

    private fun openCallActivity() {
        val intent = Intent(this, CallActivity::class.java).apply {
            putExtra("phone_number", PHONE)
        }
        startActivity(intent)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCallActivity()
        } else {
            Toast.makeText(this, "Debes habilitar los permisos", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUrl() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URL))
        startActivity(intent)
    }

    private fun setAlarm() {
        val now = Calendar.getInstance()
        now.add(Calendar.MINUTE, 2)
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)

        val intent = Intent(AlarmClock.ACTION_SET_ALARM)
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Mensaje de alarma")
        intent.putExtra(AlarmClock.EXTRA_HOUR, hour)
        intent.putExtra(AlarmClock.EXTRA_MINUTES, minute)
        startActivity(intent)
    }

    private fun openEmail() {
        val emailIntent = packageManager.getLaunchIntentForPackage("com.google.android.gm")
        if (emailIntent != null) {
            startActivity(emailIntent)
        } else {
            Toast.makeText(this, "No se encuentra la aplicacion de gmail en el dispositivo.", Toast.LENGTH_SHORT).show()
        }
    }
}

