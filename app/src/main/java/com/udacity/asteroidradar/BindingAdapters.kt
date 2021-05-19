package com.udacity.asteroidradar

import android.opengl.Visibility
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.main.AsteroidAdapter

@BindingAdapter("loading")
fun bindProgressBar(progressBar: ProgressBar, visibility: Boolean) {
    if (visibility) {
        progressBar.visibility = View.VISIBLE
    } else {
        progressBar.visibility = View.GONE
    }
}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?) {
    val adapter = recyclerView.adapter as AsteroidAdapter
    adapter.submitList(data)
}

@BindingAdapter("pictureOfDayTitle")
fun bindPictureOfDayTitle(textView: TextView, title: String?) {
    if (title != null) {
        textView.text = title
    } else {
        val context = textView.context
        textView.text = context.getString(R.string.image_of_the_day_not_available)
    }
}

@BindingAdapter("imageContentDescription")
fun bindImageContentDescription(imageView: ImageView, title: String?) {
    val context = imageView.context
    if (title != null) {
        imageView.contentDescription = title
    } else {
        imageView.contentDescription = context.getString(R.string.image_of_the_day_not_available)
    }
}

@BindingAdapter("imgUrl")
fun bindImage(imageView: ImageView, imgUrl: String?) {
    Picasso.get()
        .load(imgUrl)
        .error(R.drawable.placeholder_image_not_available)
        .placeholder(R.drawable.placeholder_picture_of_day)
        .into(imageView)
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
