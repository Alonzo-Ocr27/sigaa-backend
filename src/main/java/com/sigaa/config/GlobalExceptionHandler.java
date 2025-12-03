package com.sigaa.config;

import com.sigaa.common.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ============================================
    //  ⚠️ 1. VALIDACIONES @Valid
    // ============================================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleValidation(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return new ApiResponse<>(false, msg, null);
    }

    // ============================================
    //  ⚠️ 2. JSON MAL FORMADO
    // ============================================
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<String> handleJsonError(HttpMessageNotReadableException e) {
        return new ApiResponse<>(false, "JSON inválido o incompleto", null);
    }

    // ============================================
    //  ⚠️ 3. FALTAN PARÁMETROS (@RequestParam)
    // ============================================
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse<String> handleMissingParam(MissingServletRequestParameterException e) {
        return new ApiResponse<>(false,
                "Falta el parámetro requerido: " + e.getParameterName(),
                null);
    }

    // ============================================
    //  ⚠️ 4. AUTENTICACIÓN / JWT
    // ============================================
    @ExceptionHandler(AuthenticationException.class)
    public ApiResponse<String> handleAuthError(AuthenticationException e) {
        return new ApiResponse<>(false, "No autorizado: " + e.getMessage(), null);
    }

    // ============================================
    //  ⚠️ 5. ERRORES DE BASE DE DATOS
    // ============================================
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiResponse<String> handleSQLIntegrity(DataIntegrityViolationException e) {
        return new ApiResponse<>(false,
                "Error de integridad en la base de datos (FK, UNIQUE, NOT NULL)",
                null);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ApiResponse<String> handleDuplicate(DuplicateKeyException e) {
        return new ApiResponse<>(false,
                "Registro duplicado: ya existe un dato con esa clave",
                null);
    }

    // ============================================
    //  ⚠️ 6. ERRORES DE NEGOCIO (Runtime)
    // ============================================
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<String> handleRuntime(RuntimeException e) {
        return new ApiResponse<>(false, e.getMessage(), null);
    }

    // ============================================
    //  ⚠️ 7. ERROR GENERAL (no especificado)
    // ============================================
    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleGeneric(Exception e) {

        // Log interno
        e.printStackTrace();

        return new ApiResponse<>(
                false,
                "Error interno del servidor. Intente nuevamente.",
                null
        );
    }
}