package com.example.worlddata

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit


/**
 * Shows confetti in the middle of the screen.
 */
@Composable
fun ShowCenteredConfetti(){
    KonfettiView(
        modifier = Modifier.fillMaxSize(),
        parties = listOf(
            Party(
                maxSpeed = 50f,
                damping = 0.8f,
                spread = 360,
                emitter = Emitter(duration = 200, TimeUnit.MILLISECONDS).perSecond(120),
                position = Position.Relative(0.5, 0.4)
            )
        )
    )
}


/**
 * Shows confetti from both sides to the middle of the screen.
 */
@Composable
fun ShowSideConfetti(){
    // This is confetti from left to right.
    KonfettiView(
        modifier = Modifier.fillMaxSize(),
        parties = listOf(
            Party(
                angle = 300,
                maxSpeed = 50f,
                damping = 0.9f,
                spread = 30,
                emitter = Emitter(duration = 1, TimeUnit.SECONDS).perSecond(100),
                position = Position.Relative(0.0, 0.5)
            )
        )
    )

    // This is confetti from right to left.
    KonfettiView(
        modifier = Modifier.fillMaxSize(),
        parties = listOf(
            Party(
                angle = 240,
                maxSpeed = 50f,
                damping = 0.9f,
                spread = 30,
                emitter = Emitter(duration = 1, TimeUnit.SECONDS).perSecond(100),
                position = Position.Relative(1.0, 0.5)
            )
        )
    )
}
