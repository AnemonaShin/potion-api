package router

import "net/http"

// Handler para solicitudes generales
func NewRouter() http.Handler {
	//Mux General Servidor
	mux := http.NewServeMux()

	//Los Handler van acá

	//Regresa el mux con los handlers deseados
	return mux
}
