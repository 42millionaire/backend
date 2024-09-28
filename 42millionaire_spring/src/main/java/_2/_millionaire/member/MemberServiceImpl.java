package _2._millionaire.member;

import _2._millionaire.member.exception.MemberCustomException;
import _2._millionaire.member.exception.MemberErrorCode;
import _2._millionaire.member.dto.CreateMemberRequest;
import _2._millionaire.member.dto.CreateMemberResponse;
import _2._millionaire.member.dto.MemberListResponse;
import _2._millionaire.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Transactional
    public CreateMemberResponse join(CreateMemberRequest createMemberRequest) {
        Member member = new Member(createMemberRequest.name(), createMemberRequest.email());
        memberRepository.save(member);
        return new CreateMemberResponse(member.getId());
    }

    public MemberListResponse searchAllMember() {
        final List<Member> members = memberRepository.findAll();

        List<MemberResponse> memberResponses = members.stream()
                .map(member -> MemberResponse.builder()
                                .memberId(member.getId())
                                .name(member.getName())
                                .build())
                .collect(Collectors.toList());

        return MemberListResponse.builder()
                .members(memberResponses)
                .build();
    }

    @Transactional
    public void deleteMember(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberCustomException(MemberErrorCode.MEMBER_NOT_FOUND));
        memberRepository.delete(member);
    }
}
