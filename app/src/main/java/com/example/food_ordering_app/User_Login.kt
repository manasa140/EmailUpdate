package com.example.food_ordering_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class User_Login : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        val email = findViewById<TextInputLayout>(R.id.userEmail)
        val password = findViewById<TextInputLayout>(R.id.userPassword)
        val button = findViewById<Button>(R.id.button)
        val regbutton = findViewById<Button>(R.id.button2)

        val errorMessage = findViewById<TextView>(R.id.errorView)
        val registernav = findViewById<Button>(R.id.button2)

        val inputemailname = MutableLiveData<String>()

        val inputPassword = MutableLiveData<String>()

        val profile=findViewById<TextView>(R.id.DisplayUserEmail)
        val intent = Intent(this, update_email::class.java)
        val intent1 = Intent(this, MainActivity::class.java)
        //apiClient = ApiClient()
        val apiClient=application as ApiClient
       sessionManager = SessionManager(this,)

        button.setOnClickListener {
        CoroutineScope(Dispatchers.IO).launch {
           var result=apiClient.apiService.login(LoginRequest(email.editText?.text?.toString(),password.editText?.text?.toString()))

            withContext(Dispatchers.Main) {
                if(result.isSuccessful){
                    sessionManager.saveAuthToken(result.body()?.token)

                    intent.putExtra("string",result.body()?.token)
                    startActivity(intent)

                    Toast.makeText(this@User_Login,"Login successfull",Toast.LENGTH_LONG).show()
                }
                else
                    Toast.makeText(this@User_Login,"Invalid Credential",Toast.LENGTH_LONG).show()
               }
        }
    }

        regbutton.setOnClickListener {
            startActivity(intent1)
        }
    }


    }

