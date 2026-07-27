package az.librarycrudapi.Controller;

import az.librarycrudapi.Dto.MemberRequestDto;
import az.librarycrudapi.Dto.MemberResponseDto;
import az.librarycrudapi.Service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Tag(name = "Member Controller", description = "Uzvelrin emeliyyatlarinin idare edilmesi")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "Yeni uzvun yaradilmasi")
    public ResponseEntity<MemberResponseDto> create(@Valid @RequestBody MemberRequestDto dto) {
        MemberResponseDto created = memberService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Butun uzvlerin siyahisi (Pagination ile)")
    public ResponseEntity<Page<MemberResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(memberService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "ID-ye gore uzvun tapilmasi")
    public ResponseEntity<MemberResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Uzv melumatlarinin yenilenmesi")
    public ResponseEntity<MemberResponseDto> update(@PathVariable Long id, @Valid @RequestBody MemberRequestDto dto) {
        return ResponseEntity.ok(memberService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Uzvun silinmesi")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
