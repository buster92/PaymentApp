package com.andresgarrido.paymentapp.ui.main

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.andresgarrido.paymentapp.data.PaymentRepository
import com.andresgarrido.paymentapp.model.*
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class PaymentViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val paymentRepository = PaymentRepository()

    // Amount Fragment
    val enteredAmount: LiveData<String>
        get() = liveData {  "\$ ${_enteredAmount.value}" }
    private var _enteredAmount: MutableLiveData<Double> = MutableLiveData(0.0)

    // Payment Method Fragment
    val paymentMethodList = MutableLiveData<List<PaymentMethod>>()
    val selectedPaymentMethod = MediatorLiveData<PaymentMethod>()

    // Bank Fragment
    val bankList = MutableLiveData<List<CardIssuer>>()
    val selectedBank = MediatorLiveData<CardIssuer>()

    // Installments Fragment
    val installmentsList = MutableLiveData<List<PayerCost>>()
    val selectedInstallment = MutableLiveData<PayerCost>()

    // General
    val paymentCurrentState = MutableLiveData(PaymentProcessState.ENTER_AMOUNT)
    val loadingVisibility = MutableLiveData(View.GONE)
    var showError = MediatorLiveData<String>()
    val shouldExit = MutableLiveData(false)

    /**
     * Triggers a validation on the fragment,
     * if variable is true, should go to next screen if data is valid
     * if variable is false, just do validation showing error messages to ${showError}
     */
    val validateShouldGoNext = MutableLiveData(false)


    init {
        //needed to clear the error message in some cases
        showError.addSource(selectedPaymentMethod) {
            validateShouldGoNext.postValue(false)
        }
    }

    /**
     * Loads payment methods data
     */
    fun loadPaymentMethods() {
        loadingVisibility.postValue(View.VISIBLE)
        viewModelScope.launch {
            paymentMethodList.postValue(paymentRepository.getPaymentMethods())
            paymentMethodList.value.let {
                if (it != null && it.isNotEmpty()) {
                    selectedPaymentMethod.postValue(it[0])
                }
            }
            loadingVisibility.postValue(View.GONE)
        }

    }

    /**
     * Loads bank data
     */
    fun loadBanks() {
        loadingVisibility.postValue(View.VISIBLE)
        selectedPaymentMethod.value?.let {
            viewModelScope.launch {
                bankList.postValue(paymentRepository.getBankList(it.id))
                loadingVisibility.postValue(View.GONE)
            }
        }
    }

    /**
     * Load installments data
     */
    fun loadInstallments() {
        loadingVisibility.postValue(View.VISIBLE)
        _enteredAmount.value?.let { amount ->
            selectedPaymentMethod.value?.let { paymentMethod ->
                selectedBank.value?.let { bank ->
                    viewModelScope.launch {
                        val installments = paymentRepository.getInstallments(amount, paymentMethod.id, bank.id)
                        if (installments.isNotEmpty())
                            installmentsList.postValue(installments[0].payer_costs)
                        loadingVisibility.postValue(View.GONE)
                    }

                }
            }
        }
    }

    /**
     * Text watcher for amount, used from layout after text changed
     *
     * @param s string sequence
     * @param start start index from 's'
     * @param before replaced length text
     * @param count number of characters
     */
    fun onAmountTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        try {
            _enteredAmount.postValue(s.toString().toDouble())
        }
        catch (ignored: NumberFormatException) {
            _enteredAmount.postValue(0.0)
        }

        validateShouldGoNext.postValue(false)
    }

    /**
     * Gets the entered mount to pay
     * @return the entered amount or zero if not set
     */
    fun getEnteredAmountDouble(): Double {
        val amount = _enteredAmount.value
        return amount ?: 0.0
    }

    /**
     * Next button click event
     */
    fun onNextClicked() {
        validateShouldGoNext.postValue(true)

    }

    /**
     * Go to next screen after data is valid
     */
    fun goNext() {
        when(paymentCurrentState.value) {
            PaymentProcessState.ENTER_AMOUNT -> paymentCurrentState.postValue(PaymentProcessState.ENTER_PAYMENT_METHOD)
            PaymentProcessState.ENTER_PAYMENT_METHOD -> paymentCurrentState.postValue(PaymentProcessState.ENTER_BANK)
            PaymentProcessState.ENTER_BANK -> paymentCurrentState.postValue(PaymentProcessState.ENTER_INSTALLMENTS)
            PaymentProcessState.ENTER_INSTALLMENTS -> paymentCurrentState.postValue(PaymentProcessState.PAYMENT_SUMMARY)
            PaymentProcessState.PAYMENT_SUMMARY -> paymentCurrentState.postValue(PaymentProcessState.END)
            PaymentProcessState.END -> { }
        }
    }

    /**
     * Previous button click event
     */
    fun onPreviousClicked() {
        if (loadingVisibility.value == View.VISIBLE)
            return
        when(paymentCurrentState.value) {
            PaymentProcessState.ENTER_AMOUNT -> { }
            PaymentProcessState.ENTER_PAYMENT_METHOD -> paymentCurrentState.postValue(PaymentProcessState.ENTER_AMOUNT)
            PaymentProcessState.ENTER_BANK -> paymentCurrentState.postValue(PaymentProcessState.ENTER_PAYMENT_METHOD)
            PaymentProcessState.ENTER_INSTALLMENTS -> paymentCurrentState.postValue(PaymentProcessState.ENTER_BANK)
            PaymentProcessState.PAYMENT_SUMMARY -> paymentCurrentState.postValue(PaymentProcessState.ENTER_INSTALLMENTS)
            PaymentProcessState.END -> { }
        }

    }

    /**
     * Exit button click event
     */
    fun onExitClicked() {
        shouldExit.postValue(true)
    }
}
