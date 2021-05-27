package com.andresgarrido.paymentapp.ui

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.andresgarrido.paymentapp.model.CardIssuer
import com.andresgarrido.paymentapp.model.PayerCost
import com.andresgarrido.paymentapp.model.PaymentMethod
import com.andresgarrido.paymentapp.ui.adapter.CardIssuerAdapter
import com.andresgarrido.paymentapp.ui.adapter.PayerCostAdapter
import com.andresgarrido.paymentapp.ui.adapter.PaymentMethodAdapter


/**
 * fill the Spinner with all available projects.
 * Set the Spinner selection to selectedProject.
 * If the selection changes, call the InverseBindingAdapter
 */
@BindingAdapter(
        value = ["paymentMethodList", "selectedPaymentMethod", "selectedPaymentMethodAttrChanged"],
        requireAll = false
)
fun setPaymentMethodList(
        spinner: Spinner,
        paymentMethodList: List<PaymentMethod>?,
        selectedPaymentMethod: PaymentMethod?,
        listener: InverseBindingListener
) {
    if (paymentMethodList == null || paymentMethodList.isEmpty()) return
    if (spinner.adapter == null) {
        spinner.adapter = PaymentMethodAdapter(paymentMethodList)

        setCurrentSelection(spinner, paymentMethodList[0])
        setSpinnerListener(spinner, listener)
    }

    if (selectedPaymentMethod != null) {
        setCurrentSelection(spinner, selectedPaymentMethod)
    }
}

@InverseBindingAdapter(attribute = "selectedPaymentMethod")
fun getSelectedPaymentMethod(spinner: Spinner): PaymentMethod {
    return spinner.selectedItem as PaymentMethod
}

@BindingAdapter(
        value = ["cardIssuerList", "selectedCardIssuer", "selectedCardIssuerAttrChanged"],
        requireAll = false
)
fun setCardIssuerList(
        spinner: Spinner,
        cardIssuerList: List<CardIssuer>?,
        selectedCardIssuer: CardIssuer?,
        listener: InverseBindingListener
) {
    if (cardIssuerList == null || cardIssuerList.isEmpty()) return
    if (spinner.adapter == null) {
        spinner.adapter = CardIssuerAdapter(cardIssuerList)

        setCurrentSelection(spinner, cardIssuerList[0])
        setSpinnerListener(spinner, listener)
    }

    if (selectedCardIssuer != null) {
        setCurrentSelection(spinner, selectedCardIssuer)
    }
}

@InverseBindingAdapter(attribute = "selectedCardIssuer")
fun getSelectedCardIssuer(spinner: Spinner): CardIssuer {
    return spinner.selectedItem as CardIssuer
}


@BindingAdapter(
        value = ["installmentsList", "selectedInstallment", "selectedInstallmentAttrChanged"],
        requireAll = false
)
fun setInstallmentsList(
        spinner: Spinner,
        installmentsList: List<PayerCost>?,
        selectedInstallment: PayerCost?,
        listener: InverseBindingListener
) {
    if (installmentsList == null || installmentsList.isEmpty()) return
    if (spinner.adapter == null) {
        spinner.adapter = PayerCostAdapter(installmentsList)

        setCurrentSelection(spinner, installmentsList[0])
        setSpinnerListener(spinner, listener)
    }

    if (selectedInstallment != null) {
        setCurrentSelection(spinner, selectedInstallment)
    }
}

@InverseBindingAdapter(attribute = "selectedInstallment")
fun getSelectedInstallment(spinner: Spinner): PayerCost {
    return spinner.selectedItem as PayerCost
}

private fun setSpinnerListener(spinner: Spinner, listener: InverseBindingListener) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) = listener.onChange()

        override fun onNothingSelected(adapterView: AdapterView<*>) = listener.onChange()
    }
}

private fun <T> setCurrentSelection(spinner: Spinner, selectedItem: T): Boolean {
    if (spinner.adapter == null)
        return false
    for (index in 0 until spinner.adapter.count) {
        if (spinner.getItemAtPosition(index) == selectedItem) {
            spinner.setSelection(index)
            return true
        }
    }
    return false
}

