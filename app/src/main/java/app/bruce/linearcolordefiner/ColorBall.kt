package app.bruce.linearcolordefiner

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.Dp

@Composable
fun ColorBall(
    radius: Dp,
    ballColor: Color,
    onChange: (Dp) -> Unit = {},
) {
    Surface(modifier = Modifier
        .size(radius)
        .pointerInput(Unit) {
            detectDragGestures { change, _ ->
                onChange(change.positionChange().x.toDp())
                onChange(change.positionChange().y.toDp())
            }
        }, color = ballColor, shape = RoundedCornerShape(percent = 50)) {}
}