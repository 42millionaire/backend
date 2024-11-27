package _2._millionaire.group;

import _2._millionaire.group.dto.*;
import jakarta.servlet.http.HttpSession;
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

//    @PatchMapping("")
//    public ResponseEntity<String> updateGroup(@RequestBody ModifyGroupRequest modifyGroupRequest,
//                                              HttpSession session) {
//        groupService.updateGroup(modifyGroupRequest, session);
//        return ResponseEntity.status(HttpStatus.OK).body("그룹이 수정 되었습니다.");
//    }
    @PatchMapping("")
    public ResponseEntity<String> updateGroup(@RequestBody ModifyGroupRequest modifyGroupRequest) {
        groupService.updateGroup(modifyGroupRequest);
        return ResponseEntity.status(HttpStatus.OK).body("그룹이 수정 되었습니다.");
    }

//    @DeleteMapping("")
//    public ResponseEntity<String> deleteGroup(@RequestBody DeleteGroupRequest deleteGroupRequest,
//                                              HttpSession session) {
//        groupService.deleteGroup(deleteGroupRequest, session);
//        return ResponseEntity.status(HttpStatus.OK).body("그룹이 삭제 되었습니다.");
//    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteGroup(@RequestBody DeleteGroupRequest deleteGroupRequest) {
        groupService.deleteGroup(deleteGroupRequest);
        return ResponseEntity.status(HttpStatus.OK).body("그룹이 삭제 되었습니다.");
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> searchGroup(@PathVariable Long groupId){
        return ResponseEntity.status(HttpStatus.OK).body(groupService.searchGroup(groupId));
    }

//    @PostMapping("/notice")
//    public ResponseEntity<String> registerNotice(@RequestBody RegisterNoticeRequest registerNoticeRequest, HttpSession session) {
//        groupService.registerNotice(registerNoticeRequest, session);
//        return ResponseEntity.status(HttpStatus.CREATED).body("공지가 등록 되었습니다.");
//    }
    @PostMapping("/notice")
    public ResponseEntity<String> registerNotice(@RequestBody RegisterNoticeRequest registerNoticeRequest) {
        groupService.registerNotice(registerNoticeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("공지가 등록 되었습니다.");
    }

    @GetMapping("/notice/{groupId}")
    public ResponseEntity<NoticeResponse> searchGroupNotice(@PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok(groupService.searchGroupNotice(groupId));
    }

    @GetMapping("")
    public ResponseEntity<GroupListResponse> searchAllGroup() {
        return (ResponseEntity.ok(groupService.searchAllGroup()));
    }

    @PostMapping("/penalty")
    public ResponseEntity<String> updateGroupPenalty(@RequestBody UpdatePenaltyRequest updatePenaltyRequest){
        groupService.updateGroupPenalty(updatePenaltyRequest);
        return ResponseEntity.status(HttpStatus.OK).body("벌금 설정되었습니다.");
    }

    @GetMapping("/penalty/{groupId}")
    public ResponseEntity<GroupPenaltyResponse> searchGroupPenalty(@PathVariable("groupId") Long groupId){
        return ResponseEntity.status(HttpStatus.OK).body(groupService.searchGroupPenalty(groupId));
    }
}
