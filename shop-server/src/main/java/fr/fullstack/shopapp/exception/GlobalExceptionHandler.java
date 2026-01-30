package fr.fullstack.shopapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * this method allows validation on the nested object
     */
    @InitBinder
    private void activateDirectFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }

    /**
     * Gère les conflits d'horaires d'ouverture
     */
    @ExceptionHandler(OpeningHoursConflictException.class)
    public ResponseEntity<Map<String, Object>> handleOpeningHoursConflict(OpeningHoursConflictException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Conflit d'horaires");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gère les autres exceptions génériques
     */
    /*
     * @ExceptionHandler(Exception.class)
     * public ResponseEntity<Map<String, Object>> handleGenericException(Exception
     * ex) {
     * Map<String, Object> body = new LinkedHashMap<>();
     * body.put("timestamp", LocalDateTime.now());
     * body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
     * body.put("error", "Erreur serveur");
     * body.put("message", "Une erreur inattendue s'est produite : " +
     * ex.getMessage());
     * 
     * return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     */
}