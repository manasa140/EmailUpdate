package com.example.food_ordering_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class update_email : AppCompatActivity() {
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_email)
        val emailchange = findViewById<TextInputLayout>(R.id.userEmail)
        val changebutton = findViewById<Button>(R.id.button)

        val apiClient = application as ApiClient
        session=SessionManager(this)

        val intent = Intent(this, User_Login::class.java)

        var token=session.fetchAuthToken()

        changebutton.setOnClickListener {
            val email = emailchange.editText?.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    var result = apiClient.apiService.change_email(
                        "Bearer "+token,
                        UpdateRequest (
                            email = email
                        )
                    )
                    if (result.isSuccessful) {
                        Toast.makeText(applicationContext, "Email Changed", Toast.LENGTH_SHORT).show()
                        startActivity(intent)

                    }

                }

            }
        }
    }
}