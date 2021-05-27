package com.andresgarrido.paymentapp.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.andresgarrido.paymentapp.R
import com.andresgarrido.paymentapp.model.PaymentProcessState
import com.andresgarrido.paymentapp.ui.main.fragments.*


class MainActivity : AppCompatActivity() {
    private val viewModel: PaymentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.paymentCurrentState.observe(this) {
            navigateToScreen(it)
        }

        viewModel.shouldExit.observe(this) { exit ->
            if (exit) finish()
        }
    }

    private fun navigateToScreen(currentState: PaymentProcessState) {

        val navController = findNavController(this, R.id.nav_host_fragment)
        val currentDestination = navController.currentDestination as FragmentNavigator.Destination

        when(currentState) {
            PaymentProcessState.ENTER_AMOUNT -> goEnterAmount(navController, currentDestination)
            PaymentProcessState.ENTER_PAYMENT_METHOD -> goPaymentMethodScreen(navController, currentDestination)

            PaymentProcessState.ENTER_BANK -> goBankScreen(navController, currentDestination)
            PaymentProcessState.ENTER_INSTALLMENTS -> goInstallmentsScreen(navController, currentDestination)
            PaymentProcessState.PAYMENT_SUMMARY -> goSummaryScreen(navController, currentDestination)
            PaymentProcessState.END -> goEndScreen(navController, currentDestination)
        }
    }

    private fun goEnterAmount(navController: NavController,
                              currentDestination: FragmentNavigator.Destination) {

        val action = PaymentMethodFragmentDirections
                .actionPaymentMethodFragmentToAmountFragment()

        // check if we are actually in this fragment
        if (currentDestination.className == AmountFragment::class.qualifiedName)
            return

        navController.navigate(action)
    }

    private fun goPaymentMethodScreen(navController: NavController,
                                      currentDestination: FragmentNavigator.Destination) {
        // check if we are actually in this fragment
        if (currentDestination.className == PaymentMethodFragment::class.qualifiedName)
            return

        // navigates depending on the current fragment
        if (currentDestination.className == AmountFragment::class.qualifiedName) {
            navController.navigate(
                    AmountFragmentDirections.actionAmountFragmentToPaymentMethodFragment()
            )
        }
        else if (currentDestination.className == BankFragment::class.qualifiedName) {
            navController.navigate(
                    BankFragmentDirections.actionBankFragmentToPaymentMethodFragment()
            )
        }

    }

    private fun goBankScreen(navController: NavController,
                             currentDestination: FragmentNavigator.Destination) {

        // check if we are actually in this fragment
        if (currentDestination.className == BankFragment::class.qualifiedName)
            return

        // navigates depending on the current fragment
        if (currentDestination.className == PaymentMethodFragment::class.qualifiedName) {
            navController.navigate(
                    PaymentMethodFragmentDirections.actionPaymentMethodFragmentToBankFragment()
            )
        }
        else if (currentDestination.className == InstallmentsFragment::class.qualifiedName) {
            navController.navigate(
                    InstallmentsFragmentDirections.actionInstallmentsFragmentToBankFragment()
            )
        }
    }

    private fun goInstallmentsScreen(navController: NavController,
                                     currentDestination: FragmentNavigator.Destination) {

        // check if we are actually in this fragment
        if (currentDestination.className == InstallmentsFragment::class.qualifiedName)
            return

        // navigates depending on the current fragment
        if (currentDestination.className == BankFragment::class.qualifiedName) {
            navController.navigate(
                    BankFragmentDirections.actionBankFragmentToInstallmentsFragment()
            )
        }
        else if (currentDestination.className == SummaryFragment::class.qualifiedName) {
            navController.navigate(
                    SummaryFragmentDirections.actionSummaryFragmentToInstallmentsFragment()
            )
        }
    }

    private fun goSummaryScreen(navController: NavController,
                                currentDestination: FragmentNavigator.Destination) {

        // check if we are actually in this fragment
        if (currentDestination.className == SummaryFragment::class.qualifiedName)
            return

        // navigates depending on the current fragment
        if (currentDestination.className == InstallmentsFragment::class.qualifiedName) {
            navController.navigate(
                    InstallmentsFragmentDirections.actionInstallmentsFragmentToSummaryFragment()
            )
        }
    }


    private fun goEndScreen(navController: NavController,
                                currentDestination: FragmentNavigator.Destination) {

        // check if we are actually in this fragment
        if (currentDestination.className == PaymentEndFragment::class.qualifiedName)
            return

        // navigates depending on the current fragment
        if (currentDestination.className == SummaryFragment::class.qualifiedName) {
            navController.navigate(
                    SummaryFragmentDirections.actionSummaryFragmentToPaymentEndFragment()
            )
        }
    }


}