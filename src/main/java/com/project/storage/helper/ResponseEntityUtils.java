package com.project.storage.helper;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtils {

    private ResponseEntityUtils() {} // construtor privado para evitar instância

    public static <T> ResponseEntity<List<T>> fromList(List<T> list){
        if (list == null || list.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(list);
    }

    public static <T> ResponseEntity<List<T>> fromList(List<T> list, Map<String, String> headers){
        if (list == null || list.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        headers.forEach(builder::header);

        return builder.body(list);
    }

    public static <T> ResponseEntity<T> fromOptional(Optional<T> optional) {
        return optional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public static <T> ResponseEntity<T> fromObject(T obj){
        if (obj == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(obj);
    }

    // 🔹 Retorno customizado (status + body)
    public static <T> ResponseEntity<T> withStatus(HttpStatus status, T body) {
        return ResponseEntity.status(status).body(body);
    }

    // 🔹 Retorno customizado (status + headers + body)
    public static <T> ResponseEntity<T> withStatusAndHeaders(HttpStatus status, Map<String, String> headers, T body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);

        return ResponseEntity.status(status).headers(httpHeaders).body(body);
    }

    // 🔹 Created (201 + Location + body opcional)
    public static <T> ResponseEntity<T> created(URI location, T body) {
        return ResponseEntity.created(location).body(body);
    }

    public static <T> ResponseEntity<T> deleted() {
        return ResponseEntity.noContent().build();
    }

}
