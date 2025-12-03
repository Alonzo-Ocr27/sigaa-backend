package com.sigaa.config;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config")
@CrossOrigin("*")
public class ConfiguracionController {

    private final ConfiguracionService config;

    public ConfiguracionController(ConfiguracionService config) {
        this.config = config;
    }

    // --- PARÁMETROS ---
    @GetMapping("/param/{clave}")
    public String obtener(@PathVariable String clave) {
        return config.obtenerParametro(clave);
    }

    @PostMapping("/param/{clave}")
    public ParametroSistema guardar(
            @PathVariable String clave,
            @RequestBody String valor) {

        return config.actualizarParametro(clave, valor);
    }

    // --- CATÁLOGOS ---
    @GetMapping("/aeronaves")
    public List<CatalogoAeronave> aeronaves() {
        return config.listarAeronaves();
    }

    @GetMapping("/destinos")
    public List<CatalogoDestino> destinos() {
        return config.listarDestinos();
    }

    @GetMapping("/aerolineas")
    public List<CatalogoAerolinea> aerolineas() {
        return config.listarAerolineas();
    }
}