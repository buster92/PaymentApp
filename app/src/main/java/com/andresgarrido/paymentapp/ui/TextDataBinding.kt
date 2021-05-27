package com.andresgarrido.paymentapp.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.andresgarrido.paymentapp.R
import com.andresgarrido.paymentapp.model.CardIssuer
import com.andresgarrido.paymentapp.model.PayerCost
import com.andresgarrido.paymentapp.model.PaymentMethod

@BindingAdapter("summaryAmountText")
fun summaryAmountText(textView: TextView, amount: Double?) {
    textView.text = textView.context.getString(R.string.amount_to_pay_, amount)
}

@BindingAdapter("summaryPaymentMethodText")
fun summaryPaymentMethodText(textView: TextView, paymentMethod: PaymentMethod?) {
    textView.text = textView.context.getString(R.string.payment_method_, paymentMethod?.name)
}

@BindingAdapter("summaryBankText")
fun summaryBankText(textView: TextView, bank: CardIssuer?) {
    textView.text = textView.context.getString(R.string.bank_, bank?.name)
}

@BindingAdapter("summaryInstallmentsText")
fun summaryInstallmentsText(textView: TextView, payerCost: PayerCost?) {
    textView.text = textView.context.getString(R.string.installments_, payerCost?.recommended_message)
}

@BindingAdapter("finishTextSubtitle")
fun finishTextSubtitle(textView: TextView, amount: Double?) {
    textView.text = textView.context.getString(R.string.payment_completed, amount)
}