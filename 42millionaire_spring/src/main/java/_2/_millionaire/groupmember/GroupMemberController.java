package _2._millionaire.groupmember;

import _2._millionaire.groupmember.dto.*;
import _2._millionaire.member.Member;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/check/{groupId}")
    public ResponseEntity<Boolean> checkGroupMember(@PathVariable("groupId") Long groupId, HttpSession session) {
        return ResponseEntity.ok(groupMemberService.checkGroupMember(groupId, (Member) session.getAttribute("user")));

    }

    @PostMapping("")
    public ResponseEntity<String> joinGroupMember(@RequestBody GroupMemberRequest groupMemberRequest,
                                                  HttpSession session) {
        groupMemberService.joinGroupMember(groupMemberRequest, session);
        return ResponseEntity.status(HttpStatus.CREATED).body("그룹멤버가 가입되었습니다.");
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteGroupMember(@RequestBody GroupMemberRequest groupMemberRequest,
                                                    HttpSession session) {
        groupMemberService.deleteGroupMember(groupMemberRequest, session);
        return ResponseEntity.status(HttpStatus.OK).body("그룹멤버가 삭제되었습니다.");
    }

    @PatchMapping("")
    public ResponseEntity<String> changeRoleGroupMember(@RequestBody RollGroupMemberRequest rollGroupMemberRequest,
                                                        HttpSession session) {
        groupMemberService.changeRoleGroupMember(rollGroupMemberRequest, session);
        return ResponseEntity.status(HttpStatus.OK).body("그룹멤버의 role이 변경되었습니다.");
    }

    @GetMapping("/penalty")
    public ResponseEntity<GroupMemberPenaltyListResponse> findGroupMemberPenalty(@RequestParam final Long groupId,
                                                                                 @RequestParam final Integer year,
                                                                                 @RequestParam final Integer month,
                                                                                 HttpSession session){

        return ResponseEntity.status(HttpStatus.OK).body(groupMemberService.calculatePenaltyByGroupAndDate(groupId, year, month, session));
     }
}
