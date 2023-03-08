package com.josycom.mayorjay.holidayinfo.view.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.josycom.mayorjay.holidayinfo.R
import com.josycom.mayorjay.holidayinfo.databinding.FragmentOverviewBinding
import com.josycom.mayorjay.holidayinfo.databinding.YearListViewBinding
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.view.detail.DetailsFragment
import com.josycom.mayorjay.holidayinfo.util.Constants
import com.josycom.mayorjay.holidayinfo.util.Resource
import com.josycom.mayorjay.holidayinfo.util.switchFragment
import com.josycom.mayorjay.holidayinfo.viewmodel.OverviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    @Inject
    lateinit var countryAdapter: CountryAdapter
    private lateinit var binding: FragmentOverviewBinding
    private val viewModel: OverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.countries)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        getCountries()
        setupRecyclerview()
        observeResult()
    }

    private fun setupListener() {
        countryAdapter.setListener(listener)

        binding.ivStatus.setOnClickListener {
            viewModel.getCountries()
        }
    }

    private fun getCountries() {
        viewModel.getCountries()
    }

    private fun setupRecyclerview() {
        binding.apply {
            rvCountries.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = countryAdapter
            }
        }
    }

    private fun observeResult() {
        viewModel.getUiData().observe(viewLifecycleOwner) { result ->
            countryAdapter.submitList(result?.data)

            when (result) {
                is Resource.Loading -> {
                    binding.tvStatus.isVisible = false
                    binding.ivStatus.isVisible = result.data.isNullOrEmpty()
                    binding.ivStatus.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.loading_animation, null))
                }

                is Resource.Success -> {
                    binding.ivStatus.isVisible = false
                    binding.tvStatus.isVisible = false
                }

                else -> {
                    binding.tvStatus.isVisible = result?.data.isNullOrEmpty()
                    binding.tvStatus.text = getString(R.string.network_error_message)
                    binding.ivStatus.isVisible = result?.data.isNullOrEmpty()
                    binding.ivStatus.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_connection_error, null))
                }
            }
        }
    }

    private val listener = View.OnClickListener { v ->
        val country = v?.tag as Country
        popUpYearDialog(country)
    }

    private fun popUpYearDialog(country: Country) {
        val binding = YearListViewBinding.inflate(layoutInflater)
        AlertDialog.Builder(requireContext()).create().apply {
            setView(binding.root)
            val yearList = viewModel.getYearList()
            binding.spYear.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                yearList
            )
                .also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            binding.spYear.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel.yearSelected = p0?.getItemAtPosition(p2) as String?
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
            binding.btProceed.setOnClickListener {
                val bundle = Bundle().apply {
                    putSerializable(Constants.COUNTRY_KEY, country)
                    viewModel.yearSelected?.let { putString(Constants.YEAR_KEY, it) }
                }
                switchFragment(DetailsFragment(), bundle, true)
                dismiss()
            }
            show()
        }
    }
}