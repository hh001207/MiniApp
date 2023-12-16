Pokemon Finder Mini-App
A simple Android application to get details about the users desired pokemon.
I will explain my understanding of the codes that I used to make "Pokemon Finder" work.
I tried to avoid using GPT and other resources as much as I could, but there are some parts that I could not fully understand and got some example codes to reference.

Features
There are three features that I intended to add to my mini app.
1. Pokemon Search: I used the pokemon API to retrieve information about pokemons including name, type, weight, height, etc.
2. Display Information: Displaying the retrieved information about a pokemon and display on screen.
3. Display Image: I used coil to display the image of pokemons.

Components
MainActivity
Entry point of the app(using Jetpack Compose).


Pokemon
Data class representing the structure of a Pokémon.


PokemonApiService
  Handles API calls to fetch Pokémon data using OkHttp.(Initially tried to follow the lecture notes but due to unknown reason that I was not able to fix, I used a different way of calling API then the lecture notes).
  Parses JSON response to extract Pokémon details, including image, type, ability, size, weight, and evolution chain.
  OkHttpClient initialization creates and execute the HTTP requests, then fetchPokemonData a suspending function takes the "pokemonName" as a paramter and fetches the data of the given 
  pokemon.
  Using the Reqeust.Builder function so that it creates the GET request to the PokeAPI, then by using httpClient.newCall(request).execute() it executes the request synchronously.
  response.isSuccessful checks if it is successful, and response.body?.string() is read(the JSON data). ParsePokemonData method is the one that parses the JSON data.
  The evolution chain part I do not fully understand the functionallity, so it is not working as I intended it to do. 

  
PokemonFinder
  Composable function responsible for the UI layout and user interaction.
  Allows users to search for Pokémon by name and displays retrieved Pokémon information.
  This is the Composable function for the UI layout and managing the state of entered pokemon name and fetched pokemon information. This part of the code displays the fetched Pokemon details in a structured format.

  
PokemonViewModel
  This was the part that I could not understand much and used other resources more than other parts of the code. 
  I understand that it uses Coroutine's viewModelScope to handle API calls for fetching Pokémon details and utilizes Coil's ImageLoader for asynchronously loading Pokémon images.

Pokemonball.kt and PokemonSearcher.kt are codes that I initially tried using but couldn't make it work. Kept the code just in case, however they are not being called(Forgot to delete them.)

Libraries Used
Jetpack Compose: For building the UI.
OkHttp: For making network requests and handling API communication.
Coil: For loading images asynchronously with caching and placeholders.

