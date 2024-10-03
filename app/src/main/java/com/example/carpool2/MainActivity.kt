package com.example.carpool2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.carpool2.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        auth = FirebaseAuth.getInstance()


        activityMainBinding.btnSubmit.setOnClickListener {
            val email = activityMainBinding.emailEditText.text.toString().trim()
            if (email.isNotEmpty()) {
                sendEmailVerification(email)
            } else {
                Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendEmailVerification(email: String) {
        auth.createUserWithEmailAndPassword(email, "default_password") // Firebase requires a password
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener { verificationTask ->
                        if (verificationTask.isSuccessful) {
                            Toast.makeText(this, "Verification email sent to $email", Toast.LENGTH_LONG).show()
                            checkEmailVerified()
                        } else {
                            Toast.makeText(this, "Failed to send verification email", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Failed to create user: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }

    }

//    private fun checkEmailVerified() {
//        val user = auth.currentUser
//        user?.reload()?.addOnCompleteListener {
//            if (user.isEmailVerified) {
//                navigateToHomePage()
//            } else {
//                Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun checkEmailVerified() {
        val user = auth.currentUser
        user?.reload()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (user.isEmailVerified && user != null) {
                    navigateToHomePage()
                } else {
//                    Toast.makeText(this, "Please verify your email. Checking again in 5 seconds...", Toast.LENGTH_SHORT).show()
                    // Recheck after a delay (e.g., 5 seconds)
                    Handler().postDelayed({ checkEmailVerified() }, 5000)

                }
            } else {
                Toast.makeText(this, "Failed to reload user", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun navigateToHomePage() {
        val intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.isEmailVerified) {
            navigateToHomePage()
        }
    }
}