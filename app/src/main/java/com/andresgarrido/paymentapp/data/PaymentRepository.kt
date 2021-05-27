package com.andresgarrido.paymentapp.data

import android.util.Log
import com.andresgarrido.paymentapp.data.network.ApiRemoteDataSource
import com.andresgarrido.paymentapp.model.CardIssuer
import com.andresgarrido.paymentapp.model.InstallmentsResponse
import com.andresgarrido.paymentapp.model.PaymentMethod

class PaymentRepository {

    private val apiRemoteDataSource = ApiRemoteDataSource()

    /**
     * Get the list of available payment methods
     *
     * @return list, or empty if an error occurred
     */
    suspend fun getPaymentMethods(): List<PaymentMethod> {
        Log.w("TAG", "calling get payment methods")
        return apiRemoteDataSource.getPaymentMethods()
    }

    /**
     * get the list of banks (card issuers) given the selected payment method (card)
     *
     * @param paymentMethodId the ID of the selected payment method
     * @return list of card issuer, or empty if an error occurred
     */
    suspend fun getBankList(paymentMethodId: String): List<CardIssuer> {
        return apiRemoteDataSource.getBanks(paymentMethodId)
    }

    /**
     * get the installments available for the current data
     *
     * @param amount amount to pay
     * @param paymentMethodId payment method ID
     * @param issuerId issuer ID (Bank)
     * @return list of installments
     */
    suspend fun getInstallments(amount: Double, paymentMethodId: String, issuerId: Long): List<InstallmentsResponse> {
        return apiRemoteDataSource.getInstallments(amount, paymentMethodId, issuerId)
    }

}