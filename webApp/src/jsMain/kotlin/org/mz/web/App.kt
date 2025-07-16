import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.renderComposable


@Composable
fun webApp(){
    Div {
        H1 { Text("KillrsGenetics - Web App") }
        P { Text("This is the web app for KillrsGenetics") }
    }
}

fun main(){
    renderComposable(rootElementId = "root"){
        webApp()
    }
}