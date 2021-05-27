package com.andresgarrido.paymentapp.data.network

import android.util.Log
import com.andresgarrido.paymentapp.model.CardIssuer
import com.andresgarrido.paymentapp.model.InstallmentsResponse
import com.andresgarrido.paymentapp.model.PaymentMethod

class ApiRemoteDataSource: BaseDataSource() {

    companion object {
        private val apiService = ApiService.retrofitService
        private val TAG = ApiRemoteDataSource::class.simpleName
    }

    /**
     * Get all the available payment methods
     *
     * @return a list of payment methods, empty list if request fail
     */
    suspend fun getPaymentMethods(): List<PaymentMethod> {

        val result = getResult {
            apiService.getPaymentMethods()
        }
        if (result.status == Resource.Status.SUCCESS ) {
            val response = result.data!!
            if (result.status != Resource.Status.SUCCESS) {
                Log.e(TAG, "Error on getPaymentMethods(): $result.message")
            }
            return response
        }
        return ArrayList()
    }

    /**
     *
     */
    suspend fun getBanks(paymentMethodId: String): List<CardIssuer> {

        val result = getResult {
            apiService.getCardIssuers(paymentMethodId)
        }
        if (result.status == Resource.Status.SUCCESS ) {
            val response = result.data!!
            if (result.status != Resource.Status.ERROR) {
                Log.e(TAG, "Error on getBanks(): $result.message")
            }
            return response
        }
        return ArrayList()
    }

    suspend fun getInstallments(amount: Double, paymentMethodId: String, issuerId: Long): List<InstallmentsResponse> {

        val result = getResult {
            apiService.getInstallments(amount, paymentMethodId, issuerId)
        }
        if (result.status == Resource.Status.SUCCESS ) {
            val response = result.data!!
            if (result.status != Resource.Status.ERROR) {
                Log.e(TAG, "Error on getInstallments(): $result.message")
            }
            return response
        }
        return ArrayList()
    }
}