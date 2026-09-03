package org.example.utils;

import org.example.exceptions.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    @DisplayName("Deve validar string não vazia com sucesso")
    void testValidateNotBlankSuccess() {
        assertDoesNotThrow(() -> ValidationUtils.validateNotBlank("Texto válido", "campo"));
    }

    @Test
    @DisplayName("Deve lançar ValidationException para string vazia ou nula")
    void testValidateNotBlankFailure() {
        assertThrows(ValidationException.class, () -> ValidationUtils.validateNotBlank("", "campo"));
        assertThrows(ValidationException.class, () -> ValidationUtils.validateNotBlank("   ", "campo"));
        assertThrows(ValidationException.class, () -> ValidationUtils.validateNotBlank(null, "campo"));
    }

    @Test
    @DisplayName("Deve validar e-mail correto")
    void testValidateEmailSuccess() {
        assertDoesNotThrow(() -> ValidationUtils.validateEmail("usuario@teste.com"));
        assertDoesNotThrow(() -> ValidationUtils.validateEmail("admin.empresa@sub.domain.org"));
    }

    @Test
    @DisplayName("Deve lançar ValidationException para e-mail com formato inválido")
    void testValidateEmailFailure() {
        assertThrows(ValidationException.class, () -> ValidationUtils.validateEmail("email-invalido"));
        assertThrows(ValidationException.class, () -> ValidationUtils.validateEmail("usuario@com"));
        assertThrows(ValidationException.class, () -> ValidationUtils.validateEmail("@dominio.com"));
    }

    @Test
    @DisplayName("Deve validar tamanho mínimo de string")
    void testValidateMinLength() {
        assertDoesNotThrow(() -> ValidationUtils.validateMinLength("123456", 6, "senha"));
        assertThrows(ValidationException.class, () -> ValidationUtils.validateMinLength("12345", 6, "senha"));
    }

    @Test
    @DisplayName("Deve validar números positivos")
    void testValidatePositive() {
        assertDoesNotThrow(() -> ValidationUtils.validatePositive(10.5, "valor"));
        assertThrows(ValidationException.class, () -> ValidationUtils.validatePositive(0.0, "valor"));
        assertThrows(ValidationException.class, () -> ValidationUtils.validatePositive(-5.0, "valor"));
    }

    @Test
    @DisplayName("Deve converter UUID valido a partir de String")
    void testParseUuidSuccess() {
        UUID randomUuid = UUID.randomUUID();
        UUID parsed = ValidationUtils.parseUuid(randomUuid.toString());
        assertEquals(randomUuid, parsed);
    }

    @Test
    @DisplayName("Deve lançar ValidationException ao converter UUID invalido")
    void testParseUuidFailure() {
        assertThrows(ValidationException.class, () -> ValidationUtils.parseUuid("uuid-invalido-123"));
    }
}
