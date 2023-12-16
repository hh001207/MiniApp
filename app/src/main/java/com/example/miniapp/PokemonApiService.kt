package com.example.miniapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class PokemonApiService {
    private val httpClient = OkHttpClient()

    suspend fun fetchPokemonData(pokemonName: String): Pokemon? {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url("https://pokeapi.co/api/v2/pokemon/$pokemonName")
                .build()

            val response = httpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val responseData = response.body?.string()
                parsePokemonData(responseData)
            } else {
                null
            }
        }
    }

    private fun parsePokemonData(responseData: String?): Pokemon? {
        val jsonObject = JSONObject(responseData)
        val name = jsonObject.getString("name")
        val imageUrl = jsonObject.getJSONObject("sprites").getString("front_default")
        val type = jsonObject.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name")

        // Additional details: Ability, Size, Weight, Evolution
        val abilities = jsonObject.getJSONArray("abilities")
        val ability = abilities.getJSONObject(0).getJSONObject("ability").getString("name")

        val height = jsonObject.getInt("height")
        val weight = jsonObject.getInt("weight")

        val speciesUrl = jsonObject.getJSONObject("species").getString("url")
        val speciesResponse = OkHttpClient().newCall(Request.Builder().url(speciesUrl).build()).execute()
        val speciesJsonObject = JSONObject(speciesResponse.body?.string())
        val evolutionChainUrl = speciesJsonObject.getJSONObject("evolution_chain").getString("url")
        val evolutionChainResponse = OkHttpClient().newCall(Request.Builder().url(evolutionChainUrl).build()).execute()
        val evolutionChainJsonObject = JSONObject(evolutionChainResponse.body?.string())
        val chain = evolutionChainJsonObject.getJSONObject("chain")
        val evolutionDetails = parseEvolution(chain)

        return Pokemon(name, imageUrl, type, ability, height, weight, evolutionDetails)
    }

    private fun parseEvolution(chain: JSONObject): String {
        val evolutionDetails = StringBuilder()
        val evolvesTo = chain.getJSONArray("evolves_to")

        for (i in 0 until evolvesTo.length()) {
            val evolution = evolvesTo.getJSONObject(i)
            val species = evolution.getJSONObject("species")
            val name = species.getString("name")
            evolutionDetails.append("Name: $name\n")

            if (evolution.has("evolves_to") && !evolution.isNull("evolves_to")) {
                evolutionDetails.append(parseEvolution(evolution))
            }
        }

        return evolutionDetails.toString()
    }


}
