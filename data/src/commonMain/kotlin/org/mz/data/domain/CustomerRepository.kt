package org.mz.data.domain

import dev.gitlive.firebase.auth.FirebaseUser
import org.mz.killrs.shared.util.RequestState

interface CustomerRepository {
    fun getCurrentUserId(): String?
    suspend fun createCustomers(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
    suspend fun signOut(): RequestState<Unit>
}