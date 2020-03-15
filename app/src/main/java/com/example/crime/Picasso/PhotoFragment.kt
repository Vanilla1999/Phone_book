package com.example.crime.Picasso

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.crime.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.picasso.*


class PhotoFragment: Fragment(R.layout.picasso) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

        Picasso.with(context)
        .load(Photo.getSunsetPhotos()[0].url)
        .placeholder(R.drawable.hellow_android_disabled)
        .error(R.drawable.ic_action_name)
        .fit()
        .into(imageView)
}
}