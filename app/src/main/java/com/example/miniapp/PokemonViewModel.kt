package com.example.miniapp

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.launch

class PokemonViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService = PokemonApiService()
    var pokemonInfo by mutableStateOf("")
    private val imageLoader = ImageLoader.Builder(application).build()

    fun fetchPokemonInfo(pokemonName: String, callback: (Pokemon?) -> Unit) {
        viewModelScope.launch {
            val pokemon = apiService.fetchPokemonData(pokemonName)
            callback(pokemon)

            val imageUrl = pokemon?.imageUrl ?: ""
            if (imageUrl.isNotEmpty()) {
                val request = ImageRequest.Builder(getApplication())
                    .data(imageUrl)
                    .target(
                        onSuccess = { /* Handle image loading success */ },
                        onError = { /* Handle image loading error */ }
                    )
                    .placeholder(R.drawable.pokemon_ball)
                    .error(R.drawable.pokemon_ball)
                    .build()

                imageLoader.enqueue(request)
            }
        }
    }
}

