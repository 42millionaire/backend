package _2._millionaire.groupmember;

import _2._millionaire.groupmember.dto.SearchGroupMemberListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groupmember")
@RequiredArgsConstructor
public class GroupMemberController {
    private final GroupMemberServiceImpl groupMemberService;

    @GetMapping("")
    public ResponseEntity<SearchGroupMemberListResponse> searchAllGroupMembers(@RequestParam("groupId") Long groupId) {
        SearchGroupMemberListResponse searchGroupMemberListResponse = groupMemberService.searchAllGroupMembers(groupId);
        return ResponseEntity.ok(searchGroupMemberListResponse);
    }
}
