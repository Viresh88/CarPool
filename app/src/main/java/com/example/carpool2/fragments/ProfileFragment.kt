package com.example.carpool2.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.carpool2.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using ViewBinding
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Change Profile Photo on Click
        binding.tvChangePhoto.setOnClickListener {
            chooseImageFromGallery()
        }

        // Save Button Action
        binding.btnSave.setOnClickListener {
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
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
            binding.ivProfilePicture.setImageBitmap(bitmap)
        }
    }

    // Save Profile Details
    private fun saveProfileDetails() {
        val fullName = binding.etFullName.text.toString()
        val email = binding.etEmailAddress.text.toString()
        val phone = binding.etPhoneNumber.text.toString()
        val password = binding.etPassword.text.toString()

        // Here you can save the details to your server or database
        Toast.makeText(requireContext(), "Profile Details Saved!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
