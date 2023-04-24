package com.anywhere.takehome.ui.util

import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import com.anywhere.takehome.R
import com.bumptech.glide.Glide

object BindingAdapters {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: AppCompatImageView, url: String?){
        Glide
            .with(imageView.context)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .into(imageView)
    }

    @BindingAdapter("onQueryTextChanged")
    @JvmStatic
    fun setOnQueryTextChangedListener(view: SearchView, listener: OnQueryTextChangedListener?) {
        Log.d("BindingAdapters", "onQueryTextChanged called")
        view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listener?.onQueryTextChanged(newText ?: "")
                return true
            }
        })
    }

    interface OnQueryTextChangedListener {
        fun onQueryTextChanged(query: String)
    }
}

