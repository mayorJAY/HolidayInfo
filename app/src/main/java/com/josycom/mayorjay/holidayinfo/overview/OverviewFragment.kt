package com.josycom.mayorjay.holidayinfo.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.josycom.mayorjay.holidayinfo.R
import com.josycom.mayorjay.holidayinfo.databinding.FragmentOverviewBinding
import com.josycom.mayorjay.holidayinfo.databinding.YearListViewBinding
import com.josycom.mayorjay.holidayinfo.detail.DetailsFragment
import com.josycom.mayorjay.holidayinfo.login.LoginFragment
import com.josycom.mayorjay.holidayinfo.model.network.HolidayApi
import com.josycom.mayorjay.holidayinfo.model.network.HolidayApiStatus
import com.josycom.mayorjay.holidayinfo.model.network.RemoteHolidayRepositoryImpl
import com.josycom.mayorjay.holidayinfo.model.network.models.Country
import com.josycom.mayorjay.holidayinfo.util.Constants
import com.josycom.mayorjay.holidayinfo.util.switchFragment
import com.josycom.mayorjay.holidayinfo.viemodel.OverviewViewModel
import com.josycom.mayorjay.holidayinfo.viemodel.ViewModelProviderFactory

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by viewModels {
        ViewModelProviderFactory(RemoteHolidayRepositoryImpl(HolidayApi.retrofitService))
    }
    private lateinit var binding: FragmentOverviewBinding
    private val countryAdapter by lazy { CountryAdapter() }

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
        displayCountries()
        observeStatus()
    }

    private fun setupListener() {
        binding.ivStatus.setOnClickListener {
            viewModel.getCountries()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                performLogout()
            }
        })
    }

    private fun displayCountries() {
        binding.apply {
            rvCountries.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = countryAdapter
            }
        }
        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            countryAdapter.submitList(countries)
        }
        countryAdapter.setListener(listener)
    }

    private val listener = View.OnClickListener { v ->
        val country = v?.tag as Country
        popUpYearDialog(country)
    }

    private fun popUpYearDialog(country: Country) {
        val binding = YearListViewBinding.inflate(layoutInflater)
        AlertDialog.Builder(requireContext()).create().apply {
            setView(binding.root)
            val yearList = mutableListOf<String>()
            for (i in 1922..2122) {
                yearList.add(i.toString())
            }
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
                val bundle = Bundle()
                bundle.putString(Constants.COUNTRY_CODE_KEY, country.code)
                bundle.putString(Constants.COUNTRY_NAME_KEY, country.name)
                if (viewModel.yearSelected != null) bundle.putString(
                    Constants.YEAR_KEY,
                    viewModel.yearSelected
                )
                switchFragment(DetailsFragment(), bundle, true)
                dismiss()
            }
            show()
        }
    }

    private fun observeStatus() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                HolidayApiStatus.LOADING -> {
                    binding.tvStatus.visibility = View.GONE
                    binding.ivStatus.visibility = View.VISIBLE
                    binding.ivStatus.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.loading_animation, null))
                }

                HolidayApiStatus.ERROR -> {
                    binding.tvStatus.visibility = View.VISIBLE
                    binding.tvStatus.text = getString(R.string.network_error_message)
                    binding.ivStatus.visibility = View.VISIBLE
                    binding.ivStatus.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_connection_error, null))
                }

                else -> {
                    binding.tvStatus.visibility = View.GONE
                    binding.ivStatus.visibility = View.GONE
                }
            }
        }
    }

    fun performLogout() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.warning))
            setMessage(getString(R.string.do_you_want_to_logout))
            setNegativeButton(getString(R.string.cancel) ) { _, _ -> }
            setPositiveButton(getString(R.string.logout)) { _, _ ->
                switchFragment(LoginFragment(), null, false)
            }
            show()
        }
    }
}