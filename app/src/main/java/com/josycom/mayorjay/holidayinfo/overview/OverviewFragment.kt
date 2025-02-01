package com.josycom.mayorjay.holidayinfo.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.josycom.mayorjay.holidayinfo.details.DetailsFragment
import com.josycom.mayorjay.holidayinfo.util.Constants
import com.josycom.mayorjay.holidayinfo.util.switchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                OverviewScreen { year, country ->
                    val bundle = Bundle().apply {
                        putString(Constants.COUNTRY_KEY, country)
                        putString(Constants.YEAR_KEY, year)
                    }
                    switchFragment(DetailsFragment(), bundle, true)
                }
            }
        }
    }
}