package org.mz.killrs.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.resources.painterResource
import org.mz.killrs.shared.BorderIdle
import org.mz.killrs.shared.Exo2FontRegular
import org.mz.killrs.shared.FontSize
import org.mz.killrs.shared.Resources
import org.mz.killrs.shared.Surface
import org.mz.killrs.shared.SurfaceLighter
import org.mz.killrs.shared.TextPrimary
import org.mz.killrs.shared.TextSecondary
import org.mz.killrs.shared.component.QuantityCounter
import org.mz.killrs.shared.domain.CartItem
import org.mz.killrs.shared.domain.Product
import org.mz.killrs.shared.domain.QuantityCounterSize

@Composable
fun CartItemCard(
    modifier: Modifier = Modifier,
    product: Product,
    cartItem: CartItem,
    //onQuantityChange: (Int) -> Unit,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 12.dp))
            .background(SurfaceLighter)
    ){
        AsyncImage(
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp,
                    BorderIdle,
                    RoundedCornerShape(12.dp)
                ),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(product.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = "Product thumbnail image",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(all = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

            ) {
                Text(
                    text = product.title,
                    fontFamily = Exo2FontRegular(),
                    fontSize = FontSize.REGULAR,
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis

                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Surface)
                    .border(
                       width = 1.dp,
                        color = BorderIdle,
                        shape =RoundedCornerShape(size = 6.dp)
                    )
                    .clickable {  }
                    .padding(all = 8.dp),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Resources.Icon.Delete),
                    contentDescription = "Delete icon",
                    tint = TextPrimary
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,){
                Text(
                    text = product.price.toString(),
                    fontSize = FontSize.REGULAR,
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                )
                QuantityCounter(
                    size = QuantityCounterSize.Small,
                    value = "${cartItem.quantity}",
                    onMinusClick = { onMinusClick()},
                    onPlusClick = {onPlusClick()}
                )
            }
        }
    }
}