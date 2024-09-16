package _2._millionaire.groupmember;

import _2._millionaire.group.GroupRepository;
import _2._millionaire.group.Groups;
import _2._millionaire.group.exception.GroupCustomException;
import _2._millionaire.group.exception.GroupErrorCode;
import _2._millionaire.groupmember.dto.SearchGroupMemberListResponse;
import _2._millionaire.groupmember.dto.SearchGroupMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupMemberServiceImpl implements  GroupMemberSerivce{

    private final GroupRepository groupRepository;

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
                        .memberName(groupMember.getMember().getNickName())
                        .grade(groupMember.getRole())
                        .build())  // 빌더 패턴을 이용해 객체를 생성
                .collect(Collectors.toList());  // 변환된 스트림을 리스트로 수집

        // SearchGroupMemberListResponse 객체를 생성하여 반환
        return SearchGroupMemberListResponse.builder()
                .groupMembers(searchGroupMemberResponses)
                .build();
    }

}
