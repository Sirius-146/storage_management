package com.project.storage.builder;

import com.project.storage.dto.worker.WorkerRequestDTO;
import com.project.storage.model.Role;

public class WorkerRequestDTOBuilder {
    private String name = "Test Name";
    private String username = "testUser";
    private String password = "testPassword123";
    private String cpf = "11122233345";
    private String email = "thisisan@email.com";
    private String department = "TestDept";
    private Role role = Role.ADMIN;

    public WorkerRequestDTOBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public WorkerRequestDTOBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public WorkerRequestDTOBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public WorkerRequestDTOBuilder withDepartment(String department) {
        this.department = department;
        return this;
    }

    public WorkerRequestDTOBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    public WorkerRequestDTO build() {
        return new WorkerRequestDTO(name, username, password, cpf, email, department, role);
    }
}

