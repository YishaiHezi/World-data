package com.example.worlddata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch


/**
 * The fragment that displays a list of countries.
 *
 * @author Yishai Hezi
 */
class CountryRatingFragment : Fragment(R.layout.country_rating_fragment) {


	/**
	 * The view model for this fragment.
	 */
	private val viewModel: CountryRatingViewModel by viewModels()


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewLifecycleOwner.lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.countriesFlow.collect {
					view.findViewById<RecyclerView>(R.id.recycler_view).adapter = CountriesListAdapter(it)
				}
			}
		}
	}


	/**
	 * The adapter for the recycler view that shows all the countries.
	 */
	private class CountriesListAdapter(private val countries: List<CountryItem>) :
		RecyclerView.Adapter<RecyclerView.ViewHolder>() {
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
			val view = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)

			return CountryViewHolder(view)
		}

		override fun getItemCount(): Int {
			return countries.size
		}

		override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
			val countryItem = countries[position]
			val itemView = holder.itemView

			(position + 1).toString().also { itemView.findViewById<TextView>(R.id.position).text = it }
			itemView.findViewById<ImageView>(R.id.flag).setImageResource(countryItem.flag)
			itemView.findViewById<TextView>(R.id.name).text = countryItem.name
			itemView.findViewById<TextView>(R.id.parameter).text = countryItem.parameter.toString()
		}

		class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
	}


}
