package client

import (
	"Go-Poke/internal/model"
	"encoding/json"
	"fmt"
	"net/http"
	"time"
)

const url = "https://pokeapi.co/api/v2/pokemon"

type PokeClient struct {
	httpClient *http.Client
}

func NewPokeClient() *PokeClient {
	return &PokeClient{
		httpClient: &http.Client{Timeout: 10 * time.Second},
	}
}

func (c *PokeClient) FetchPokemon(name string) (*model.Pokemon, error) {
	callUrl := fmt.Sprintf("%v/%v", url, name)

	resp, err := c.httpClient.Get(callUrl)

	if err != nil {
		return nil, err
	}

	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		return nil, fmt.Errorf("error al consultar PokeAPI: status %d", resp.StatusCode)
	}

	var pokemon model.Pokemon
	if err := json.NewDecoder(resp.Body).Decode(&pokemon); err != nil {
		return nil, err
	}

	return &pokemon, nil
}
