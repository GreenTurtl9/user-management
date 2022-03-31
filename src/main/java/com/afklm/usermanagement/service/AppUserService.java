package com.afklm.usermanagement.service;

import com.afklm.usermanagement.model.AppUser;

import java.util.Optional;

public interface AppUserService {

    AppUser create(AppUser user);

    Optional<AppUser> get(Long userId);

}
