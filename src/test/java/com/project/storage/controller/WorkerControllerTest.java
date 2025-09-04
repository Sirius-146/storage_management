package com.project.storage.controller;

import com.project.storage.dto.worker.WorkerRequestDTO;
import com.project.storage.dto.worker.WorkerResponseDTO;
import com.project.storage.model.Role;
import com.project.storage.service.WorkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkerControllerTest {

    @Mock
    private WorkerService workerService;

    @InjectMocks
    private WorkerController workerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_shouldReturnCreatedResponse() {
        WorkerRequestDTO request = new WorkerRequestDTO("Name", "user", "pass", "Dept", Role.ADMIN);
        WorkerResponseDTO responseDTO = new WorkerResponseDTO(1, "Name", "user", Role.ADMIN);
        when(workerService.create(request)).thenReturn(responseDTO);

        ResponseEntity<WorkerResponseDTO> response = workerController.register(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        assertEquals(URI.create("/workers/1"), response.getHeaders().getLocation());
    }

    @Test
    void getAllWorkers_shouldReturnListResponse() {
        WorkerResponseDTO worker1 = new WorkerResponseDTO(1, "Name1", "user1", Role.ADMIN);
        WorkerResponseDTO worker2 = new WorkerResponseDTO(2, "Name2", "user2", Role.HUMAN_RESOURCES);
        when(workerService.getAllWorkers()).thenReturn(List.of(worker1, worker2));

        ResponseEntity<List<WorkerResponseDTO>> response = workerController.getAllWorkers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("2", response.getHeaders().getFirst("X-Total-Count"));
    }

    @Test
    void findById_shouldReturnWorkerResponse() {
        WorkerResponseDTO worker = new WorkerResponseDTO(1, "Name", "user", Role.ADMIN);
        when(workerService.findById(1)).thenReturn(Optional.of(worker));

        ResponseEntity<WorkerResponseDTO> response = workerController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(worker, response.getBody());
    }

    @Test
    void patch_shouldReturnUpdatedWorker() {
        WorkerRequestDTO dto = new WorkerRequestDTO("Name", "user", "pass", "Dept", Role.ADMIN);
        WorkerResponseDTO updated = new WorkerResponseDTO(1, "Name", "user", Role.ADMIN);
        when(workerService.patch(1, dto)).thenReturn(updated);

        ResponseEntity<WorkerResponseDTO> response = workerController.patch(1, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
    }

    @Test
    void delete_shouldReturnDeletedResponse() {
        doNothing().when(workerService).delete(1);

        ResponseEntity<Void> response = workerController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
