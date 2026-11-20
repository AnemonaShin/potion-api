package handler

import (
	"Go-Poke/internal/client"
	"encoding/json"
	"net/http"
	"strings"
)

type PokemonHandler struct {
	pokeClient *client.PokeClient
}

func NewPokemonHandler(pokeClient *client.PokeClient) *PokemonHandler {
	return &PokemonHandler{pokeClient: pokeClient}
}

func (h *PokemonHandler) GetPokemon(w http.ResponseWriter, r *http.Request) {
	name := r.URL.Query().Get("name")
	if strings.TrimSpace(name) == "" {
		http.Error(w, "El parámetro 'name' es obligatorio (ej. /pokemon?name=pikachu)", http.StatusBadRequest)
		return
	}

	pokemon, err := h.pokeClient.FetchPokemon(strings.ToLower(name))
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(pokemon)
}
