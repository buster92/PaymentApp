package com.andresgarrido.paymentapp.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.andresgarrido.paymentapp.R
import com.andresgarrido.paymentapp.databinding.FragmentAmountBinding
import com.andresgarrido.paymentapp.ui.main.PaymentViewModel

class AmountFragment : Fragment() {

    private val viewModel: PaymentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAmountBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)

        viewModel.validateShouldGoNext.observe(viewLifecycleOwner){ goNext -> validateData(goNext) }
        return binding.root
    }

    private fun validateData(goNext: Boolean) {
        if (viewModel.loadingVisibility.value == View.VISIBLE)
            return
        val amount = viewModel.getEnteredAmountDouble()
        if (amount <= 0) {
            viewModel.showError.postValue(context?.getString(R.string.error_enter_valid_amount))
            return
        }
        if (!viewModel.showError.value.isNullOrEmpty()) {
            return
        }
        if (goNext) viewModel.goNext()
        viewModel.showError.postValue("")
    }

}