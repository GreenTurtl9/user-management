package com.afklm.usermanagement.service;

import com.afklm.usermanagement.exception.UnauthorizedUserException;
import com.afklm.usermanagement.exception.UserNotFoundException;
import com.afklm.usermanagement.model.AppUser;
import com.afklm.usermanagement.model.Country;
import com.afklm.usermanagement.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserServiceImplTest {

    @Mock
    private AppUserRepository userRepository;

    @InjectMocks
    private AppUserServiceImpl userService;


    @Test
    void canCreateUser() {
        //Given
        AppUser user = new AppUser("Paul",
                LocalDate.of(1985, 04, 03), Country.FRANCE);
        //When
        userService.create(user);
        //Then
        ArgumentCaptor<AppUser> userArgumentCaptor =
                ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        AppUser capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
        assertThatNoException();
    }

    @Test
    void shouldThrowExceptionWhenUserUnderAge() {
        //Given
        AppUser user = new AppUser("Paul",
                LocalDate.of(2008, 04, 03), Country.FRANCE);
        //Then
        UnauthorizedUserException exception = assertThrows(UnauthorizedUserException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals("user should have +18 and be resident in France!", exception.getMessage());
        Assertions.assertTrue(exception.getMessage().contains("+18"));
        Assertions.assertFalse(exception.getMessage().contains("+17"));
        Assertions.assertTrue(user.getAge() < 18);
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotResidentInFrance() {
        //Given
        AppUser user = new AppUser("Pablo",
                LocalDate.of(2001, 04, 03), Country.MEXICO);
        //Then
        UnauthorizedUserException exception = assertThrows(UnauthorizedUserException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals("user should have +18 and be resident in France!", exception.getMessage());
        Assertions.assertTrue(exception.getMessage().contains("France"));
        Assertions.assertFalse(user.getCountry().equals(Country.FRANCE));
    }

    @Test
    void shouldThrowUnauthorizedUserExceptionWhenUserIsNotResidentInFrance() {
        //Given
        AppUser user = new AppUser("Mano",
                LocalDate.of(1989, 04, 03), Country.TAJIKISTAN);
        //Then
        assertThatThrownBy(() -> userService.create(user))
                .isInstanceOf(UnauthorizedUserException.class)
                .hasMessageContaining("resident in France");
    }

    @Test
    void shouldReturnExistingUser() {
        AppUser user = new AppUser("Jaime",
                LocalDate.of(1988, 11, 05), Country.ALBANIA);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        assertThat(userService.get(13L).equals(user));
        assertThatNoException();
    }

    @Test
    void shouldThrowExceptionWhenNoExistingUser() {
        Long userId = 5L;
        assertThatThrownBy(() -> userService.get(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("user not found");
    }
}