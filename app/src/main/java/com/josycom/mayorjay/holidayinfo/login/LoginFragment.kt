package com.josycom.mayorjay.holidayinfo.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.josycom.mayorjay.holidayinfo.R
import com.josycom.mayorjay.holidayinfo.databinding.FragmentLoginBinding
import com.josycom.mayorjay.holidayinfo.overview.OverviewFragment
import com.josycom.mayorjay.holidayinfo.util.switchFragment
import com.josycom.mayorjay.holidayinfo.viemodel.LoginViewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.login)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObserver()
    }

    private fun setupListeners() {
        binding.etEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.email.value = p0?.toString()
                viewModel.canProceed.value = viewModel.validateInputs(viewModel.email.value ?: "", viewModel.password.value ?: "")
            }
        })

        binding.etPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.password.value = p0?.toString()
                viewModel.canProceed.value = viewModel.validateInputs(viewModel.email.value ?: "", viewModel.password.value ?: "")
            }
        })

        binding.btLogin.setOnClickListener {
            switchFragment(OverviewFragment(), null, false)
        }
    }

    private fun setupObserver() {
        viewModel.canProceed.observe(viewLifecycleOwner) { canProceed ->
            binding.btLogin.isEnabled = canProceed
        }
    }
}