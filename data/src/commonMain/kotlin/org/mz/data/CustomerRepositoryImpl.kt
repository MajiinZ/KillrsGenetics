package org.mz.data

import com.google.firebase.firestore.firestore
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.firestore
import org.mz.data.domain.CustomerRepository
import org.mz.killrs.shared.domain.Customer

class CustomerRepositoryImpl: CustomerRepository {

    override suspend fun createCustomers(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        try {
            if (user != null) {
                val customerCollection = Firebase.firestore.collection(collectionPath = "customer")
                val customer = Customer(
                    id = user.uid,
                    firstName = user.displayName?.split(" ")?.firstOrNull() ?: "Unknown",
                    lastName = user.displayName?.split(" ")?. lastOrNull() ?: "Unknown",
                    email = user.email?: "Unknown",
                    dateOfBirth = "Unknown",
                    gender = "Unknown"

                )

                val customerExsists = customerCollection.document(user.uid).get().exists
                if (customerExsists){
                    onSuccess()
                }else{
                    customerCollection.document(user.uid).set(customer)
                    onSuccess()
                }
            }else{
                onFailure("User is null")
            }
        }catch (e: Exception){
            onFailure("Error while creating a customer: ${e.message}")
        }
    }
}