package _2._millionaire.member;

import _2._millionaire.member.dto.CreateMemberRequest;
import _2._millionaire.member.dto.CreateMemberResponse;
import _2._millionaire.member.dto.MemberListResponse;
import _2._millionaire.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;

    @PostMapping("")
    public ResponseEntity<CreateMemberResponse> createMember(@RequestBody CreateMemberRequest createMemberRequest) {
        return ResponseEntity.ok(memberService.join(createMemberRequest));
    }

    @GetMapping("")
    public ResponseEntity<MemberListResponse> searchAllMembers() {
        return ResponseEntity.ok(memberService.searchAllMember());
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteTask(@PathVariable(name = "memberId") Long memberId){
        memberService.deleteMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).body("멤버 삭제되었습니다.");
    }
}
