package com.example.worlddata

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng


/**
 * Shows a remote image in full screen (support svg files).
 */
class FullScreenImageFragment : Fragment(R.layout.full_screen_image_fragment), OnMapReadyCallback {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar: ProgressBar = view.findViewById(R.id.loader)
        val imageView: ImageView = view.findViewById(R.id.map_image)
        val url = arguments?.getString("url")

        val imageLoader = ImageLoader.Builder(requireContext()).components { add(SvgDecoder.Factory()) }.build()
        imageView.load(url, imageLoader) {
            listener(
                onStart = { progressBar.visibility = View.VISIBLE },
                onSuccess = { _, _ -> progressBar.visibility = View.GONE },
                onError = { _, _ -> progressBar.visibility = View.GONE }
            )
            crossfade(true)
        }


        // todo: for google maps:
//        val mapFragment: SupportMapFragment? = childFragmentManager.findFragmentById(R.id.country_map) as? SupportMapFragment
//        mapFragment?.getMapAsync(this)
    }


    // todo: for google maps:
    override fun onMapReady(googleMap: GoogleMap) {
        Log.d(TAG, "onMapReady() called with: googleMap = $googleMap")


        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 2.0F))

    }


    companion object {


        /**
         * For logging.
         */
        const val TAG = "FullScreenImageFragment"


        /**
         * Creates a new instance of the fragment from the given image url.
         */
        fun newInstance(url: String): FullScreenImageFragment {
            val fragment = FullScreenImageFragment()
            val args = Bundle()

            args.putString("url", url)
            fragment.arguments = args

            return fragment
        }


    }


}