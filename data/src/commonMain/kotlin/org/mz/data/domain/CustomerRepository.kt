package org.mz.data.domain

import dev.gitlive.firebase.auth.FirebaseUser

interface CustomerRepository {
    suspend fun createCustomers(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit

    )

}