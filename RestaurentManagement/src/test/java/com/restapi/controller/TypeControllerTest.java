package com.restapi.controller;

import com.restapi.controllers.TypeController;
import com.restapi.dto.TypeDTO;
import com.restapi.exceptions.RMValidateException;
import com.restapi.dto.SuccessResponse;
import com.restapi.services.TypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TypeControllerTest {

    @Mock
    private TypeService typeService;

    @InjectMocks
    private TypeController typeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTypesWhenSuccessShouldReturnSuccessResponse() {
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setData(new ArrayList<>());
        successResponse.setMessage("Get types successfully!");
        successResponse.setStatus(HttpStatus.OK.getReasonPhrase());
        assertEquals(successResponse,typeController.getAllTypes().getBody());
        verify(typeService,times(1)).getAllTypes();
    }

    @Test
    void getTypeByIdWhenTypeNotFoundThrowErrorResponse() {
        UUID id = UUID.randomUUID();
        when(typeService.getTypeById(id)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class,()->typeController.getTypeById(id));
        verify(typeService,times(1)).getTypeById(id);
    }

    @Test
    void getTypeByIdWhenTypeFoundReturnSuccessResponseHasTypeDTO() {
        UUID id = UUID.randomUUID();
        TypeDTO typeDTO = new TypeDTO("hot");
        when(typeService.getTypeById(id)).thenReturn(typeDTO);
        assertEquals(typeDTO, Objects.requireNonNull(typeController.getTypeById(id).getBody()).getData());
        verify(typeService,times(1)).getTypeById(id);
    }

    @Test
    void createTypeWhenSuccessShouldReturnSuccessResponseHasTypeDTO() {
        TypeDTO typeDTO = new TypeDTO("hot");
        when(typeService.createType(typeDTO)).thenReturn(typeDTO);
        assertEquals(typeDTO, Objects.requireNonNull(typeController.createType(typeDTO).getBody()).getData());
        verify(typeService,times(1)).createType(typeDTO);
    }

    @Test
    void updateTypeWhenTypeNotFoundShouldThrowErrorResponse() {
        UUID uuid = UUID.randomUUID();
        TypeDTO typeDTO = new TypeDTO();
        when(typeService.updateType(uuid,typeDTO)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class,()->typeController.updateType(uuid,typeDTO));
        verify(typeService,times(1)).updateType(uuid,typeDTO);
    }

    @Test
    void updateTypeWhenTypeFoundShouldReturnSuccessResponse() {
        UUID uuid = UUID.randomUUID();
        TypeDTO typeDTO = new TypeDTO("hot");
        when(typeService.updateType(uuid,typeDTO)).thenReturn(typeDTO);
        assertEquals(SuccessResponse.class, Objects.requireNonNull(typeController.updateType(uuid, typeDTO).getBody()).getClass());
        verify(typeService,times(1)).updateType(uuid,typeDTO);
    }

    @Test
    void updateTypeWhenTypeFoundShouldReturnTypeDTO() {
        UUID uuid = UUID.randomUUID();
        TypeDTO typeDTO = new TypeDTO("hot");
        when(typeService.updateType(uuid,typeDTO)).thenReturn(typeDTO);
        assertEquals(typeDTO, Objects.requireNonNull(typeController.updateType(uuid, typeDTO).getBody()).getData());
        verify(typeService,times(1)).updateType(uuid,typeDTO);
    }

    @Test
    void deleteTypeWhenTypeNotFoundShouldThrowErrorResponse() {
        UUID uuid = UUID.randomUUID();
        doThrow(RMValidateException.class).when(typeService).deleteType(uuid);
        assertThrows(RMValidateException.class,()->typeController.deleteType(uuid));
        verify(typeService,times(1)).deleteType(uuid);
    }

    @Test
    void deleteTypeWhenTypeFoundShouldReturnSuccessResponse() {
        UUID uuid = UUID.randomUUID();
        doNothing().when(typeService).deleteType(uuid);
        assertEquals(SuccessResponse.class, Objects.requireNonNull(typeController.deleteType(uuid).getBody()).getClass());
        verify(typeService,times(1)).deleteType(uuid);
    }
}