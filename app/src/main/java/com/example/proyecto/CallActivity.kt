package com.example.proyecto

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.net.Uri

class CallActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        val phoneNumber = intent.getStringExtra("phone_number")
        val phoneNumberTextView = findViewById<TextView>(R.id.callText)
        phoneNumberTextView.text = "Vas a llamar a Víctor Ruiz del Moral"

        val confirmCallButton = findViewById<Button>(R.id.callButton2)
        confirmCallButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(callIntent)
        }
    }
}
