package com.mehmaancoders.planme.presentation.fetching

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

@Composable
fun FetchingScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val sensorManager = remember { context.getSystemService(SensorManager::class.java) as SensorManager }
    val accelerometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }

    var gravityX by remember { mutableStateOf(0f) }
    var gravityY by remember { mutableStateOf(9.8f) }
    var isShaking by remember { mutableStateOf(false) }
    var shakeIntensity by remember { mutableStateOf(0f) }

    // Create larger water bubbles with floating properties
    val bubbles = remember {
        List(8) { i ->
            WaterBubble(
                x = Random.nextFloat() * 800f + 100f,
                y = Random.nextFloat() * 1200f + 200f,
                radius = Random.nextFloat() * 40f + 30f, // Much larger bubbles (30-70px)
                mass = Random.nextFloat() * 0.5f + 0.3f,
                id = i,
                floatSpeedX = Random.nextFloat() * 1.5f + 0.5f, // Unique floating speeds
                floatSpeedY = Random.nextFloat() * 1.2f + 0.3f,
                floatOffsetX = Random.nextFloat() * 6.28f, // Random phase offsets
                floatOffsetY = Random.nextFloat() * 6.28f,
                floatAmplitude = Random.nextFloat() * 2f + 1f // Floating strength
            )
        }
    }

    // Sensor listener setup for realistic physics
    DisposableEffect(Unit) {
        var lastTime = System.currentTimeMillis()
        val shakeThreshold = 3f

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let { sensorEvent ->
                    val currentTime = System.currentTimeMillis()
                    val deltaTime = (currentTime - lastTime) / 1000f
                    lastTime = currentTime

                    // Get accelerometer values (device coordinates)
                    val x = sensorEvent.values[0]
                    val y = sensorEvent.values[1]
                    val z = sensorEvent.values[2]

                    // Convert to world coordinates (flip Y for correct gravity direction)
                    gravityX = x * 2f // Amplify for more noticeable effect
                    gravityY = -y * 2f // Negative to make gravity pull down when phone is upright

                    // Calculate shake intensity
                    val totalAcceleration = sqrt(x * x + y * y + z * z)
                    val netAcceleration = abs(totalAcceleration - SensorManager.GRAVITY_EARTH)

                    if (netAcceleration > shakeThreshold) {
                        isShaking = true
                        shakeIntensity = min(netAcceleration / 10f, 2f)
                    } else {
                        shakeIntensity = max(shakeIntensity - deltaTime * 3f, 0f)
                        if (shakeIntensity < 0.1f) isShaking = false
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_GAME)

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP ->
                    sensorManager.unregisterListener(listener)
                Lifecycle.Event.ON_RESUME, Lifecycle.Event.ON_START ->
                    sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_GAME)
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            sensorManager.unregisterListener(listener)
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Optimized physics simulation - 60 FPS with continuous floating motion
    LaunchedEffect(gravityX, gravityY, isShaking) {
        while (true) {
            delay(16) // 60 FPS for smooth animation

            bubbles.forEach { bubble ->
                // Continuous floating motion even when static
                val time = System.currentTimeMillis() * 0.001f
                val floatForceX = sin(time * bubble.floatSpeedX + bubble.floatOffsetX) * bubble.floatAmplitude
                val floatForceY = cos(time * bubble.floatSpeedY + bubble.floatOffsetY) * bubble.floatAmplitude * 0.5f

                // Add gravity and shake forces
                val totalForceX = gravityX * 0.5f + floatForceX + if (isShaking) {
                    Random.nextFloat() * shakeIntensity * 40f - shakeIntensity * 20f
                } else 0f

                val totalForceY = gravityY * 0.5f + floatForceY + if (isShaking) {
                    Random.nextFloat() * shakeIntensity * 40f - shakeIntensity * 20f
                } else 0f

                // Update velocity with forces
                bubble.velocityX += totalForceX * 0.02f
                bubble.velocityY += totalForceY * 0.02f

                // Apply different resistance for shaking vs floating
                val resistance = if (isShaking) 0.94f else 0.98f
                bubble.velocityX *= resistance
                bubble.velocityY *= resistance

                // Cap maximum velocity for stability
                val maxVelocity = if (isShaking) 25f else 8f
                bubble.velocityX = bubble.velocityX.coerceIn(-maxVelocity, maxVelocity)
                bubble.velocityY = bubble.velocityY.coerceIn(-maxVelocity, maxVelocity)

                // Update position
                bubble.x += bubble.velocityX
                bubble.y += bubble.velocityY
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA1BE5C))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Update bubble boundaries and draw
            bubbles.forEach { bubble ->
                bubble.updateBoundaries(size.width, size.height)
                drawWaterBubble(bubble, isShaking, shakeIntensity)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Fetching Data...",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Mouse,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = when {
                        isShaking && shakeIntensity > 1f -> "Water is splashing violently!"
                        isShaking -> "Bubbles are shaking!"
                        else -> "Watch the bubbles float! Shake for more action!"
                    },
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Stable
class WaterBubble(
    var x: Float,
    var y: Float,
    val radius: Float,
    val mass: Float,
    val id: Int,
    val floatSpeedX: Float = 1f,
    val floatSpeedY: Float = 1f,
    val floatOffsetX: Float = 0f,
    val floatOffsetY: Float = 0f,
    val floatAmplitude: Float = 1f
) {
    var velocityX: Float = 0f
    var velocityY: Float = 0f
    var lastCollisionTime: Long = 0L

    fun updateBoundaries(screenWidth: Float, screenHeight: Float) {
        // Enhanced boundary collisions with better bouncing
        if (x - radius <= 0f) {
            x = radius
            velocityX = abs(velocityX) * 0.7f
        } else if (x + radius >= screenWidth) {
            x = screenWidth - radius
            velocityX = -abs(velocityX) * 0.7f
        }

        if (y - radius <= 0f) {
            y = radius
            velocityY = abs(velocityY) * 0.7f
        } else if (y + radius >= screenHeight) {
            y = screenHeight - radius
            velocityY = -abs(velocityY) * 0.7f
        }
    }
}

fun DrawScope.drawWaterBubble(bubble: WaterBubble, isShaking: Boolean, shakeIntensity: Float) {
    // Enhanced rendering with shake-responsive effects
    val time = System.currentTimeMillis() * 0.001f

    // Dynamic bubble effects - more pronounced when shaking
    val dynamicRadius = bubble.radius + if (isShaking) {
        sin(time * 8f + bubble.id) * 4f * shakeIntensity
    } else {
        sin(time * 2f + bubble.id) * 1.5f
    }

    // Color changes based on shaking
    val alpha = if (isShaking) {
        (160 + sin(time * 10f + bubble.id) * 30f * shakeIntensity).toInt().coerceIn(120, 220)
    } else {
        180
    }

    // Main bubble with enhanced water color
    drawCircle(
        color = Color(0x70, 0xB0, 0xE0, alpha),
        radius = dynamicRadius,
        center = Offset(bubble.x, bubble.y)
    )

    // Enhanced highlight with shake effects
    val highlightAlpha = if (isShaking) {
        (60 + sin(time * 12f + bubble.id) * 40f * shakeIntensity).toInt().coerceIn(40, 120)
    } else {
        80
    }

    drawCircle(
        color = Color(0xFF, 0xFF, 0xFF, highlightAlpha),
        radius = dynamicRadius * 0.25f,
        center = Offset(bubble.x - dynamicRadius * 0.15f, bubble.y - dynamicRadius * 0.15f)
    )

    // Additional shimmer effect when shaking
    if (isShaking && shakeIntensity > 0.3f) {
        drawCircle(
            color = Color(0xFF, 0xFF, 0xFF, (20 * shakeIntensity).toInt()),
            radius = dynamicRadius * 0.8f,
            center = Offset(bubble.x, bubble.y)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun FetchingScreenPreview() {
    FetchingScreen(navHostController = NavHostController(LocalContext.current))
}