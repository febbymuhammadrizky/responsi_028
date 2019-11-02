package com.example.responsi_028

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.Snackbar;
//import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.R
import com.google.android.material.textfield.TextInputLayout
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.snackbar.Snackbar
import sun.text.normalizer.ICUBinary.Authenticate



class LoginActivity {
    //Declaration EditTexts
    var editTextEmail: EditText
    var editTextPassword: EditText

    //Declaration TextInputLayout
    var textInputLayoutEmail: TextInputLayout
    var textInputLayoutPassword: TextInputLayout

    //Declaration Button
    var buttonLogin: Button

    //Declaration SqliteHelper
    var sqliteHelper: SqliteHelper

    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sqliteHelper = SqliteHelper(this)
        initCreateAccountTextView()
        initViews()

        //set click event of login button
        buttonLogin.setOnClickListener {
            //Check user input is correct or not
            if (validate()) {

                //Get values from EditText fields
                val Email = editTextEmail.text.toString()
                val Password = editTextPassword.text.toString()

                //Authenticate user
                val currentUser = sqliteHelper.Authenticate(User(null, null, Email, Password))

                //Check Authentication is successful or not
                if (currentUser != null) {
                    Snackbar.make(buttonLogin, "Successfully Logged in!", Snackbar.LENGTH_LONG)
                        .show()

                    //User Logged in Successfully Launch You home screen activity
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {

                    //User Logged in Failed
                    Snackbar.make(
                        buttonLogin,
                        "Failed to log in , please try again",
                        Snackbar.LENGTH_LONG
                    ).show()

                }
            }
        }


    }

    //this method used to set Create account TextView text and click event( maltipal colors
    // for TextView yet not supported in Xml so i have done it programmatically)
    private fun initCreateAccountTextView() {
        val textViewCreateAccount = findViewById(R.id.textViewCreateAccount) as TextView
        textViewCreateAccount.text =
            fromHtml("<font color='#ffffff'>I don't have account yet. </font><font color='#0c0099'>create one</font>")
        textViewCreateAccount.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    //this method is used to connect XML views to its Objects
    private fun initViews() {
        editTextEmail = findViewById(R.id.editTextEmail) as EditText
        editTextPassword = findViewById(R.id.editTextPassword) as EditText
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword) as TextInputLayout
        buttonLogin = findViewById(R.id.buttonLogin) as Button

    }

    //This method is for handling fromHtml method deprecation
    fun fromHtml(html: String): Spanned {
        val result: Spanned
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            result = Html.fromHtml(html)
        }
        return result
    }

    //This method is used to validate input given by user
    fun validate(): Boolean {
        var valid = false

        //Get values from EditText fields
        val Email = editTextEmail.text.toString()
        val Password = editTextPassword.text.toString()

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
