package com.andresgarrido.paymentapp.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.andresgarrido.paymentapp.databinding.FragmentPaymentMethodBinding
import com.andresgarrido.paymentapp.ui.main.PaymentViewModel

class PaymentMethodFragment : Fragment() {

    private val viewModel: PaymentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPaymentMethodBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)

        viewModel.loadPaymentMethods()
        viewModel.validateShouldGoNext.observe(viewLifecycleOwner){ goNext -> validateData(goNext) }
        return binding.root
    }

    private fun validateData(goNext: Boolean) {
        if (viewModel.selectedPaymentMethod.value != null) {
            viewModel.showError.postValue("")
        } else {
            viewModel.showError.postValue("Debes seleccionar un método de pago")
        }
        if (goNext) viewModel.goNext()
    }
}