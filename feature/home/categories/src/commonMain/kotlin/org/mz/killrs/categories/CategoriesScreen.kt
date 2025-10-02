package org.mz.killrs.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.mz.killrs.categories.component.CategoryCard
import org.mz.killrs.shared.domain.ProductCategory

@Composable
fun CategoriesScreen(
    navigateToCategories: (String) -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.Gray)
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        ProductCategory.entries.forEach {
            CategoryCard(
                category = it,
                onClick = {
                    navigateToCategories(it.name)

                }
            )
        }
    }
}