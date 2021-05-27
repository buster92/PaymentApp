package com.andresgarrido.paymentapp.data.network

import com.andresgarrido.paymentapp.model.CardIssuer
import com.andresgarrido.paymentapp.model.InstallmentsResponse
import com.andresgarrido.paymentapp.model.PaymentMethod
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("payment_methods")
    suspend fun getPaymentMethods(): Response<List<PaymentMethod>>

    @GET("payment_methods/card_issuers")
    suspend fun getCardIssuers(@Query("payment_method_id") paymentMethodId: String): Response<List<CardIssuer>>

    @GET("payment_methods/installments")
    suspend fun getInstallments(@Query("amount") amount: Double,
                                @Query("payment_method_id") paymentMethodId: String,
                                @Query("issuer.id") issuerId: Long): Response<List<InstallmentsResponse>>
}