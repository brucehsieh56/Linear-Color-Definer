package app.bruce.linearcolordefiner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BigColorBall(
    radius: Dp,
    ballColor: Color,
    onClick: (String) -> Unit,
) {
    val clipboardManager = LocalClipboardManager.current

    val redInt = (ballColor.red * 255).toInt()
    val greenInt = (ballColor.green * 255).toInt()
    val blueInt = (ballColor.blue * 255).toInt()

    Surface(modifier = Modifier.size(radius),
        color = ballColor,
        shape = RoundedCornerShape(percent = 50)) {
        Row(modifier = Modifier
            .size(radius)
            .clickable {
                val text = "$redInt $greenInt $blueInt"
                clipboardManager.setText(AnnotatedString(text))
                onClick("Color ($text) copied!")
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Text(text = "$redInt", color = Color.Gray, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$greenInt",
                color = Color.Gray,
                style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$blueInt", color = Color.Gray, style = MaterialTheme.typography.titleLarge)
        }
    }
}