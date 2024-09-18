package _2._millionaire.groupjoin;

import _2._millionaire.group.GroupRepository;
import _2._millionaire.group.Groups;
import _2._millionaire.groupjoin.dto.GroupJoinListResponse;
import _2._millionaire.groupjoin.dto.GroupJoinRequest;
import _2._millionaire.groupjoin.dto.GroupJoinResponse;
import _2._millionaire.member.Member;
import _2._millionaire.member.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupJoinServiceImpl implements GroupJoinService {

    private final GroupJoinRepository groupJoinRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public void joinGroup(GroupJoinRequest groupJoinRequest) {
        Member member = memberRepository.findById(groupJoinRequest.memberId())
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));
        Groups group = groupRepository.findById(groupJoinRequest.groupId())
                .orElseThrow(() -> new IllegalArgumentException("그룹을 찾을 수 없습니다."));

        GroupJoin groupJoin = new GroupJoin(member, group);
        groupJoinRepository.save(groupJoin);
    }

    public GroupJoinListResponse searchAllGroupJoin (Long groupId, HttpSession session) {
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        Member loginMember = (Member) session.getAttribute("loginMember");

        boolean isAdmin = group.getGroupMembers().stream()
                .anyMatch(groupMember -> groupMember.getRole().equals("admin") && groupMember.getMember().equals(loginMember));

        if (!isAdmin) {
            throw new IllegalStateException("권한이 없습니다. 관리자만 그룹에 멤버를 추가할 수 있습니다.");
        }

        final List<GroupJoin> groupJoins = groupJoinRepository.findAll();

        // groupId가 같은 GroupJoin 객체만 필터링
        List<GroupJoinResponse> newGroupJoinResponses = groupJoins.stream()
                .filter(groupJoin -> groupJoin.getGroups().getId().equals(groupId))  // groupId와 일치하는지 필터링
                .map(groupJoin -> GroupJoinResponse.builder()
                        .memberId(groupJoin.getMember().getId())
                        .name(groupJoin.getMember().getName())
                        .createdTime(groupJoin.getCreatedAt()).build())
                .collect(Collectors.toList());

        return GroupJoinListResponse.builder()
                .groupJoinResponses(newGroupJoinResponses)
                .build();
    }
}
