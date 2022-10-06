package com.josycom.mayorjay.holidayinfo.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.josycom.mayorjay.holidayinfo.R
import com.josycom.mayorjay.holidayinfo.databinding.FragmentDetailsBinding
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.util.Constants
import com.josycom.mayorjay.holidayinfo.viemodel.DetailsViewModel
import com.josycom.mayorjay.holidayinfo.state.UIState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var binding: FragmentDetailsBinding
    @Inject
    lateinit var holidayAdapter: HolidayAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.holidays, arguments?.getString(
            Constants.COUNTRY_NAME_KEY) ?: "")

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
        viewModel.getUiState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.tvStatus.isVisible = false
                    binding.ivStatus.isVisible = true
                    binding.ivStatus.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.loading_animation, null))
                }

                is UIState.Success -> {
                    holidayAdapter.submitList(state.data)
                    binding.tvStatus.isVisible = false
                    binding.ivStatus.isVisible = false
                }

                is UIState.Error -> {
                    binding.tvStatus.isVisible = true
                    binding.tvStatus.text = getString(R.string.network_error_message)
                    binding.ivStatus.isVisible = true
                    binding.ivStatus.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_connection_error, null))
                }
            }
        }
    }
}