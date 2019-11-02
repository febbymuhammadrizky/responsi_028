package com.example.responsi_028

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.R
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.snackbar.Snackbar



class RegisterActivity {
    //Declaration EditTexts
    var editTextUserName: EditText
    var editTextEmail: EditText
    var editTextPassword: EditText

    //Declaration TextInputLayout
    var textInputLayoutUserName: TextInputLayout
    var textInputLayoutEmail: TextInputLayout
    var textInputLayoutPassword: TextInputLayout

    //Declaration Button
    var buttonRegister: Button

    //Declaration SqliteHelper
    var sqliteHelper: SqliteHelper

    protected fun onCreate(@Nullable savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        sqliteHelper = SqliteHelper(this)
        initTextViewLogin()
        initViews()
        buttonRegister.setOnClickListener {
            if (validate()) {
                val UserName = editTextUserName.text.toString()
                val Email = editTextEmail.text.toString()
                val Password = editTextPassword.text.toString()

                //Check in the database is there any user associated with  this email
                if (!sqliteHelper.isEmailExists(Email)) {

                    //Email does not exist now add new user to database
                    sqliteHelper.addUser(User(null, UserName, Email, Password))
                    Snackbar.make(
                        buttonRegister,
                        "User created successfully! Please Login ",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Handler().postDelayed({ finish() }, Snackbar.LENGTH_LONG.toLong())
                } else {

                    //Email exists with email input provided so show error user already exist
                    Snackbar.make(
                        buttonRegister,
                        "User already exists with same email ",
                        Snackbar.LENGTH_LONG
                    ).show()
                }


            }
        }
    }

    //this method used to set Login TextView click event
    private fun initTextViewLogin() {
        val textViewLogin = findViewById(R.id.textViewLogin) as TextView
        textViewLogin.setOnClickListener { finish() }
    }

    //this method is used to connect XML views to its Objects
    private fun initViews() {
        editTextEmail = findViewById(R.id.editTextEmail) as EditText
        editTextPassword = findViewById(R.id.editTextPassword) as EditText
        editTextUserName = findViewById(R.id.editTextUserName) as EditText
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword) as TextInputLayout
        textInputLayoutUserName = findViewById(R.id.textInputLayoutUserName) as TextInputLayout
        buttonRegister = findViewById(R.id.buttonRegister) as Button

    }

    //This method is used to validate input given by user
    fun validate(): Boolean {
        var valid = false

        //Get values from EditText fields
        val UserName = editTextUserName.text.toString()
        val Email = editTextEmail.text.toString()
        val Password = editTextPassword.text.toString()

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false
            textInputLayoutUserName.error = "Please enter valid username!"
        } else {
            if (UserName.length > 5) {
                valid = true
                textInputLayoutUserName.error = null
            } else {
                valid = false
                textInputLayoutUserName.error = "Username is to short!"
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false
            textInputLayoutEmail.error = "Please enter valid email!"
        } else {
            valid = true
            textInputLayoutEmail.error = null
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false
            textInputLayoutPassword.error = "Please enter valid password!"
        } else {
            if (Password.length > 5) {
                valid = true
                textInputLayoutPassword.error = null
            } else {
                valid = false
                textInputLayoutPassword.error = "Password is to short!"
            }
        }


        return valid
    }
}