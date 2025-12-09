package com.sigaa.seguridad;

import com.sigaa.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seguridad")
@CrossOrigin("*")
public class SeguridadController {

    private final SeguridadService seguridadService;
    private final RolRepository rolRepo;
    private final PermisoRepository permisoRepo;
    private final UsuarioRolRepository usuarioRolRepo;

    public SeguridadController(SeguridadService seguridadService,
                               RolRepository rolRepo,
                               PermisoRepository permisoRepo,
                               UsuarioRolRepository usuarioRolRepo) {
        this.seguridadService = seguridadService;
        this.rolRepo = rolRepo;
        this.permisoRepo = permisoRepo;
        this.usuarioRolRepo = usuarioRolRepo;
    }

    // ============================================
    //                  ROLES
    // ============================================
    @PostMapping("/roles/crear")
    public ApiResponse<Rol> crearRol(@RequestParam String nombre) {
        Rol r = seguridadService.crearRol(nombre.toUpperCase());
        return new ApiResponse<>(true, "Rol creado", r);
    }

    @GetMapping("/roles")
    public List<Rol> listarRoles() {
        return rolRepo.findAll();
    }

    // ============================================
    //                PERMISOS
    // ============================================
    @PostMapping("/permisos/crear")
    public ApiResponse<Permiso> crearPermiso(
            @RequestParam String codigo,
            @RequestParam String descripcion) {

        Permiso p = seguridadService.crearPermiso(codigo.toUpperCase(), descripcion);
        return new ApiResponse<>(true, "Permiso creado", p);
    }

    @GetMapping("/permisos")
    public List<Permiso> listarPermisos() {
        return permisoRepo.findAll();
    }

    // ============================================
    //        ASIGNAR PERMISO A ROL
    // ============================================
    @PostMapping("/roles/{rolId}/permisos/{permisoId}")
    public ApiResponse<RolPermiso> asignarPermisoARol(
            @PathVariable Long rolId,
            @PathVariable Long permisoId) {

        RolPermiso rp = seguridadService.asignarPermisoARol(rolId, permisoId);
        return new ApiResponse<>(true, "Permiso asignado al rol", rp);
    }

    @GetMapping("/roles/{rolId}/permisos")
    public List<RolPermiso> listarPermisosDelRol(@PathVariable Long rolId) {
        return seguridadService
                .listarPermisosDeRol(rolId);
    }


    // ============================================
    //        ASIGNAR ROL A USUARIO
    // ============================================
    @PostMapping("/usuarios/{usuarioId}/roles/{rolId}")
    public ApiResponse<UsuarioRol> asignarRolAUsuario(
            @PathVariable Long usuarioId,
            @PathVariable Long rolId) {

        UsuarioRol ur = seguridadService.asignarRolAUsuario(usuarioId, rolId);
        return new ApiResponse<>(true, "Rol asignado al usuario", ur);
    }

    @GetMapping("/usuarios/{usuarioId}/roles")
    public List<UsuarioRol> listarRolesUsuario(@PathVariable Long usuarioId) {
        return usuarioRolRepo.findByUsuarioId(usuarioId);
    }
}
