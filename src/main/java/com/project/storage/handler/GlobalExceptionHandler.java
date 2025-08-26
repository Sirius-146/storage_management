package com.project.storage.handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Not Found
    @ExceptionHandler(WorkerNotFoundException.class)
    public ResponseEntity<Object> handleWorkerNotFound(WorkerNotFoundException ex){
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 401 - Unauthorized (não autenticado)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorized(UnauthorizedException ex){
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    // 403 - Forbidden (autenticado mas sem permissão)
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(org.springframework.security.access.AccessDeniedException ex){
        return buildErrorResponse(HttpStatus.FORBIDDEN, "Acesso negado: você não possui permissão para este recurso");
    }

    // 500- fallback genérico para exceções não tratadas
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntime(RuntimeException ex){
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor");
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String msg){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", msg);

        return ResponseEntity.status(status).body(body);
    }

}
