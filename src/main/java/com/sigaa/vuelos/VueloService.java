package com.sigaa.vuelos;

import com.sigaa.notificaciones.NotificacionService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VueloService {

    private final VueloRepository repo;
    private final NotificacionService notificacionService;

    public VueloService(VueloRepository repo, NotificacionService notificacionService) {
        this.repo = repo;
        this.notificacionService = notificacionService;
    }

    // =========================================
    //  CREAR VUELO
    // =========================================
    public Vuelo crear(Vuelo vuelo) {
        vuelo.setEstado("PROGRAMADO");
        Vuelo creado = repo.save(vuelo);

        notificacionService.enviar(
                "Nuevo vuelo programado",
                "El vuelo " + creado.getNumero() + " ha sido registrado en el sistema."
        );

        return creado;
    }

    // =========================================
    //  LISTAR
    // =========================================
    public List<Vuelo> listar() {
        return repo.findAll();
    }

    // =========================================
    //  BUSCAR
    // =========================================
    public Vuelo buscar(@NonNull Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vuelo no encontrado: " + id));
    }

    // =========================================
    //  ACTUALIZAR
    // =========================================
    public Vuelo actualizar(@NonNull Long id, Vuelo datos) {
        Vuelo v = buscar(id);

        v.setNumero(datos.getNumero());
        v.setOrigen(datos.getOrigen());
        v.setDestino(datos.getDestino());
        v.setFechaHoraSalida(datos.getFechaHoraSalida());
        v.setFechaHoraLlegada(datos.getFechaHoraLlegada());
        v.setTipoAeronave(datos.getTipoAeronave());

        Vuelo actualizado = repo.save(v);

        notificacionService.enviar(
                "Vuelo actualizado",
                "El vuelo " + actualizado.getNumero() + " ha sido modificado."
        );

        return actualizado;
    }

    // =========================================
    //  ELIMINAR
    // =========================================
    public boolean eliminar(@NonNull Long id) {
        Vuelo v = buscar(id);

        repo.delete(v);

        notificacionService.enviar(
                "Vuelo eliminado",
                "El vuelo " + v.getNumero() + " ha sido eliminado del sistema."
        );

        return true;
    }

    // =========================================
    //  CAMBIAR ESTADO
    // =========================================
    public Vuelo cambiarEstado(@NonNull Long id, String estado) {
        Vuelo v = buscar(id);
        v.setEstado(estado);

        Vuelo actualizado = repo.save(v);

        notificacionService.enviar(
                "Estado de vuelo actualizado",
                "El vuelo " + v.getNumero() + " ahora está: " + estado
        );

        return actualizado;
    }
}