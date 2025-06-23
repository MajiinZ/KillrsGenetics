package org.killrs.di

//import org.koin.compose.viewmodel.dsl.viewModelOf

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.mz.data.CustomerRepositoryImpl
import org.mz.data.domain.CustomerRepository
import org.mz.home.HomeGraphViewModel
import org.mz.killrs.auth.AuthViewModel
import org.mz.killrs.profile.ProfileViewModel
//import org.mz.killrs.admin.AdminPanelViewModel



val sharedModule = module {
    single<CustomerRepository>{CustomerRepositoryImpl()}

    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    //viewModelOf(::AdminPanelViewModel)
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule)
    }
}
