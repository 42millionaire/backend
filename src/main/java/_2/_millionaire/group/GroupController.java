package _2._millionaire.group;

import _2._millionaire.group.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupServiceImpl groupService;

    @PostMapping("")
    public ResponseEntity<String> createGroup(@RequestBody CreateGroupRequest createGroupRequest) {
        groupService.createGroup(createGroupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("그룹이 생성 되었습니다.");
    }

    @PatchMapping("")
    public ResponseEntity<String> updateGroup(@RequestBody ModifyGroupRequest modifyGroupRequest) {
        groupService.updateGroup(modifyGroupRequest);
        return ResponseEntity.status(HttpStatus.OK).body("그룹이 수정 되었습니다.");
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteGroup(@RequestBody DeleteGroupRequest deleteGroupRequest) {
        groupService.deleteGroup(deleteGroupRequest);
        return ResponseEntity.status(HttpStatus.OK).body("그룹이 삭제 되었습니다.");
    }

    @PostMapping("/notice")
    public ResponseEntity<String> registerNotice(@RequestBody RegisterNoticeRequest registerNoticeRequest) {
        groupService.registerNotice(registerNoticeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("공지가 등록 되었습니다.");
    }

    @GetMapping("/notice")
    public ResponseEntity<NoticeResponse> searchGroupNotice(@RequestParam("groupId") Long groupId) {
        NoticeResponse noticeResponse = groupService.searchGroupNotice(groupId);
        return ResponseEntity.ok(noticeResponse);
    }

}
