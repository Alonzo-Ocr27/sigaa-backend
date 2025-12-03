package com.sigaa.comercial;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContratoComercialService {

    private final ContratoComercialRepository contratoRepo;
    private final LocalComercialRepository localRepo;
    private final ArrendatarioRepository arrendatarioRepo;

    public ContratoComercialService(ContratoComercialRepository contratoRepo,
                                    LocalComercialRepository localRepo,
                                    ArrendatarioRepository arrendatarioRepo) {
        this.contratoRepo = contratoRepo;
        this.localRepo = localRepo;
        this.arrendatarioRepo = arrendatarioRepo;
    }

    public ContratoComercial crear(Long localId, Long arrendatarioId,
                                   LocalDate inicio, LocalDate fin) {

        LocalComercial local = localRepo.findById(localId).orElse(null);
        Arrendatario arr = arrendatarioRepo.findById(arrendatarioId).orElse(null);

        if (local == null || arr == null) {
            throw new RuntimeException("Local o arrendatario no encontrado");
        }

        if (!"DISPONIBLE".equals(local.getEstado())) {
            throw new RuntimeException("El local no está disponible");
        }

        // cálculo automático
        double monto = local.getMetrosCuadrados() * local.getPrecioBase();

        ContratoComercial c = new ContratoComercial();
        c.setLocal(local);
        c.setArrendatario(arr);
        c.setFechaInicio(inicio);
        c.setFechaFin(fin);
        c.setMontoMensual(monto);
        c.setEstado("ACTIVO");

        // marcar local como ocupado
        local.setEstado("OCUPADO");
        localRepo.save(local);

        return contratoRepo.save(c);
    }

    public List<ContratoComercial> listar() {
        return contratoRepo.findAll();
    }

    public ContratoComercial buscar(Long id) {
        return contratoRepo.findById(id).orElse(null);
    }

    public boolean finalizar(Long id) {
        ContratoComercial c = buscar(id);
        if (c == null) return false;
        c.setEstado("FINALIZADO");

        LocalComercial local = c.getLocal();
        if (local != null) {
            local.setEstado("DISPONIBLE");
            localRepo.save(local);
        }

        contratoRepo.save(c);
        return true;
    }

    public boolean cancelar(Long id) {
        ContratoComercial c = buscar(id);
        if (c == null) return false;
        c.setEstado("CANCELADO");

        LocalComercial local = c.getLocal();
        if (local != null) {
            local.setEstado("DISPONIBLE");
            localRepo.save(local);
        }

        contratoRepo.save(c);
        return true;
    }
}