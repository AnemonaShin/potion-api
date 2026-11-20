package main

import (
	"Go-Poke/internal/client"
	"Go-Poke/internal/handler"
	"log"
	"net/http"
)

func main() {

	pokeClient := client.NewPokeClient()
	pokeHandler := handler.NewPokemonHandler(pokeClient)

	mux := http.NewServeMux()
	mux.HandleFunc("/pokemon", pokeHandler.GetPokemon)

	log.Println("Inicio del servidor en local puerto 8080")
	if err := http.ListenAndServe(":8080", mux); err != nil {
		log.Fatalf("Error iniciando el servidor: %v", err)
	}
}
