package com.example.carpool2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
                loginOrRegisterUser(email)
            } else {
                Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Check if the user exists, then log in or create a new user
    private fun loginOrRegisterUser(email: String) {
        val defaultPassword = "default_password"

        // Try signing in first
        auth.signInWithEmailAndPassword(email, defaultPassword).addOnCompleteListener { signInTask ->
            if (signInTask.isSuccessful) {
                // User signed in successfully
                val user = auth.currentUser
                if (user != null && user.isEmailVerified) {
                    navigateToHomePage()
                } else if (user != null && !user.isEmailVerified) {
                    sendEmailVerification(email)
                }
            } else {
                // If sign-in fails, try creating the user
                auth.createUserWithEmailAndPassword(email, defaultPassword)
                    .addOnCompleteListener { signUpTask ->
                        if (signUpTask.isSuccessful) {
                            sendEmailVerification(email)
                        } else {
                            Toast.makeText(
                                this,
                                "Failed to create user: ${signUpTask.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    private fun sendEmailVerification(email: String) {
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { verificationTask ->
            if (verificationTask.isSuccessful) {
                Toast.makeText(this, "Verification email sent to $email", Toast.LENGTH_LONG).show()
                checkEmailVerified()
            } else {
                Toast.makeText(this, "Failed to send verification email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkEmailVerified() {
        val user = auth.currentUser
        user?.reload()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (user != null && user.isEmailVerified) {
                    navigateToHomePage()
                } else {
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
