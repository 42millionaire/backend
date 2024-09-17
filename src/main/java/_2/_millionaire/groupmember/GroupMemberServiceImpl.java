package _2._millionaire.groupmember;

import _2._millionaire.group.GroupRepository;
import _2._millionaire.group.Groups;
import _2._millionaire.groupjoin.GroupJoin;
import _2._millionaire.groupjoin.GroupJoinRepository;
import _2._millionaire.groupmember.dto.GroupMemberRequest;
import _2._millionaire.groupmember.dto.RollGroupMemberRequest;
import _2._millionaire.group.exception.GroupCustomException;
import _2._millionaire.group.exception.GroupErrorCode;
import _2._millionaire.groupmember.dto.SearchGroupMemberListResponse;
import _2._millionaire.groupmember.dto.SearchGroupMemberResponse;
import _2._millionaire.member.Member;
import _2._millionaire.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Group;
import org.hibernate.metamodel.model.domain.internal.MapMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupMemberServiceImpl implements  GroupMemberSerivce{

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupJoinRepository groupJoinRepository;

    public SearchGroupMemberListResponse searchAllGroupMembers(Long groupId) {
        // groupId로 그룹을 찾고 없으면 예외를 던짐

        Groups groups = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupCustomException(GroupErrorCode.GROUP_NOT_FOUND));

        // 그룹에 속한 멤버들을 가져옴
        List<GroupMember> groupMembers = groups.getGroupMembers();

        // 각 그룹 멤버를 SearchGroupMemberResponse 객체로 변환
        List<SearchGroupMemberResponse> searchGroupMemberResponses = groupMembers.stream()
                .map(groupMember -> SearchGroupMemberResponse.builder()
                        .memberId(groupMember.getMember().getId())
                        .name(groupMember.getMember().getName())
                        .grade(groupMember.getRole())
                        .build())  // 빌더 패턴을 이용해 객체를 생성
                .collect(Collectors.toList());  // 변환된 스트림을 리스트로 수집

        // SearchGroupMemberListResponse 객체를 생성하여 반환
        return SearchGroupMemberListResponse.builder()
                .groupMembers(searchGroupMemberResponses)
                .build();
    }

    @Transactional
    public void joinGroupMember (GroupMemberRequest groupMemberRequest) {
        Member member = memberRepository.findById(groupMemberRequest.memberId())
                .orElseThrow(() -> new IllegalArgumentException("멤버가 존재하지 않습니다."));
        Groups group = groupRepository.findById(groupMemberRequest.groupId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        List<GroupJoin> groupJoins = member.getGroupJoins();
        // memberId와 member.getId()가 같은 GroupJoin 찾기
        GroupJoin groupJoin = groupJoins.stream()
                .filter(gj -> gj.getGroups().getId().equals(group.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹 가입 요청이 없습니다."));

        GroupMember groupMember = GroupMember.builder()
                .member(groupJoin.getMember())
                .groups(groupJoin.getGroups())
                .role("member")
                .build();
        groupJoinRepository.delete(groupJoin);
        groupMemberRepository.save(groupMember);
    }

    @Transactional
    public void deleteGroupMember (GroupMemberRequest groupMemberRequest) {
        Member member = memberRepository.findById(groupMemberRequest.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));
        Groups group = groupRepository.findById(groupMemberRequest.groupId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        List<GroupMember> groupMembers = member.getGroupMembers();

        GroupMember targetGroupMember = groupMembers.stream()
                .filter(groupMember -> groupMember.getGroups().getId().equals(group.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹멤버가 없습니다."));

        groupMemberRepository.delete(targetGroupMember);
    }

    @Transactional
    public void changeRoleGroupMember (RollGroupMemberRequest rollGroupMemberRequest) {
        Groups group = groupRepository.findById(rollGroupMemberRequest.groupId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));
        List<GroupMember> groupMembers = group.getGroupMembers();

        GroupMember targetGroupMember = groupMembers.stream()
                .filter(groupMember -> groupMember.getMember().getId().equals(rollGroupMemberRequest.memberId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버는 그룹에 속해 있지 않습니다."));

        if (Objects.equals(rollGroupMemberRequest.role(), "up"))
            targetGroupMember.setRole("admin");
        else
            targetGroupMember.setRole("member");
    }
}
