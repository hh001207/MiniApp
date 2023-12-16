package com.example.miniapp

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokemonBall() {
    Image(
        painter = painterResource(id = R.drawable.pokemon_ball), // Replace with your Pokemon Ball image resource
        contentDescription = "Pokemon Ball"
    )
}

@Preview
@Composable
fun PokemonBallPreview() {
    PokemonBall()
}
