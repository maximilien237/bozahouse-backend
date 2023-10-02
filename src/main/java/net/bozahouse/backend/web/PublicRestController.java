package net.bozahouse.backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.dtos.AppUserDTO;
import net.bozahouse.backend.payload.request.LoginRequest;
import net.bozahouse.backend.payload.response.JwtResponse;
import net.bozahouse.backend.services.AuthService;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/api/auth/v1/")
public class PublicRestController {

    private AuthService authService;


    @PostMapping("signIn")
    public JwtResponse authenticate(@RequestBody LoginRequest request) {
        return authService.authenticate(request);
    }

    //test ok
    @PostMapping("signUp")
    public AppUserDTO registration(@RequestBody AppUserDTO dto)  {
        return authService.register(dto);
    }

    @GetMapping("checkEmail/{email}")
    public String checkEmail(@PathVariable String email)  {
        return authService.checkEmail(email);
    }
}
