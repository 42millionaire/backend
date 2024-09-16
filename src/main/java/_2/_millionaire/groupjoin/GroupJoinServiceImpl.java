package _2._millionaire.groupjoin;

import _2._millionaire.group.GroupRepository;
import _2._millionaire.group.Groups;
import _2._millionaire.group.dto.GroupListResponse;
import _2._millionaire.group.dto.GroupResponse;
import _2._millionaire.groupjoin.dto.GroupJoinListResponse;
import _2._millionaire.groupjoin.dto.GroupJoinRequest;
import _2._millionaire.groupjoin.dto.GroupJoinResponse;
import _2._millionaire.member.Member;
import _2._millionaire.member.MemberRepository;
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
public class GroupJoinServiceImpl implements GroupJoinService {

    private final GroupJoinRepository groupJoinRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    public void joinGroup(GroupJoinRequest groupJoinRequest) {
        Member member = memberRepository.findById(groupJoinRequest.memberId())
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));
        Groups group = groupRepository.findById(groupJoinRequest.groupId())
                .orElseThrow(() -> new IllegalArgumentException("그룹을 찾을 수 없습니다."));

        GroupJoin groupJoin = new GroupJoin(member, group);
        groupJoinRepository.save(groupJoin);
    }

    public GroupJoinListResponse searchAllGroupJoin (Long groupId) {
        final List<GroupJoin> groupJoins = groupJoinRepository.findAll();

        // groupId가 같은 GroupJoin 객체만 필터링
        List<GroupJoinResponse> newGroupJoinResponses = groupJoins.stream()
                .filter(groupJoin -> groupJoin.getGroups().getId().equals(groupId))  // groupId와 일치하는지 필터링
                .map(groupJoin -> GroupJoinResponse.builder()
                        .memberId(groupJoin.getMember().getId())
                        .memberName(groupJoin.getMember().getNickName())
                        .createdTime(groupJoin.getCreatedAt()).build())
                .collect(Collectors.toList());


        return GroupJoinListResponse.builder()
                .groupJoinResponses(newGroupJoinResponses)
                .build();
    }
}
