package com.example.worlddata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import data.ParameterType
import kotlinx.coroutines.launch


/**
 * The fragment that displays a list of countries.
 *
 * @author Yishai Hezi
 */
@AndroidEntryPoint
class CountryRatingFragment : Fragment(R.layout.country_rating_fragment) {


	/**
	 * The view model for this fragment.
	 */
	private val viewModel: CountryRatingViewModel by viewModels()


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val spinner: Spinner = view.findViewById(R.id.spinner)
		initSpinner(spinner)

		val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

		viewLifecycleOwner.lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.countriesFlow.collect { countries -> updateCountries(recyclerView, countries) }
			}
		}
	}


	/**
	 * Initialize the spinner.
	 */
	private fun initSpinner(spinner: Spinner) {
		val adapter = ArrayAdapter(requireContext(), R.layout.spinner_layout, ParameterType.entries)
		adapter.setDropDownViewResource(R.layout.spinner_item)
		spinner.adapter = adapter

		spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				val selectedParameter = parent?.getItemAtPosition(position) as ParameterType
				viewModel.setSelectedParameter(selectedParameter)
			}

			override fun onNothingSelected(parent: AdapterView<*>?) {
				viewModel.setSelectedParameter(ParameterType.POPULATION)
			}

		}
	}


	/**
	 * Update the countries list.
	 */
	private fun updateCountries(recyclerView: RecyclerView, countries: List<CountryItem>){
		val adapter = (recyclerView.adapter as? CountriesListAdapter)

		if (adapter == null) {
			initRecyclerView(recyclerView, countries)
		} else {
			adapter.updateCountries(countries)
		}
	}


	/**
	 * Initialize the recycler view.
	 */
	private fun initRecyclerView(recyclerView: RecyclerView, countries: List<CountryItem>) {
		recyclerView.adapter = CountriesListAdapter(countries)
		addDividers(recyclerView)
	}


	/**
	 * Add dividers between elements to the recyclerView.
	 */
	private fun addDividers(recyclerView: RecyclerView?) {
		val context = recyclerView?.context ?: return
		val divider = ContextCompat.getDrawable(context, R.drawable.divider_horizontal)
		val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
			if (divider != null) {
				setDrawable(divider)
			}
		}

		recyclerView.addItemDecoration(dividerItemDecoration)
	}


	/**
	 * The adapter for the recycler view that shows all the countries.
	 */
	private class CountriesListAdapter(var countries: List<CountryItem>) :
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

			val positionView: TextView = itemView.findViewById(R.id.position)
			positionView.text = itemView.context.getString(R.string.position, position + 1)

			val flagView: ImageView = itemView.findViewById(R.id.flag)
			flagView.setImageResource(countryItem.flag)

			val nameView: TextView = itemView.findViewById(R.id.name)
			nameView.text = countryItem.name

			val parameterView: TextView = itemView.findViewById(R.id.parameter)
			parameterView.text = countryItem.parameter.toString()
		}


		/**
		 * Update the countries list.
		 */
		fun updateCountries(newCountries: List<CountryItem>) {
			countries = newCountries
			notifyDataSetChanged()
		}


		/**
		 * The view holder for the recycler view.
		 */
		class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
	}


}
