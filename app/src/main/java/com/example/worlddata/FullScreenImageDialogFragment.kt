package com.example.worlddata

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide


/**
 * A dialog fragment that shows a full screen image.
 */
class FullScreenImageDialogFragment : DialogFragment(R.layout.full_screen_dialog_fragment) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.ImageDialog)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image: ImageView = view.findViewById(R.id.image)
        val url = arguments?.getString("url")
        val resource = arguments?.getInt("resource")

        if (url != null) {
            Glide.with(this).load(url).into(image)
        } else if (resource != null) {
            image.setImageResource(resource)
        }

    }


    companion object {


        /**
         * Creates a new instance of the fragment from the given image resource.
         */
        fun newInstance(@DrawableRes imageResource: Int): FullScreenImageDialogFragment {
            val fragment = FullScreenImageDialogFragment()
            val args = Bundle()

            args.putInt("resource", imageResource)
            fragment.arguments = args

            return fragment
        }


        /**
         * Creates a new instance of the fragment from the given image url.
         */
        fun newInstance(imageUrl: String): FullScreenImageDialogFragment {
            val fragment = FullScreenImageDialogFragment()
            val args = Bundle()

            args.putString("url", imageUrl)
            fragment.arguments = args

            return fragment
        }

    }


}