package com.project.storage.service;

import com.project.storage.builder.LoginRequestDTOBuilder;
import com.project.storage.builder.WorkerRequestDTOBuilder;
import com.project.storage.dto.login.LoginRequestDTO;
import com.project.storage.dto.worker.WorkerRequestDTO;
import com.project.storage.dto.worker.WorkerResponseDTO;
import com.project.storage.handler.NotFoundException;
import com.project.storage.handler.UnauthorizedException;
import com.project.storage.model.Role;
import com.project.storage.model.Worker;
import com.project.storage.repository.WorkerRepository;
import com.project.storage.security.JWTProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WorkerServiceTest {
    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_PASSWORD = "testPassword123";

    @Mock
    private WorkerRepository workerRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private JWTProperties jwtProperties;

    @InjectMocks
    private WorkerService workerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtProperties.getPrefix()).thenReturn("Bearer");
        when(jwtProperties.getKey()).thenReturn("testkey");
    }

    @Test
    void register_shouldSaveWorkerAndReturnResponse() {
        WorkerRequestDTO dto = new WorkerRequestDTOBuilder().build();
        
        Worker worker = new Worker("Test Name", TEST_USERNAME, "encoded", Role.ADMIN, "Dept");
        
        when(encoder.encode(TEST_PASSWORD)).thenReturn("encoded");
        when(workerRepository.save(any())).thenReturn(worker);
        
        WorkerResponseDTO response = workerService.create(dto);
        
        assertEquals("Test Name", response.name());
        verify(workerRepository).save(any());
    }
    
    @Test
    void authenticate_shouldThrowNotFound_whenUserNotExists() {
        when(workerRepository.findByUsername("user")).thenReturn(Optional.empty());
    
        LoginRequestDTO login = new LoginRequestDTOBuilder()
                .withUsername("user")
                .withPassword("pass")
                .build();
    
        assertThrows(NotFoundException.class, () -> workerService.authenticate(login));
    }


    @Test
    void authenticate_shouldThrowUnauthorized_whenPasswordWrong() {
        Worker worker = new Worker("Name", "user", "encoded", Role.ADMIN, "Dept");
        when(workerRepository.findByUsername("user")).thenReturn(Optional.of(worker));
        when(encoder.matches("pass", "encoded")).thenReturn(false);

        LoginRequestDTO login = new LoginRequestDTO("user", "pass");
        assertThrows(UnauthorizedException.class, () -> workerService.authenticate(login));
    }

    @Test
    void getAllWorkers_shouldReturnList() {
        Worker worker = new Worker("Name", "user", "encoded", Role.ADMIN, "Dept");
        when(workerRepository.findAll()).thenReturn(List.of(worker));
        List<WorkerResponseDTO> result = workerService.getAllWorkers();
        assertEquals(1, result.size());
    }

    @Test
    void findById_shouldReturnWorkerResponseDTO() {
        Worker worker = new Worker("Name", "user", "encoded", Role.ADMIN, "Dept");
        when(workerRepository.findById(1)).thenReturn(Optional.of(worker));
        Optional<WorkerResponseDTO> result = workerService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("user", result.get().username());
    }

    @Test
    void findByUsername_shouldReturnWorkerResponseDTO() {
        Worker worker = new Worker("Name", "user", "encoded", Role.ADMIN, "Dept");
        when(workerRepository.findByUsername("user")).thenReturn(Optional.of(worker));
        Optional<WorkerResponseDTO> result = workerService.findByUsername("user");
        assertTrue(result.isPresent());
        assertEquals("user", result.get().username());
    }

    @Test
    void update_shouldUpdateWorker() {
        Worker worker = new Worker("Old", "olduser", "oldpass", Role.RECEPTIONIST, "OldDept");
        when(workerRepository.findById(1)).thenReturn(Optional.of(worker));
        when(encoder.encode("newpass")).thenReturn("newpass");
        when(workerRepository.save(any())).thenReturn(worker);

        WorkerRequestDTO dto = new WorkerRequestDTO("New", "newuser", "newpass", "NewDept", Role.ADMIN);
        WorkerResponseDTO response = workerService.update(1, dto);

        assertEquals("New", response.name());
        assertEquals("newuser", response.username());
    }

    @Test
    void patch_shouldPatchWorker() {
        Worker worker = new Worker("Old", "olduser", "oldpass", Role.RECEPTIONIST, "OldDept");
        when(workerRepository.findById(1)).thenReturn(Optional.of(worker));
        when(encoder.encode("newpass")).thenReturn("newpass");
        when(workerRepository.save(any())).thenReturn(worker);

        WorkerRequestDTO dto = new WorkerRequestDTO(null, "newuser", "newpass", null, Role.ADMIN);
        WorkerResponseDTO response = workerService.patch(1, dto);

        assertEquals("newuser", response.username());
        assertEquals(Role.ADMIN, response.role());
    }

    @Test
    void delete_shouldDeleteWorker() {
        when(workerRepository.existsById(1)).thenReturn(true);
        workerService.delete(1);
        verify(workerRepository).deleteById(1);
    }

    @Test
    void delete_shouldThrowNotFound_whenWorkerNotExists() {
        when(workerRepository.existsById(1)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> workerService.delete(1));
    }
}
