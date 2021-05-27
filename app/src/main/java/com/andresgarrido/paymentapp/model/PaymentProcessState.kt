package com.andresgarrido.paymentapp.model

enum class PaymentProcessState {
    ENTER_AMOUNT, ENTER_PAYMENT_METHOD, ENTER_BANK, ENTER_INSTALLMENTS, PAYMENT_SUMMARY, END
}