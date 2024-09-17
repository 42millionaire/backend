package _2._millionaire.groupmember;

import _2._millionaire.groupmember.dto.GroupMemberRequest;
import _2._millionaire.groupmember.dto.RollGroupMemberRequest;
import _2._millionaire.groupmember.dto.SearchGroupMemberListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groupmember")
@RequiredArgsConstructor
public class GroupMemberController {
    private final GroupMemberServiceImpl groupMemberService;

    @GetMapping("/{groupId}")
    public ResponseEntity<SearchGroupMemberListResponse> searchAllGroupMembers(@PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok(groupMemberService.searchAllGroupMembers(groupId));
    }

    @PostMapping("")
    public ResponseEntity<String> joinGroupMember(@RequestBody GroupMemberRequest groupMemberRequest) {
        groupMemberService.joinGroupMember(groupMemberRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("그룹멤버가 가입되었습니다.");
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteGroupMember(@RequestBody GroupMemberRequest groupMemberRequest) {
        groupMemberService.deleteGroupMember(groupMemberRequest);
        return ResponseEntity.status(HttpStatus.OK).body("그룹멤버가 삭제되었습니다.");
    }

    @PatchMapping("")
    public ResponseEntity<String> changeRoleGroupMember(@RequestBody RollGroupMemberRequest rollGroupMemberRequest) {
        groupMemberService.changeRoleGroupMember(rollGroupMemberRequest);
        return ResponseEntity.status(HttpStatus.OK).body("그룹멤버의 role이 변경되었습니다.");
    }
}
