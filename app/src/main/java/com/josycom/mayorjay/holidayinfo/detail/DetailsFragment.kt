package com.josycom.mayorjay.holidayinfo.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.josycom.mayorjay.holidayinfo.R
import com.josycom.mayorjay.holidayinfo.databinding.FragmentDetailsBinding
import com.josycom.mayorjay.holidayinfo.network.HolidayApiStatus
import com.josycom.mayorjay.holidayinfo.network.model.HolidayRequest
import com.josycom.mayorjay.holidayinfo.util.Constants

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
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
        displayHolidays()
        observeStatus()
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

    private fun displayHolidays() {
        binding.apply {
            rvHolidays.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = holidayAdapter
            }
        }
        viewModel.holidays.observe(viewLifecycleOwner, { holidays ->
            holidayAdapter.submitList(holidays)
        })
    }

    private fun observeStatus() {
        viewModel.status.observe(viewLifecycleOwner, { status ->
            when (status) {
                HolidayApiStatus.LOADING -> {
                    binding.tvStatus.visibility = View.GONE
                    binding.ivStatus.visibility = View.VISIBLE
                    binding.ivStatus.setImageDrawable(
                        resources.getDrawable(
                            R.drawable.loading_animation,
                            null
                        )
                    )
                }

                HolidayApiStatus.ERROR -> {
                    binding.tvStatus.visibility = View.VISIBLE
                    binding.tvStatus.text = getString(R.string.network_error_message)
                    binding.ivStatus.visibility = View.VISIBLE
                    binding.ivStatus.setImageDrawable(
                        resources.getDrawable(
                            R.drawable.ic_connection_error,
                            null
                        )
                    )
                }

                else -> {
                    binding.tvStatus.visibility = View.GONE
                    binding.ivStatus.visibility = View.GONE
                }
            }
        })
    }
}