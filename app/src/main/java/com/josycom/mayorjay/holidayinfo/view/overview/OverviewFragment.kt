package com.josycom.mayorjay.holidayinfo.view.overview

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
import com.josycom.mayorjay.holidayinfo.model.local.CountryLocal
import com.josycom.mayorjay.holidayinfo.view.detail.DetailsFragment
import com.josycom.mayorjay.holidayinfo.view.login.LoginFragment
import com.josycom.mayorjay.holidayinfo.model.remote.HolidayApi
import com.josycom.mayorjay.holidayinfo.model.remote.HolidayApiResult
import com.josycom.mayorjay.holidayinfo.model.remote.RemoteHolidayRepositoryImpl
import com.josycom.mayorjay.holidayinfo.model.util.Constants
import com.josycom.mayorjay.holidayinfo.model.util.switchFragment
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
        setupRecyclerview()
        observeResult()
    }

    private fun setupListener() {
        countryAdapter.setListener(listener)

        binding.ivStatus.setOnClickListener {
            viewModel.getCountries()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                performLogout()
            }
        })
    }

    private fun setupRecyclerview() {
        binding.apply {
            rvCountries.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = countryAdapter
            }
        }
    }

    private val listener = View.OnClickListener { v ->
        val country = v?.tag as CountryLocal
        popUpYearDialog(country)
    }

    private fun popUpYearDialog(country: CountryLocal) {
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

    private fun observeResult() {
        viewModel.apiResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is HolidayApiResult.Loading -> {
                    binding.tvStatus.visibility = View.GONE
                    binding.ivStatus.visibility = View.VISIBLE
                    binding.ivStatus.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.loading_animation, null))
                }

                is HolidayApiResult.Success -> {
                    countryAdapter.submitList(result.data as List<CountryLocal>)
                    binding.tvStatus.visibility = View.GONE
                    binding.ivStatus.visibility = View.GONE
                }

                is HolidayApiResult.Error -> {
                    binding.tvStatus.visibility = View.VISIBLE
                    binding.tvStatus.text = getString(R.string.network_error_message)
                    binding.ivStatus.visibility = View.VISIBLE
                    binding.ivStatus.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_connection_error, null))
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