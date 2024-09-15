package _2._millionaire.member;

import _2._millionaire.member.dto.CreateMemberRequest;
import _2._millionaire.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    void join(CreateMemberRequest createMemberRequest) {
        Member member = new Member(createMemberRequest.getNickName(), createMemberRequest.getEmail());
        memberRepository.save(member);
    }

    public List<MemberResponse> findAll() {
        final List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(member -> MemberResponse.builder()
                .memberName(member.getNickName())
                .memberId(member.getId())
                .build())
                .toList();
    }

    @Transactional
    public void deleteMember(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow();

        memberRepository.delete(member);
    }
}
