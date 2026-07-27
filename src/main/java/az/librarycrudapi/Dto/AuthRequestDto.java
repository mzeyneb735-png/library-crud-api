package az.librarycrudapi.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {

    @NotBlank(message = "Username bos ola bilmez")
    @Size(min = 3, max = 50, message = "Username minimum 3, maksimum 50 simvol olmalidir")
    private String username;

    @NotBlank(message = "Password bos ola bilmez")
    @Size(min = 4, message = "Password minimum 4 simvol olmalidir")
    private String password;
}
