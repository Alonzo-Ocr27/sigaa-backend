package com.sigaa.seguridad;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeguridadService {

    private final RolRepository rolRepo;
    private final PermisoRepository permisoRepo;
    private final RolPermisoRepository rolPermisoRepo;
    private final UsuarioRolRepository usuarioRolRepo;

    public SeguridadService(RolRepository rolRepo, PermisoRepository permisoRepo,
                            RolPermisoRepository rolPermisoRepo, UsuarioRolRepository usuarioRolRepo) {
        this.rolRepo = rolRepo;
        this.permisoRepo = permisoRepo;
        this.rolPermisoRepo = rolPermisoRepo;
        this.usuarioRolRepo = usuarioRolRepo;
    }

    // ================================
    // ROLES
    // ================================
    public Rol crearRol(Rol r) {
        return rolRepo.save(r);
    }

    // ================================
    // PERMISOS
    // ================================
    public Permiso crearPermiso(Permiso p) {
        return permisoRepo.save(p);
    }

    // ================================
    // ASIGNAR PERMISO A ROL
    // ================================
    public RolPermiso asignarPermiso(Long rolId, Long permisoId) {
        Rol rol = rolRepo.findById(rolId).orElseThrow(
                () -> new RuntimeException("Rol no encontrado: " + rolId)
        );

        Permiso perm = permisoRepo.findById(permisoId).orElseThrow(
                () -> new RuntimeException("Permiso no encontrado: " + permisoId)
        );

        RolPermiso rp = new RolPermiso();
        rp.setRol(rol);
        rp.setPermiso(perm);

        return rolPermisoRepo.save(rp);
    }

    // ================================
    // ASIGNAR ROL A USUARIO
    // ================================
    public UsuarioRol asignarRolUsuario(Long usuarioId, Long rolId) {

        Rol rol = rolRepo.findById(rolId).orElseThrow(
                () -> new RuntimeException("Rol no encontrado: " + rolId)
        );

        UsuarioRol ur = new UsuarioRol();
        ur.setUsuarioId(usuarioId);
        ur.setRol(rol);

        return usuarioRolRepo.save(ur);
    }

    // ================================
    // VALIDAR PERMISOS DEL USUARIO
    // ================================
    public boolean usuarioTienePermiso(Long usuarioId, String permisoCodigo) {

        // 1. Permiso objetivo
        Permiso permisoBuscado = permisoRepo.findByCodigo(permisoCodigo);
        if (permisoBuscado == null) return false;

        Long permisoId = permisoBuscado.getId();

        // 2. Roles del usuario
        List<UsuarioRol> rolesAsignados = usuarioRolRepo.findByUsuarioId(usuarioId);

        // 3. Revisar permisos de cada rol del usuario
        for (UsuarioRol userRol : rolesAsignados) {

            List<RolPermiso> permisosRol =
                    rolPermisoRepo.findByRolId(userRol.getRol().getId());

            for (RolPermiso rp : permisosRol) {
                if (rp.getPermiso().getId().equals(permisoId)) {
                    return true;  // Tiene permiso
                }
            }
        }

        return false;
    }
}