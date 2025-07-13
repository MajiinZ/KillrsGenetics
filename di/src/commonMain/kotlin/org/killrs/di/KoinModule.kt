package org.killrs.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.mz.admin.AdminPanelViewModel
import org.mz.data.AdminRepositoryImpl
import org.mz.data.CustomerRepositoryImpl
import org.mz.data.domain.AdminRepository
import org.mz.data.domain.CustomerRepository
import org.mz.home.HomeGraphViewModel
import org.mz.killrs.auth.AuthViewModel
import org.mz.killrs.manage_product.ManageProductViewModel
import org.mz.killrs.profile.ProfileViewModel
import org.mz.killrs.manage_product.PhotoPicker

val sharedModule = module {
    single<CustomerRepository>{CustomerRepositoryImpl()}
    single<AdminRepository>{ AdminRepositoryImpl() }
    

    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ManageProductViewModel)
    viewModelOf(::AdminPanelViewModel)
}



expect val targetModule: Module

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, targetModule)
    }
}
