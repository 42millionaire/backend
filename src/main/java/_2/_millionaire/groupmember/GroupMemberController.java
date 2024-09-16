package _2._millionaire.groupmember;

import _2._millionaire.groupmember.dto.SearchGroupMemberListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groupmember")
@RequiredArgsConstructor
public class GroupMemberController {
    private final GroupMemberServiceImpl groupMemberService;

    @GetMapping("")
    public ResponseEntity<SearchGroupMemberListResponse> searchAllGroupMembers(@PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok(groupMemberService.searchAllGroupMembers(groupId));
    }
}
