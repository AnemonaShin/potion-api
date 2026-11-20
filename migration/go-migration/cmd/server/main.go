package main

import (
	"go-migration/internal/router"
	"log"
	"net/http"
)

func main() {

	//Se define el enrutador que luego hara continuidad en los handlers especificos
	r := router.NewRouter()

	//Se define el puerto a levantar y el enrutador handler que se usara. (En este caso el router)

	log.Println("Servidor levantado en el puerto 8080.")
	log.Fatal(http.ListenAndServe(":8080", r))
}
