package com.sigaa.seguridad;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeguridadService {

    private final RolRepository rolRepo;
    private final PermisoRepository permisoRepo;
    private final RolPermisoRepository rolPermisoRepo;
    private final UsuarioRolRepository usuarioRolRepo;

    public SeguridadService(
            RolRepository rolRepo,
            PermisoRepository permisoRepo,
            RolPermisoRepository rolPermisoRepo,
            UsuarioRolRepository usuarioRolRepo
    ) {
        this.rolRepo = rolRepo;
        this.permisoRepo = permisoRepo;
        this.rolPermisoRepo = rolPermisoRepo;
        this.usuarioRolRepo = usuarioRolRepo;
    }

    // ============================
    //        ROLES
    // ============================
    public Rol crearRol(String nombre) {
        if (rolRepo.existsByNombre(nombre)) {
            throw new RuntimeException("El rol ya existe: " + nombre);
        }

        Rol r = new Rol(nombre);
        return rolRepo.save(r);
    }

    // ============================
    //       PERMISOS
    // ============================
    public Permiso crearPermiso(String codigo, String descripcion) {
        if (permisoRepo.findByCodigo(codigo) != null) {
            throw new RuntimeException("El permiso ya existe: " + codigo);
        }

        Permiso p = new Permiso(codigo, descripcion);
        return permisoRepo.save(p);
    }

    // ============================
    //   ASIGNAR PERMISO A ROL
    // ============================
    public RolPermiso asignarPermisoARol(Long rolId, Long permisoId) {

        Rol rol = rolRepo.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolId));

        Permiso permiso = permisoRepo.findById(permisoId)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado: " + permisoId));

        if (rolPermisoRepo.existsByRolIdAndPermisoId(rolId, permisoId)) {
            throw new RuntimeException("El permiso ya está asignado a este rol.");
        }

        RolPermiso rp = new RolPermiso();
        rp.setRol(rol);
        rp.setPermiso(permiso);

        return rolPermisoRepo.save(rp);
    }

    // ============================
    //     ASIGNAR ROL A USUARIO
    // ============================
    public UsuarioRol asignarRolAUsuario(Long usuarioId, Long rolId) {

        if (usuarioRolRepo.existsByUsuarioIdAndRolId(usuarioId, rolId)) {
            throw new RuntimeException("El usuario ya tiene este rol.");
        }

        Rol rol = rolRepo.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolId));

        UsuarioRol ur = new UsuarioRol();
        ur.setUsuarioId(usuarioId);
        ur.setRol(rol);

        return usuarioRolRepo.save(ur);
    }

    // ============================
    // VALIDAR PERMISO DEL USUARIO
    // ============================
    public boolean usuarioTienePermiso(Long usuarioId, String permisoCodigo) {

        // 1. Buscar permiso objetivo
        Permiso permiso = permisoRepo.findByCodigo(permisoCodigo);
        if (permiso == null) {
            return false; // permiso no existe
        }

        Long permisoId = permiso.getId();

        // 2. Obtener roles del usuario
        List<UsuarioRol> rolesUsuario = usuarioRolRepo.findByUsuarioId(usuarioId);

        // 3. Ver si alguno de los roles tiene ese permiso
        for (UsuarioRol ur : rolesUsuario) {
            List<RolPermiso> permisosRol = rolPermisoRepo.findByRolId(ur.getRol().getId());

            for (RolPermiso rp : permisosRol) {
                if (rp.getPermiso().getId().equals(permisoId)) {
                    return true; // sí tiene permiso
                }
            }
        }

        return false; // ningún rol contiene ese permiso
    }


    public List<RolPermiso> listarPermisosDeRol(Long rolId) {
        return rolPermisoRepo.findByRolId(rolId);
    }
}
