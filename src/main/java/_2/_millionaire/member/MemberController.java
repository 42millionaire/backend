package _2._millionaire.member;

import _2._millionaire.member.dto.CreateMemberRequest;
import _2._millionaire.member.dto.CreateMemberRespone;
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
    public ResponseEntity<CreateMemberRespone> createMember(@RequestBody CreateMemberRequest createMemberRequest) {
        memberService.join(createMemberRequest);
        return ResponseEntity.ok(new CreateMemberRespone());
    }

    @GetMapping("")
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        final List<MemberResponse> memberResponseList = memberService.findAll();

        return ResponseEntity.ok(memberResponseList);
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteTask(@RequestParam(name = "memberId") Long memberId){
        memberService.deleteMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).body("멤버 삭제되었습니다.");
    }
}
