package com.sigaa.boarding;

import com.sigaa.checkin.PasajeroCheckin;
import com.sigaa.checkin.PasajeroCheckinService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardingService {

    private final BoardingRepository repo;
    private final PasajeroCheckinService checkinService;

    public BoardingService(BoardingRepository repo, PasajeroCheckinService checkinService) {
        this.repo = repo;
        this.checkinService = checkinService;
    }

    public BoardingRegistro escanear(String codigoBP) {

        // validar si el boarding pass existe
        PasajeroCheckin pasajero = checkinService.buscarPorBoardingPass(codigoBP);
        if (pasajero == null) {
            throw new RuntimeException("Boarding Pass inválido");
        }

        // validar duplicado
        BoardingRegistro existente = repo.findByCodigoBoardingPass(codigoBP);
        if (existente != null &&
            !existente.getEstado().equals("CANCELADO")) {

            existente.setEstado("DUPLICADO");
            repo.save(existente);
            throw new RuntimeException("El pasajero ya embarcó anteriormente");
        }

        // crear registro nuevo
        BoardingRegistro nuevo = new BoardingRegistro();
        nuevo.setVueloId(pasajero.getVueloId());
        nuevo.setCodigoBoardingPass(codigoBP);
        nuevo.setEstado("VALIDADO");
        nuevo.setFechaHoraEscaneo(LocalDateTime.now());

        return repo.save(nuevo);
    }

    public List<BoardingRegistro> listarPorVuelo(Long vueloId) {
        return repo.findByVueloId(vueloId);
    }

    public boolean cancelar(Long id) {
        BoardingRegistro r = repo.findById(id).orElse(null);
        if (r == null) return false;
        r.setEstado("CANCELADO");
        repo.save(r);
        return true;
    }
}