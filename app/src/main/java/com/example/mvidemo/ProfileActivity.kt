package com.example.mvidemo

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mvidemo.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import org.koin.android.ext.android.inject

class ProfileActivity : AppCompatActivity(), ProfileView {

    private lateinit var dialog: AlertDialog
    private lateinit var binding: ActivityProfileBinding

    // Lazy injected ProfilePresenter
    private val presenter: ProfilePresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dialog = SpotsDialog
            .Builder()
            .setContext(this)
            .build()

        presenter.bind(this)

        presenter.getUserProfile(getString(R.string.user_id))
    }

    override fun render(state: ProfileState) {
        when(state){
            is ProfileState.LoadingState -> renderLoadingState()
            is ProfileState.DataState -> renderDataState(state)
            is ProfileState.ErrorState -> renderErrorState(state)
            is ProfileState.FinishState -> renderFinishState()
        }
    }

    private fun renderLoadingState() {
        //Render progress bar on screen
        dialog.show()
    }

    private fun renderDataState(dataState: ProfileState.DataState) {
        //Render profile
        val user = dataState.data
        if(user.user_image.isNotEmpty())
        Picasso
            .get()
            .load(user.user_image)
            .resize(120, 120)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .into(binding.iv)
        binding.name.text = user.user_name
    }

    private fun renderErrorState(errorState: ProfileState.ErrorState) {
        //Display error mesage
        Toast
            .makeText(this,errorState.data,Toast.LENGTH_SHORT)
            .show()
    }

    private fun renderFinishState() {
        dialog.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

}
