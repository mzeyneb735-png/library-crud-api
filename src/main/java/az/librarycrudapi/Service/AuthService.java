package az.librarycrudapi.Service;

import az.librarycrudapi.Dto.AuthRequestDto;
import az.librarycrudapi.Dto.AuthResponseDto;
import az.librarycrudapi.Entity.Role;
import az.librarycrudapi.Entity.User;
import az.librarycrudapi.Exception.ResourceNotFoundException;
import az.librarycrudapi.Exception.UserAlreadyExistsException;
import az.librarycrudapi.Exception.BadCredentialsException;
import az.librarycrudapi.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(AuthRequestDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username artiq movcuddur");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
        return "Istifadeci ugurla qeydiyyatdan kecdi";
    }

    public AuthResponseDto login(AuthRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Istifadeci tapilmadi"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Sifre yanlisdir");
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponseDto(token);
    }
}
