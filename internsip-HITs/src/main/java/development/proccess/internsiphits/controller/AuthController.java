package development.proccess.internsiphits.controller;

import development.proccess.internsiphits.domain.dto.AuthenticationRequest;
import development.proccess.internsiphits.domain.dto.AuthenticationResponse;
import development.proccess.internsiphits.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @Operation(summary = "Логин")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(tokenService.authenticate(request));
    }

    @Operation(summary = "Обновление токена")
    @PostMapping("/refresh-token")
    public AuthenticationResponse refreshToken(
            @RequestParam("refreshToken") String token
    ) {
        return tokenService.refreshToken(token);
    }
}