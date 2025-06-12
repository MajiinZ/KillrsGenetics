package org.killrs.di

//import org.koin.compose.viewmodel.dsl.viewModelOf

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.mz.data.CustomerRepositoryImpl
import org.mz.data.domain.CustomerRepository
import org.mz.killrs.auth.AuthViewModel


val sharedModule = module {
    single<CustomerRepository>{CustomerRepositoryImpl()}
    singleOf(::AuthViewModel)
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule)
    }
}
