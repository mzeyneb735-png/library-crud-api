package az.librarycrudapi.Controller;

import az.librarycrudapi.Dto.AuthRequestDto;
import az.librarycrudapi.Dto.AuthResponseDto;
import az.librarycrudapi.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Qeydiyyat ve Giriş emeliyyatlarinin idare edilmesi")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Yeni istifadeci qeydiyyati")
    public ResponseEntity<String> register(@Valid @RequestBody AuthRequestDto dto) {
        String response = authService.register(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Istifadeci girisi ve JWT token alinmasi")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto dto) {
        AuthResponseDto response = authService.login(dto);
        return ResponseEntity.ok(response);
    }
}
