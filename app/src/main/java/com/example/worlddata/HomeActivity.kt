package com.example.worlddata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit


/**
 * The main screen of the application.
 *
 * @author Yishai Hezi
 */
class HomeActivity : AppCompatActivity(R.layout.home_activity) {


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

//		val mapButton = findViewById<Button>(R.id.map_button) // todo: according to the button show the required fragment.
		// according to the button:
		val fragment = createFragment()
		populateFragment(fragment)
	}


	/**
	 * Creates the required fragment to be shown.
	 */
	private fun createFragment(): Fragment{
		return CountryRatingFragment()
	}


	/**
	 * Populates the fragment container with the required fragment.
	 */
	private fun populateFragment(fragment: Fragment) {
		supportFragmentManager.commit {
			replace(R.id.fragment_container, fragment)
		}
	}


}

