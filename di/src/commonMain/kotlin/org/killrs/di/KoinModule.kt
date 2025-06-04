package org.killrs.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module
///import org.mz.killrs.auth.AuthViewModel

///val sharedModule = module {
//    viewModelOf(::AuthViewModel)
//}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
){
    startKoin {
        config?.invoke(this)
        ///modules(sharedModule)
    }
}