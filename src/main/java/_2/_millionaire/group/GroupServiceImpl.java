package _2._millionaire.group;

import _2._millionaire.group.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupServiceImpl implements  GroupService{
    private final GroupRepository groupRepository;

    public void createGroup(CreateGroupRequest createGroupRequest) {
        Groups group = Groups.builder()
                .groupName(createGroupRequest.groupName())
                .groupImage(createGroupRequest.groupImage())
                .build();

        groupRepository.save(group);
    }

    public void updateGroup(ModifyGroupRequest modifyGroupRequest) {
        Groups group = groupRepository.findById(modifyGroupRequest.groupId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        group.setGroupName(modifyGroupRequest.groupName());
        groupRepository.save(group);
    }

    public void deleteGroup(DeleteGroupRequest deleteGroupRequest) {
        Groups group = groupRepository.findById(deleteGroupRequest.groupId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));
        groupRepository.delete(group);
    }

    public void registerNotice(RegisterNoticeRequest registerNoticeRequest) {
        Groups group = groupRepository.findById(registerNoticeRequest.groupId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));
        group.setNotice(registerNoticeRequest.notice());
        groupRepository.save(group);
    }

    public NoticeResponse searchGroupNotice(Long groupId) {
        Groups group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));
        return new NoticeResponse(group.getNotice());
    }
}
