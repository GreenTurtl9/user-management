package com.afklm.usermanagement.service;

import com.afklm.usermanagement.exception.UnauthorizedUserException;
import com.afklm.usermanagement.exception.UserNotFoundException;
import com.afklm.usermanagement.model.AppUser;
import com.afklm.usermanagement.model.Country;
import com.afklm.usermanagement.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {


    private final AppUserRepository repository;

    @Override
    public AppUser create(AppUser user) {
        if (user.getAge() > 18 && user.getCountry() == Country.FRANCE) {
            log.info("Saving new user: {}", user.getUsername());
            return repository.save(user);
        } else {
            throw new UnauthorizedUserException("user should have +18 and be resident in France!");
        }
    }

    @Override
    public Optional<AppUser> get(Long userId) {
        log.info("Fetching user by ID: {}", userId);
        return Optional.ofNullable(repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found")));
    }
}
