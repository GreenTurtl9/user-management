package com.afklm.usermanagement.service;

import com.afklm.usermanagement.repository.AppUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AppUserServiceImplTest {

    @Mock
    private AppUserRepository userRepository;

    private AutoCloseable autoCloseable;

    private AppUserServiceImpl userService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userService = new AppUserServiceImpl(userRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void create() {
    }

    @Test
    void get() {
    }
}