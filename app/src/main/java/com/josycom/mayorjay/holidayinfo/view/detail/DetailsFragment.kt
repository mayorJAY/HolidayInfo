package com.josycom.mayorjay.holidayinfo.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.josycom.mayorjay.holidayinfo.R
import com.josycom.mayorjay.holidayinfo.databinding.FragmentDetailsBinding
import com.josycom.mayorjay.holidayinfo.model.local.HolidayLocal
import com.josycom.mayorjay.holidayinfo.model.remote.HolidayApi
import com.josycom.mayorjay.holidayinfo.model.remote.HolidayApiResult
import com.josycom.mayorjay.holidayinfo.model.remote.RemoteHolidayRepositoryImpl
import com.josycom.mayorjay.holidayinfo.model.remote.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.model.util.Constants
import com.josycom.mayorjay.holidayinfo.viemodel.DetailsViewModel
import com.josycom.mayorjay.holidayinfo.viemodel.ViewModelProviderFactory

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels {
        ViewModelProviderFactory(RemoteHolidayRepositoryImpl(HolidayApi.retrofitService))
    }
    private lateinit var binding: FragmentDetailsBinding
    private val holidayAdapter by lazy { HolidayAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.holidays, arguments?.getString(Constants.COUNTRY_NAME_KEY) ?: "")

        setupListener()
        getHolidays()
        setupRecyclerview()
        observeResult()
    }

    private fun setupListener() {
        binding.ivStatus.setOnClickListener {
            getHolidays()
        }
    }

    private fun getHolidays() {
        val code = arguments?.getString(Constants.COUNTRY_CODE_KEY) ?: ""
        val year = arguments?.getString(Constants.YEAR_KEY) ?: ""
        viewModel.getHolidays(HolidayRequest(countryCode = code, year = year))
    }

    private fun setupRecyclerview() {
        binding.apply {
            rvHolidays.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = holidayAdapter
            }
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
                    holidayAdapter.submitList(result.data as List<HolidayLocal>)
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
}