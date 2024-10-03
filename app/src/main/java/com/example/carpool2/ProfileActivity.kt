package com.example.carpool2;



import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProfileActivity : AppCompatActivity() {

  private lateinit var ivProfilePicture: ImageView
  private lateinit var tvChangePhoto: TextView
  private lateinit var etFullName: EditText
  private lateinit var etEmailAddress: EditText
  private lateinit var etPhoneNumber: EditText
  private lateinit var etPassword: EditText
  private lateinit var btnSave: Button

  companion object {
   private const val PICK_IMAGE_REQUEST = 1
  }

  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
   super.onCreate(savedInstanceState)
   setContentView(R.layout.activity_profile)

   // Initialize Views
   ivProfilePicture = findViewById(R.id.iv_profile_picture)
   tvChangePhoto = findViewById(R.id.tv_change_photo)
   etFullName = findViewById(R.id.et_full_name)
   etEmailAddress = findViewById(R.id.et_email_address)
   etPhoneNumber = findViewById(R.id.et_phone_number)
   etPassword = findViewById(R.id.et_password)
   btnSave = findViewById(R.id.btn_save)

   val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
      bottomNavigationView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.primary_dark)
   // Set the correct menu item as selected
   bottomNavigationView.selectedItemId = R.id.profile

   bottomNavigationView.setOnNavigationItemSelectedListener { item ->
    when (item.itemId) {
     R.id.home -> {
      val intent = Intent(this, HomePageActivity::class.java)
      startActivity(intent)
         finish()
      true
     }

        R.id.chat -> {
            val intent = Intent(this, UserListActivity::class.java)
            startActivity(intent)
            finish()
            true
        }

     R.id.myRides -> {
      val intent = Intent(this, MyRidesActivity::class.java)
      startActivity(intent)
         finish()
      true
     }

     R.id.requestRide -> {
         val intent = Intent(this, RequestRideActivity::class.java)
         startActivity(intent)
         finish()
      true
     }

        R.id.profile->{
            true
        }

     else -> false
    }
   }


   // Change Profile Photo on Click
   tvChangePhoto.setOnClickListener {
    chooseImageFromGallery()
   }

   // Save Button Action
   btnSave.setOnClickListener {
    saveProfileDetails()
   }
  }

  // Function to Choose Image
  private fun chooseImageFromGallery() {
   val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
   startActivityForResult(intent, PICK_IMAGE_REQUEST)
  }

  // Handle Image Selection Result
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
   super.onActivityResult(requestCode, resultCode, data)
   if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
    val imageUri: Uri = data.data!!
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
    ivProfilePicture.setImageBitmap(bitmap)
   }
  }

    override fun onBackPressed() {
        super.onBackPressed()
        // Navigate to HomeActivity when back button is pressed
        val intent = Intent(this, HomePageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish() // Finish the current activity
    }

  // Save Profile Details
  private fun saveProfileDetails() {
   val fullName = etFullName.text.toString()
   val email = etEmailAddress.text.toString()
   val phone = etPhoneNumber.text.toString()
   val password = etPassword.text.toString()

   // Here you can save the details to your server or database
   Toast.makeText(this, "Profile Details Saved!", Toast.LENGTH_SHORT).show()
  }
 }
