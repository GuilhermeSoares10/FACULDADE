package org.example.utils;

import org.example.exceptions.ValidationException;

import java.util.UUID;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    );

    private ValidationUtils() {
        // Utility class private constructor
    }

    public static void validateNotBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("O campo '" + fieldName + "' não pode ser vazio ou nulo.");
        }
    }

    public static void validateEmail(String email) {
        validateNotBlank(email, "email");
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("O e-mail fornecido (" + email + ") possui formato inválido.");
        }
    }

    public static void validateMinLength(String value, int minLength, String fieldName) {
        validateNotBlank(value, fieldName);
        if (value.trim().length() < minLength) {
            throw new ValidationException("O campo '" + fieldName + "' deve ter no mínimo " + minLength + " caracteres.");
        }
    }

    public static void validatePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new ValidationException("O campo '" + fieldName + "' deve ser maior que zero. Valor informado: " + value);
        }
    }

    public static void validateNonNegative(double value, String fieldName) {
        if (value < 0) {
            throw new ValidationException("O campo '" + fieldName + "' não pode ser negativo. Valor informado: " + value);
        }
    }

    public static void validateNotNull(Object object, String objectName) {
        if (object == null) {
            throw new ValidationException("O objeto '" + objectName + "' não pode ser nulo.");
        }
    }

    public static UUID parseUuid(String uuidStr) {
        validateNotBlank(uuidStr, "UUID");
        try {
            return UUID.fromString(uuidStr.trim());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("O valor fornecido ('" + uuidStr + "') não é um UUID válido.");
        }
    }
}
