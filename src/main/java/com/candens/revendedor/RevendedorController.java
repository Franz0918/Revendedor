package com.candens.revendedor;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate; //PARA HACER LLAMADAS A OTRAS APIS
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/revendedor/carros")
@CrossOrigin(origins = "*") //PERMITE A LAS APLICACCIONES DE FRONT PUEDAN CONSULTAR ESTA API DESDE CUALQIUER ORIGEN
public class RevendedorController {


    // URL DEL API CARROS
    private final String URL_API_Carros2 = "http://localhost:8080/api/carros";
    private final RestTemplate restTemplate; //ES EL OBJETO NATIVO QUE HACE PETICIONES HTTP QUE USA SPIRNG PARA DISPARAR PETIONES A LA API CARRITOS

    public RevendedorController(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    // 1. POST - REGISTRAR
    @PostMapping("/registrar")
    public ResponseEntity<?> postCarro(@RequestBody Map<String, Object> datito) {
        String url = URL_API_Carros2 + "/registrar";

        HttpHeaders cabecera = new HttpHeaders();
        cabecera.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> peticion = new HttpEntity<>(datito, cabecera); // HTTP ENTITY ES PARA FUSIONAR EL DATO CON LA CABECERA, Y ME INDICA QUE SOLO TRANSPORTA DENTRO UN MAP LLAVE VALOR

        ResponseEntity<Map> respuesta = restTemplate.postForEntity(url, peticion, Map.class);// CRUZA INTERNET
        return ResponseEntity.status(respuesta.getStatusCode()).body(respuesta.getBody());
    }

    // 2. GET - BUSCAR POR ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getCarro(@PathVariable Integer id) {
        String url = URL_API_Carros2 + "/buscar/" + id;

        try {
            ResponseEntity<Map> respuesta = restTemplate.getForEntity(url, Map.class);
            return ResponseEntity.status(respuesta.getStatusCode()).body(respuesta.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El carro con ID " + id + " no existe en la API original.");
        }
    }

    // 3. GET - LISTAR TODOS
    @GetMapping("/listar")
    public ResponseEntity<?> listCarro() {
        String url = URL_API_Carros2 + "/listar";

        ResponseEntity<List> respuesta = restTemplate.getForEntity(url, List.class);
        return ResponseEntity.status(respuesta.getStatusCode()).body(respuesta.getBody());
    }

    // 4. PUT - ACTUALIZAR
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> putCarro(@PathVariable Integer id, @RequestBody Map<String, Object> datito) {
        String url = URL_API_Carros2 + "/actualizar/" + id;

        HttpHeaders cabecera = new HttpHeaders();
        cabecera.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> peticion = new HttpEntity<>(datito, cabecera);

        try {
            //ResponseEntity<Map> respuesta = restTemplate.put(url, peticion, Map.class);
            ResponseEntity<Map> respuesta = restTemplate.exchange(url, HttpMethod.PUT, peticion, Map.class);
            return ResponseEntity.status(respuesta.getStatusCode()).body(respuesta.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo actualizar el carrito con ID " + id + " o no existe.");
        }
    }

    // 5. DELETE - ELIMINAR
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteCarro(@PathVariable Integer id) {
        String url = URL_API_Carros2 + "/eliminar/" + id;

        try {
            restTemplate.delete(url);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo eliminar el carro con ID " + id + "o no existe.");
        }
    }
}
