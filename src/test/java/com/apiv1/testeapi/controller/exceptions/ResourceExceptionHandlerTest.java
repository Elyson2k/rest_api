package com.apiv1.testeapi.controller.exceptions;

import com.apiv1.testeapi.service.exceptions.DataIntegrityViolationException;
import com.apiv1.testeapi.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void startUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void objNotFound() {
        ResponseEntity<StandardError> response = exceptionHandler.objNotFound(new ObjectNotFoundException("Error: Usuario não existe."), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response. getBody().getClass());
        assertEquals("Error: Usuario não existe.", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void dtIntViolation() {
        ResponseEntity<StandardError> response = exceptionHandler.dtIntViolation(new DataIntegrityViolationException("Error: Email ja esta cadastrado!!"), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response. getBody().getClass());
        assertEquals("Error: Email ja esta cadastrado!!", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());

    }
}