//package org.mz.killrs.shared.util
//
//
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.callbackFlow
//import org.mz.killrs.shared.navigation.Screen
//
//object PreferencesRepository {
//    private val settings: ObservableSettings = Settings().makeObservable()
//
//    private const val IS_SUCCESS = "isSuccess_paypal"
//    private const val ERROR = "error_paypal"
//    private const val TOKEN = "token_paypal"
//
//    fun savePayPalData(
//        isSuccess: Boolean?,
//        error: String?,
//        token: String?,
//    ) {
//        isSuccess?.let { settings.putBoolean(IS_SUCCESS, it) }
//        error?.let { settings.putString(ERROR, it) }
//        token?.let { settings.putString(TOKEN, it) }
//    }
//
//    fun readPayPalDataFlow(): Flow<Screen.PaymentCompleted?> = callbackFlow {
//        fun getCurrentPaymentProcessed(): Screen.PaymentCompleted {
//            return Screen.PaymentCompleted(
//                isSuccess = settings.getBooleanOrNull(IS_SUCCESS),
//                error = settings.getStringOrNull(ERROR),
//                token = settings.getStringOrNull(TOKEN)
//            )
//        }
//
//        while (true) {
//            this.send(getCurrentPaymentProcessed())
//            delay(1000) // Check for updates every second
//        }
//    }
//
//    fun reset() {
//        settings.clear()
//    }
//}