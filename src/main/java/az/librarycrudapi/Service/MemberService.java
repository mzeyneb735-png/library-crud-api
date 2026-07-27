package az.librarycrudapi.Service;

import az.librarycrudapi.Exception.ResourceNotFoundException;
import az.librarycrudapi.Dto.MemberRequestDto;
import az.librarycrudapi.Dto.MemberResponseDto;
import az.librarycrudapi.Entity.Member;
import az.librarycrudapi.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto create(MemberRequestDto dto) {
        Member member = new Member();
        member.setFullName(dto.getFullName());
        member.setEmail(dto.getEmail());

        Member saved = memberRepository.save(member);
        return toResponseDto(saved);
    }

    public Page<MemberResponseDto> getAll(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(this::toResponseDto);
    }

    public MemberResponseDto getById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Uzv tapilmadi " + id));
        return toResponseDto(member);
    }

    public MemberResponseDto update(Long id, MemberRequestDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Uzv tapilmadi " + id));

        member.setFullName(dto.getFullName());
        member.setEmail(dto.getEmail());

        Member updated = memberRepository.save(member);
        return toResponseDto(updated);
    }

    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Uzv tapilmadi " + id);
        }
        memberRepository.deleteById(id);
    }

    private MemberResponseDto toResponseDto(Member member) {
        MemberResponseDto dto = new MemberResponseDto();
        dto.setId(member.getId());
        dto.setFullName(member.getFullName());
        dto.setEmail(member.getEmail());
        return dto;
    }
}
