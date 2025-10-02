import org.mz.killrs.shared.Resources
import org.jetbrains.compose.resources.DrawableResource

enum class States(
    val dialCode: Int,
    val code: String,
    val flag: DrawableResource
) {
    California(
        dialCode = 381,
        code = "Cali",
        flag = Resources.Image.KillrsLogo
    ),
    Nevada(
        dialCode = 91,
        code = "Nev",
        flag = Resources.Image.KillrsLogo
    ),
    Texas(
        dialCode = 1,
        code = "Tex",
        flag = Resources.Image.KillrsLogo
    )
}