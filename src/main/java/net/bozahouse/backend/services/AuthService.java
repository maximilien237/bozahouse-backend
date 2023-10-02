package net.bozahouse.backend.services;

import net.bozahouse.backend.dtos.AppUserDTO;
import net.bozahouse.backend.payload.request.LoginRequest;
import net.bozahouse.backend.payload.response.JwtResponse;

public interface AuthService {
    JwtResponse authenticate(LoginRequest request);

    AppUserDTO register(AppUserDTO dto);

    String checkEmail(String email);
}
