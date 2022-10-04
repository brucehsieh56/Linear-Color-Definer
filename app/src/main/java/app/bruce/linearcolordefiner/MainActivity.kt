package app.bruce.linearcolordefiner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import app.bruce.linearcolordefiner.ui.theme.LinearColorDefinerTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LinearColorDefinerTheme {

                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }
                var snackBarJob: Job? = null

                Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        val localDensity = LocalDensity.current
                        val minRadius = 20.dp
                        val maxRadius = 100.dp

                        var redRadius by remember { mutableStateOf(maxRadius) }
                        var greenRadius by remember { mutableStateOf(maxRadius) }
                        var blueRadius by remember { mutableStateOf(maxRadius) }
                        var bigBallColor by remember { mutableStateOf(Color(255, 255, 255)) }

                        val radiusToColorValue: (Dp) -> Float = { radiusInDp ->
                            localDensity.run {
                                (radiusInDp - minRadius) / (maxRadius - minRadius)
                            }
                        }

                        // Top big color ball
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            BigColorBall(radius = 250.dp,
                                ballColor = bigBallColor,
                                onClick = { text ->
                                    snackBarJob?.cancel()
                                    snackBarJob = scope.launch {
                                        snackbarHostState.showSnackbar(message = text,
                                            withDismissAction = true)
                                    }
                                })
                        }

                        // Random color button
                        FilledTonalButton(onClick = {
                            redRadius = (20..100).shuffled().first().dp
                            greenRadius = (20..100).shuffled().first().dp
                            blueRadius = (20..100).shuffled().first().dp
                            bigBallColor = bigBallColor.copy(red = radiusToColorValue(redRadius),
                                green = radiusToColorValue(greenRadius),
                                blue = radiusToColorValue(blueRadius))
                        }) { Text(text = "Lucky Color") }

                        // Bottom small color balls
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically) {

                            // Red
                            Box(modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center) {
                                ColorBall(radius = redRadius,
                                    ballColor = Color(117, 251, 76),
                                    onChange = { newRadius ->
                                        redRadius =
                                            (redRadius + newRadius).coerceIn(minRadius, maxRadius)
                                        bigBallColor =
                                            bigBallColor.copy(red = radiusToColorValue(redRadius))
                                    })
                            }

                            // Green
                            Box(modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center) {
                                ColorBall(radius = greenRadius,
                                    ballColor = Color(234, 55, 41),
                                    onChange = { newRadius ->
                                        greenRadius =
                                            (greenRadius + newRadius).coerceIn(minRadius, maxRadius)
                                        bigBallColor =
                                            bigBallColor.copy(green = radiusToColorValue(greenRadius))
                                    })
                            }

                            // Blue
                            Box(modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center) {
                                ColorBall(radius = blueRadius,
                                    ballColor = Color(0, 0, 245),
                                    onChange = { newRadius ->
                                        blueRadius =
                                            (blueRadius + newRadius).coerceIn(minRadius, maxRadius)
                                        bigBallColor =
                                            bigBallColor.copy(blue = radiusToColorValue(blueRadius))
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}