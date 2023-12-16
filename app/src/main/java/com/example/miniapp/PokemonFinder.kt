package com.example.miniapp

// PokemonFinder.kt
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter

@Composable
fun PokemonFinder() {
    val apiService = remember { PokemonApiService() }
    val viewModel: PokemonViewModel = viewModel()

    var pokemonName by remember { mutableStateOf("") }
    var pokemonInfo by remember { mutableStateOf<Pokemon?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.pokemon_ball),
                contentDescription = "Pokemon Ball",
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }

        Text(
            text = "Pokemon Searcher",
            modifier = Modifier.padding(16.dp)
        )

        BasicTextField(
            value = pokemonName,
            onValueChange = { pokemonName = it },
            singleLine = true,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Button(
            onClick = {
                viewModel.fetchPokemonInfo(pokemonName) { pokemon ->
                    pokemonInfo = pokemon
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Search")
        }

// Display Pokemon information if available
        pokemonInfo?.let { pokemon ->
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Display the Pokemon image if available
                pokemon.imageUrl?.let { imageUrl ->
                    Image(
                        painter = rememberImagePainter(
                            data = imageUrl,
                            builder = {
                                crossfade(true)
                                placeholder(R.drawable.pokemon_ball) // Placeholder image resource
                                error(R.drawable.pokemon_ball) // Error image resource
                            }
                        ),
                        contentDescription = "Pokemon Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(shapes.medium)
                            .border(1.dp, Color.Black, shapes.medium)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text("Name: ${pokemon.name}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Type: ${pokemon.type}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Ability: ${pokemon.ability}") // Display Ability
                Spacer(modifier = Modifier.height(8.dp))
                Text("Size: ${pokemon.size}") // Display Size
                Spacer(modifier = Modifier.height(8.dp))
                Text("Weight: ${pokemon.weight}") // Display Weight
                Spacer(modifier = Modifier.height(8.dp))
                Text("Evolution: ${pokemon.evolution ?: "None"}")
            }

            }
        }
    }