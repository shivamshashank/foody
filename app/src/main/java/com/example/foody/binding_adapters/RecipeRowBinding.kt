package com.example.foody.binding_adapters

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.foody.R


class RecipeRowBinding {

    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int){
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int){
            textView.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(textView: TextView, vegan: Boolean) {
            if(vegan){
                textView.setTextColor(
                    ContextCompat.getColor(
                        textView.context,
                        R.color.green,
                    )
                )
                for (drawable in textView.compoundDrawablesRelative) {
                    if (drawable != null) {
                        drawable.colorFilter =
                            PorterDuffColorFilter(
                                ContextCompat.getColor(
                                    textView.context,
                                    R.color.green,
                                ),
                                PorterDuff.Mode.SRC_IN
                            )
                    }
                }
            }
        }

    }

}