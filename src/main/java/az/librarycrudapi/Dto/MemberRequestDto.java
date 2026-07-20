package az.librarycrudapi.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDto {

    @NotBlank(message = "Ad bos ola bilmez")
    private String fullName;

    @NotBlank(message = "Email bos ola bilmez")
    @Email(message = "Email formatı düzgün deyil")
    private String email;
}