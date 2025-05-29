package org.mz.killrs.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import killrsgenetics.shared.generated.resources.Res
import killrsgenetics.shared.generated.resources.exo2_italic
import killrsgenetics.shared.generated.resources.exo2_regular

@Composable
fun Exo2FontRegular() = FontFamily(
    Font(Res.font.exo2_regular)
)


@Composable
fun Exo2FontItalic() = FontFamily(
    Font(Res.font.exo2_italic)
)

object FontSize{
     val EXTRA_SMALL = 10.sp
     val SMALL = 10.sp
     val MEDIUM = 20.sp
     val LARGE = 25.sp
     val EXTRA_LARGE = 30.sp

}