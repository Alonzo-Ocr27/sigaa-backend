package com.sigaa.checkin;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PasajeroCheckinService {

    private final PasajeroCheckinRepository repo;

    public PasajeroCheckinService(PasajeroCheckinRepository repo) {
        this.repo = repo;
    }

    // ============================================
    //                 CREAR CHECK-IN
    // ============================================
    public PasajeroCheckinDTO hacerCheckin(PasajeroCheckin p) {

        p.setAsiento(p.getAsiento().toUpperCase().trim());

        // Validar asiento ocupado
        PasajeroCheckin ocupado = repo.findByVueloIdAndAsiento(p.getVueloId(), p.getAsiento());
        if (ocupado != null && !ocupado.getEstado().equals("CANCELADO")) {
            throw new RuntimeException("El asiento ya está ocupado en este vuelo");
        }

        // Boarding pass único
        String codigo = "BP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        p.setCodigoBoardingPass(codigo);

        p.setEstado("COMPLETADO");
        p.setFechaHoraCheckin(LocalDateTime.now());

        repo.save(p);

        return new PasajeroCheckinDTO(p);
    }

    // ============================================
    //        LISTAR PASAJEROS DEL VUELO
    // ============================================
    public List<PasajeroCheckinDTO> listarPorVuelo(Long vueloId) {
        return repo.findByVueloId(vueloId)
                .stream()
                .map(PasajeroCheckinDTO::new)
                .toList();
    }

    // ============================================
    //                  CANCELAR
    // ============================================
    public boolean cancelar(Long id) {
        PasajeroCheckin p = repo.findById(id).orElse(null);
        if (p == null) return false;

        p.setEstado("CANCELADO");
        repo.save(p);
        return true;
    }

    // ============================================
    //              CAMBIAR ASIENTO
    // ============================================
    public PasajeroCheckinDTO actualizarAsiento(Long id, String nuevoAsiento) {

        PasajeroCheckin p = repo.findById(id).orElse(null);
        if (p == null) return null;

        nuevoAsiento = nuevoAsiento.toUpperCase().trim();

        PasajeroCheckin ocupado = repo.findByVueloIdAndAsiento(p.getVueloId(), nuevoAsiento);
        if (ocupado != null && !ocupado.getEstado().equals("CANCELADO")) {
            throw new RuntimeException("El asiento ya está ocupado");
        }

        p.setAsiento(nuevoAsiento);
        repo.save(p);

        return new PasajeroCheckinDTO(p);
    }

    // ============================================
    //           BUSCAR POR BOARDING PASS
    // ============================================
    public PasajeroCheckinDTO buscarPorBoardingPass(String codigo) {
        PasajeroCheckin p = repo.findByCodigoBoardingPass(codigo);
        return p == null ? null : new PasajeroCheckinDTO(p);
    }
}