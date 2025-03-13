package com.example.worlddata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.example.worlddata.ThemesMapper.getThemeResource


/**
 * Shows a remote image in full screen (support svg files).
 */
class FullScreenImageFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val countryCode = arguments?.getString("country_code", null)
        val themeResId = getThemeResource(countryCode)
        val contextThemeWrapper = ContextThemeWrapper(requireContext(), themeResId)
        val themedInflater = inflater.cloneInContext(contextThemeWrapper)
        return themedInflater.inflate(R.layout.full_screen_image_fragment, container, false)
    }


    companion object{


        /**
         * Creates a new instance of this fragment.
         */
        fun newInstance(countryCode: String): FullScreenImageFragment {
            val fragment = FullScreenImageFragment()
            val args = Bundle()
            args.putString("country_code", countryCode)
            fragment.arguments = args
            return fragment
        }

    }


}