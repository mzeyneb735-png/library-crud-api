package az.librarycrudapi.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {

    private Long id;
    private String fullName;
    private String email;
}