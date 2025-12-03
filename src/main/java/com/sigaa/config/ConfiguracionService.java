package com.sigaa.config;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConfiguracionService {

    private final ParametroSistemaRepository paramRepo;
    private final CatalogoAeronaveRepository aerRepo;
    private final CatalogoDestinoRepository destRepo;
    private final CatalogoAerolineaRepository aeroRepo2;

    public ConfiguracionService(
            ParametroSistemaRepository paramRepo,
            CatalogoAeronaveRepository aerRepo,
            CatalogoDestinoRepository destRepo,
            CatalogoAerolineaRepository aeroRepo2
    ) {
        this.paramRepo = paramRepo;
        this.aerRepo = aerRepo;
        this.destRepo = destRepo;
        this.aeroRepo2 = aeroRepo2;
    }

    // --- PARAMETROS DEL SISTEMA ---
    public ParametroSistema actualizarParametro(String clave, String valor) {
        ParametroSistema p = paramRepo.findByClave(clave);
        if (p == null) {
            p = new ParametroSistema();
            p.setClave(clave);
        }
        p.setValor(valor);
        return paramRepo.save(p);
    }

    public String obtenerParametro(String clave) {
        ParametroSistema p = paramRepo.findByClave(clave);
        return p != null ? p.getValor() : null;
    }

    // --- AERONAVES ---
    public List<CatalogoAeronave> listarAeronaves() {
        return aerRepo.findAll();
    }

    // --- DESTINOS ---
    public List<CatalogoDestino> listarDestinos() {
        return destRepo.findAll();
    }

    // --- AEROLÍNEAS ---
    public List<CatalogoAerolinea> listarAerolineas() {
        return aeroRepo2.findAll();
    }
}