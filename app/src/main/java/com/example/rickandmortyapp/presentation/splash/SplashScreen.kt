package com.example.rickandmortyapp.presentation.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.navigation.RickAndMortyScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember { Animatable(0f) }
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 100,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            )
        )
        delay(1000L)
        navController.popBackStack()
        navController.navigate(RickAndMortyScreens.HomeScreen.name)
    })
    Surface(
        modifier = Modifier
            .size(200.dp)
            .scale(scale.value),
        shape = CircleShape
    ) {
        AsyncImage(model = R.drawable.rickandmorty_splash, contentDescription = null)
    }
}