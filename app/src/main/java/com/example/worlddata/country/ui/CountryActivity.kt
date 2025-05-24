package com.example.worlddata.country.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.worlddata.FullScreenImageDialogFragment
import com.example.worlddata.FullScreenImageFragment
import com.example.worlddata.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import com.example.worlddata.country.model.FormattedCountry
import kotlinx.coroutines.launch


/**
 * Shows data about a specific country.
 */
@AndroidEntryPoint
class CountryActivity : AppCompatActivity(R.layout.country_activity) {


    /**
     * The view model for this activity.
     */
    private val viewModel: CountryViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.countryFlow.collect { country ->
                    updateUi(country)
                }
            }
        }
    }


    /**
     * Update the UI from the given country.
     */
    private fun updateUi(country: FormattedCountry) {
        updateFlag()
        updateCoatOfArms(country)
        updateName(country)
        updatePager(country)
    }


    /**
     * Initialize the flag image from the given flag resource.
     */
    private fun updateFlag(){
        val flag = viewModel.flag
        val flagView: ImageView = findViewById(R.id.flag)

        flagView.setImageResource(flag)
        flagView.setOnClickListener {
            FullScreenImageDialogFragment.newInstance(flag).show(supportFragmentManager, "fullScreenImageDialog")
        }
    }


    /**
     * Initialize the coat of arms image and name from the given coat of arms url.
     */
    private fun updateCoatOfArms(country: FormattedCountry) {
        val coatOfArmsUrl = country.coatOfArms
        val coatOfArmsView: ImageView = findViewById(R.id.coat_of_arms)
        val coatOfArmsNameView: TextView = findViewById(R.id.coat_of_arms_name)

        loadCoatOfArms(coatOfArmsView, coatOfArmsNameView, coatOfArmsUrl)
        coatOfArmsView.setOnClickListener {
            FullScreenImageDialogFragment.newInstance(coatOfArmsUrl).show(supportFragmentManager, "fullScreenImageDialog")
        }
    }


    /**
     * Initialize the coat of arms image and name from the given country.
     */
    private fun loadCoatOfArms(coatOfArmsView: ImageView, coatOfArmsNameView: TextView, coatOfArmsUrl: String) {
        Glide.with(this)
            .load(coatOfArmsUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    coatOfArmsView.visibility = View.GONE
                    coatOfArmsNameView.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    coatOfArmsView.visibility = View.VISIBLE
                    coatOfArmsNameView.visibility = View.VISIBLE
                    return false
                }

            })
            .into(coatOfArmsView)
    }


    /**
     * Initialize the country name from the given country.
     */
    private fun updateName(country: FormattedCountry){
        val nameView: TextView = findViewById(R.id.country_name)
        nameView.text = country.name
    }


    /**
     * Update the tab layout and view pager from the given country.
     */
    private fun updatePager(country: FormattedCountry){
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)

        viewPager.adapter = ViewPagerAdapter(this, country)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Information"
                1 -> tab.text = "Map"
            }
        }.attach()
    }


    /**
     * The adapter for the view pager.
     */
    class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val country: FormattedCountry) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CountryInformationFragment.newInstance(country)
                1 -> FullScreenImageFragment.newInstance("https://upload.wikimedia.org/wikipedia/commons/d/dc/USA_orthographic.svg") // todo: put here the real image
                else -> CountryInformationFragment.newInstance(country)
            }
        }
    }



    companion object {


        /**
         * Create a start intent for this activity.
         */
        fun createStartIntent(context: Context, countryCode: String): Intent {
            val intent = Intent(context, CountryActivity::class.java)
            intent.putExtra("country_code", countryCode)
            return intent
        }


    }



}