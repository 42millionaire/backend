package _2._millionaire.groupjoin;

import _2._millionaire.group.dto.GroupListResponse;
import _2._millionaire.groupjoin.dto.GroupJoinListResponse;
import _2._millionaire.groupjoin.dto.GroupJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groupjoinrequest")
@RequiredArgsConstructor
public class GroupJoinController {
    private final GroupJoinServiceImpl groupJoinService;

    @PostMapping("")
    public ResponseEntity<String> joinGroup(@RequestBody GroupJoinRequest groupJoinRequest) {
        groupJoinService.joinGroup(groupJoinRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("그룹가입 신청이 완료되었습니다.");
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupJoinListResponse> searchAllGroupJoin(@PathVariable(name = "groupId") Long groupId) {
        return (ResponseEntity.ok(groupJoinService.searchAllGroupJoin(groupId)));
    }
}
