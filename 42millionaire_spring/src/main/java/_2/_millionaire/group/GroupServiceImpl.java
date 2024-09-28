package _2._millionaire.group;

import _2._millionaire.group.dto.*;
import _2._millionaire.member.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupServiceImpl implements  GroupService{
    private final GroupRepository groupRepository;

    @Transactional
    public void createGroup(CreateGroupRequest createGroupRequest) {
        Groups group = Groups.builder()
                .groupName(createGroupRequest.groupName())
                .groupImage(createGroupRequest.groupImage())
                .build();

        groupRepository.save(group);
    }

//    @Transactional
//    public void updateGroup(ModifyGroupRequest modifyGroupRequest, HttpSession session) {
//        Groups group = groupRepository.findById(modifyGroupRequest.groupId())
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));
//
//        Member loginMember = (Member) session.getAttribute("loginMember");
//
//        boolean isAdmin = group.getGroupMembers().stream()
//                .anyMatch(groupMember -> groupMember.getRole().equals("admin") && groupMember.getMember().equals(loginMember));
//
//        if (!isAdmin) {
//            throw new IllegalStateException("권한이 없습니다. 관리자만 그룹에 멤버를 추가할 수 있습니다.");
//        }
//
//        group.setGroupName(modifyGroupRequest.groupName());
//    }
    @Transactional
    public void updateGroup(ModifyGroupRequest modifyGroupRequest) {
        Groups group = groupRepository.findById(modifyGroupRequest.groupId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));
        group.setGroupName(modifyGroupRequest.groupName());
    }


//    @Transactional
//    public void deleteGroup(DeleteGroupRequest deleteGroupRequest, HttpSession session) {
//        Groups group = groupRepository.findById(deleteGroupRequest.groupId()).
//                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));
//
//        Member loginMember = (Member) session.getAttribute("loginMember");
//
//        boolean isAdmin = group.getGroupMembers().stream()
//                .anyMatch(groupMember -> groupMember.getRole().equals("admin") && groupMember.getMember().equals(loginMember));
//
//        if (!isAdmin) {
//            throw new IllegalStateException("권한이 없습니다. 관리자만 그룹에 멤버를 추가할 수 있습니다.");
//        }
//
//        groupRepository.delete(group);
//    }
    @Transactional
    public void deleteGroup(DeleteGroupRequest deleteGroupRequest) {
        Groups group = groupRepository.findById(deleteGroupRequest.groupId()).
                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        groupRepository.delete(group);
    }

//    @Transactional
//    public void registerNotice(RegisterNoticeRequest registerNoticeRequest, HttpSession session) {
//        Groups group = groupRepository.findById(registerNoticeRequest.groupId()).
//                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));
//
//        Member loginMember = (Member) session.getAttribute("loginMember");
//
//        boolean isAdmin = group.getGroupMembers().stream()
//                .anyMatch(groupMember -> groupMember.getRole().equals("admin") && groupMember.getMember().equals(loginMember));
//
//        if (!isAdmin) {
//            throw new IllegalStateException("권한이 없습니다. 관리자만 그룹에 멤버를 추가할 수 있습니다.");
//        }
//
//        group.setNotice(registerNoticeRequest.notice());
//    }
    @Transactional
    public void registerNotice(RegisterNoticeRequest registerNoticeRequest) {
        Groups group = groupRepository.findById(registerNoticeRequest.groupId()).
                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        group.setNotice(registerNoticeRequest.notice());
    }

    public NoticeResponse searchGroupNotice(Long groupId) {
        Groups group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));
        return new NoticeResponse(group.getNotice());
    }

    public GroupListResponse searchAllGroup() {
        final List<Groups> groups = groupRepository.findAll();

        List<GroupResponse> groupResponses = groups.stream().map(group -> GroupResponse.builder()
                .groupId(group.getId())
                .groupName(group.getGroupName()).build()).collect(Collectors.toList());

        return GroupListResponse.builder().groupResponses(groupResponses).build();
    }
}
