package org.mz.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.mz.killrs.shared.BorderIdle
import org.mz.killrs.shared.Exo2FontRegular
import org.mz.killrs.shared.FontSize
import org.mz.killrs.shared.IconPrimary
import org.mz.killrs.shared.Resources
import org.mz.killrs.shared.SurfaceLighter
import org.mz.killrs.shared.TextPrimary
import org.mz.killrs.shared.TextSecondary

private data class SettingsSection(
    val title: String,
    val summary: String,
    val icon: DrawableResource,
    val options: List<String>
)

@Composable
fun SettingsScreen() {
    val sections = remember {
        listOf(
            SettingsSection(
                title = "Age & Location Compliance",
                summary = "Verification, region, and legal acknowledgments",
                icon = Resources.Icon.Info,
                options = listOf(
                    "Age verification status",
                    "Shipping region",
                    "Jurisdiction restrictions",
                    "Local-law acknowledgment"
                )
            ),
            SettingsSection(
                title = "Order & Shipping Preferences",
                summary = "Delivery details, packaging, and order updates",
                icon = Resources.Icon.ShoppingCartFilled,
                options = listOf(
                    "Default shipping address",
                    "Discreet packaging preference",
                    "Shipping notifications",
                    "Saved delivery instructions"
                )
            ),
            SettingsSection(
                title = "Genetics & Inventory Alerts",
                summary = "Choose the genetics updates you want to receive",
                icon = Resources.Icon.Seed,
                options = listOf(
                    "Restock alerts",
                    "Breeder releases",
                    "Favorite genetics",
                    "Seed-type and promotional preferences"
                )
            ),
            SettingsSection(
                title = "Account, Privacy & Security",
                summary = "Sign-in, payments, privacy, and account controls",
                icon = Resources.Icon.Profile,
                options = listOf(
                    "Google account and biometric security",
                    "Saved-payment controls",
                    "Data and marketing consent",
                    "Account deletion, policies, and legal disclaimers"
                )
            )
        )
    }

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
                text = "Manage your shopping, compliance, and account preferences.",
                color = TextPrimary,
                fontFamily = Exo2FontRegular(),
                fontSize = FontSize.REGULAR
            )
        }
        items(sections) { section ->
            SettingsSectionCard(section)
        }
        item { Spacer(modifier = Modifier.size(4.dp)) }
    }
}

@Composable
private fun SettingsSectionCard(section: SettingsSection) {
    var expanded by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(16.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceLighter, shape)
            .border(1.dp, BorderIdle, shape)
            .clickable { expanded = !expanded }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(section.icon),
                contentDescription = null,
                tint = IconPrimary
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = section.title,
                    color = TextPrimary,
                    fontFamily = Exo2FontRegular(),
                    fontSize = FontSize.EXTRA_REGULAR,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = section.summary,
                    color = TextPrimary.copy(alpha = 0.65f),
                    fontFamily = Exo2FontRegular(),
                    fontSize = FontSize.SMALL
                )
            }
            Text(
                text = if (expanded) "−" else "+",
                color = TextSecondary,
                fontSize = FontSize.EXTRA_MEDIUM
            )
        }

        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier.padding(top = 14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                section.options.forEach { option ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "•",
                            color = TextSecondary,
                            fontSize = FontSize.EXTRA_REGULAR
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = option,
                            color = TextPrimary,
                            fontFamily = Exo2FontRegular(),
                            fontSize = FontSize.REGULAR
                        )
                    }
                }
            }
        }
    }
}
