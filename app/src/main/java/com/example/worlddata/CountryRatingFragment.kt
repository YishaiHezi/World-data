package com.example.worlddata

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
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
class CountryRatingFragment : Fragment(R.layout.country_rating_fragment), OnCountryClickListener {


	/**
	 * The view model for this fragment.
	 */
	private val viewModel: CountryRatingViewModel by viewModels()


// todo: uncomment this for updating the DB. Delete at the end.
//	override fun onCreate(savedInstanceState: Bundle?) {
//		super.onCreate(savedInstanceState)
//
//		context?.let { viewModel.startDb(it) }
//	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val spinner: Spinner = view.findViewById(R.id.spinner)
		initSpinner(spinner)

		val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
		val adapter = initRecyclerView(recyclerView)

		val editText: EditText = view.findViewById(R.id.edit_text)
		initEditText(editText, adapter)

		viewLifecycleOwner.lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.countriesFlow.collect { countries -> adapter.updateCountries(countries) }
			}
		}
	}


	/**
	 * Initialize the spinner.
	 */
	private fun initSpinner(spinner: Spinner) {
		val titleToParameter = ParameterType.entries.associateBy { it.title }
		val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, ParameterType.entries.map { it.title })
		spinner.adapter = adapter

		spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				val title = parent?.getItemAtPosition(position)
				val parameter = titleToParameter[title]
				viewModel.setSelectedParameter(parameter)
			}

			override fun onNothingSelected(parent: AdapterView<*>?) {
				viewModel.setSelectedParameter(ParameterType.POPULATION)
			}

		}
	}


	/**
	 * Initialize the recycler view.
	 */
	private fun initRecyclerView(recyclerView: RecyclerView) : CountriesListAdapter {
		val adapter = CountriesListAdapter(this)
		recyclerView.adapter = adapter
		addDividers(recyclerView)

		return adapter
	}


	/**
	 * Initialize the edit text.
	 */
	private fun initEditText(editText: EditText, adapter: CountriesListAdapter){
		editText.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				adapter.filterCountries(s.toString())
			}

			override fun afterTextChanged(s: Editable?) {}
		})
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


	override fun onCountryClicked(context: Context, country: CountryItem) {
		startActivity(CountryActivity.createStartIntent(context, country.code))
	}


	/**
	 * The adapter for the recycler view that shows all the countries.
	 */
	private class CountriesListAdapter(val onCountryClickListener: OnCountryClickListener) :
		RecyclerView.Adapter<RecyclerView.ViewHolder>() {


		/**
		 * All the countries in the world.
		 */
		var allCountries: List<CountryItem> = emptyList()


		/**
		 * The countries to show after filtering.
		 */
		var countriesToDisplay: List<CountryItem> = emptyList()


		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
			val view = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)

			return CountryViewHolder(view)
		}


		override fun getItemCount(): Int {
			return countriesToDisplay.size
		}


		override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
			val countryItem = countriesToDisplay[position]
			val itemView = holder.itemView

			val positionView: TextView = itemView.findViewById(R.id.position)
			positionView.text = itemView.context.getString(R.string.position, position + 1)

			val flagView: ImageView = itemView.findViewById(R.id.flag)
			flagView.setImageResource(countryItem.flag)

			val nameView: TextView = itemView.findViewById(R.id.name)
			nameView.text = countryItem.name

			val parameterView: TextView = itemView.findViewById(R.id.parameter)
			parameterView.text = countryItem.value

			itemView.setOnClickListener {
				onCountryClickListener.onCountryClicked(itemView.context, countryItem)
			}
		}


		/**
		 * Update the countries list.
		 */
		fun updateCountries(newCountries: List<CountryItem>) {
			allCountries = newCountries
			countriesToDisplay = newCountries
			notifyDataSetChanged()
		}


		/**
		 * Filter the countries based on the query.
		 */
		fun filterCountries(query: String) {
			countriesToDisplay = if (query.isEmpty()) {
				allCountries
			} else {
				allCountries.filter { it.name.startsWith(query, ignoreCase = true) }
			}
			notifyDataSetChanged()
		}


		/**
		 * The view holder for the recycler view.
		 */
		class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
	}


}


/**
 * The interface for the click listener on a country.
 */
interface OnCountryClickListener{


	/**
	 * Runs when clicking on a country.
	 */
	fun onCountryClicked(context: Context, country: CountryItem)


}
