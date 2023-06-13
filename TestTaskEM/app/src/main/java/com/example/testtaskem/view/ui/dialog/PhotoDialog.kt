package com.example.testtaskem.view.ui.dialog

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.domain.common.Constants.SHARED_PREF_PROFILE_PHOTO
import com.example.testtaskem.R
import com.example.testtaskem.databinding.PopupItemChangeAccountPhotoBinding
import com.example.testtaskem.view.ui.item.ProfilePhotoItem

class PhotoDialog(
    private val sharedPreferences: SharedPreferences,
    private val profilePhotoItem: ProfilePhotoItem
) : DialogFragment() {

    private var _binding: PopupItemChangeAccountPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopupItemChangeAccountPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val uriAsString = sharedPreferences.getString(SHARED_PREF_PROFILE_PHOTO, "") ?: ""
            if (uriAsString.isNotBlank()) {
                val photoUri = Uri.parse(uriAsString)
                updatePhoto(photoUri)
            } else {
                updatePhoto(R.drawable.ic_user_default_photo)
            }
            btnChangeImage.setOnClickListener {
                changeProfilePhoto()
            }
            ivExit.setOnClickListener {
                dialog!!.dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_dialog)
    }

    private fun updatePhoto(photo: Any) {
        Glide
            .with(requireContext())
            .load(photo)
            .centerCrop()
            .into(binding.ivPhoto)
    }

    private fun changeProfilePhoto() {
        val iGallery = Intent(Intent.ACTION_PICK)
        iGallery.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        galleryResultLauncher.launch(iGallery)
    }

    private val galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val photoUri = result.data?.data!!
                updatePhoto(photoUri)
                profilePhotoItem.changePhoto(photoUri)

                saveProfilePhotoToDatabase(photoUri)
            }
        }

    private fun saveProfilePhotoToDatabase(photoUri: Uri?) {
        with(sharedPreferences.edit()) {
            putString(SHARED_PREF_PROFILE_PHOTO, photoUri.toString())
            apply()
        }
    }
}