package org.mz.killrs.categories

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoriesViewModel : ViewModel() {
    private val _state = MutableStateFlow(categoriesState())
    val state = _state.asStateFlow()
}

fun categoriesState(){

}