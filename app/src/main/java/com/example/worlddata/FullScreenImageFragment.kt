package com.example.worlddata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val continent = arguments?.getString("continent", "default") ?: "default"
        val map:ImageView = view.findViewById(R.id.map_image)
        map.setImageResource(getMapImage(continent))
    }


    /**
     * Return the correct map of the continent of the given country.
     */
    private fun getMapImage(continent: String): Int = when(continent){
        "North America" -> R.drawable.north_america
        "South America" -> R.drawable.south_america
        "Africa" -> R.drawable.africa
        "Asia" -> R.drawable.asia
        "Oceania" -> R.drawable.australia // todo: need another image for all the islands in the pecific
        "Europe" -> R.drawable.europe
        else -> R.drawable.africa
    }


    companion object{


        /**
         * Creates a new instance of this fragment.
         */
        fun newInstance(countryCode: String, continent: String?): FullScreenImageFragment {
            val fragment = FullScreenImageFragment()
            val args = Bundle()
            args.putString("country_code", countryCode)
            args.putString("continent", continent)
            fragment.arguments = args
            return fragment
        }

    }


}