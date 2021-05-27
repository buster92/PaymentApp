package com.andresgarrido.paymentapp.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.andresgarrido.paymentapp.databinding.FragmentSummaryBinding
import com.andresgarrido.paymentapp.ui.main.PaymentViewModel

class SummaryFragment : Fragment() {

    private val viewModel: PaymentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSummaryBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)

        viewModel.validateShouldGoNext.observe(viewLifecycleOwner){ goNext -> validateData(goNext) }
        return binding.root
    }

    private fun validateData(goNext: Boolean) {
        if (goNext) viewModel.goNext()
    }
}