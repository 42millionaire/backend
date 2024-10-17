package _2._millionaire.appeal;

import _2._millionaire.appeal.dto.ChangeAppealStatus;
import _2._millionaire.appeal.dto.CreateAppealRequest;
import _2._millionaire.appeal.dto.SearchAppealListResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appeal")
public class AppealController {
    private final AppealServiceImpl appealService;

    @PostMapping("")
    public ResponseEntity<String> createAppeal(@RequestBody CreateAppealRequest createAppealRequest){
        appealService.createAppeal(createAppealRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("이의제기 생성되었습니다.");
    }
//    @PostMapping("")
//    public ResponseEntity<String> createAppeal(@RequestBody CreateAppealRequest createAppealRequest, HttpSession session) {
//        appealService.createAppeal(createAppealRequest, session);
//        return ResponseEntity.status(HttpStatus.CREATED).body("이의제기 생성되었습니다.");
//    }

    @GetMapping("/{group_id}")
    public ResponseEntity<SearchAppealListResponse> searchAppeal(@PathVariable(value = "group_id") Long groupId){
        return ResponseEntity.status(HttpStatus.OK).body(appealService.searchAllAppeals(groupId));
    }
//    @GetMapping("/{group_id}")
//    public ResponseEntity<SearchAppealListResponse> searchAppeal(@PathVariable(value = "group_id") Long groupId, HttpSession session) {
//        return ResponseEntity.status(HttpStatus.OK).body(appealService.searchAllAppeals(groupId, session));
//    }

//    @PatchMapping("")
//    public ResponseEntity<String> changeAppealStatus(@RequestBody ChangeAppealStatus changeAppealStatus){
//        appealService.changeAppealStatus(changeAppealStatus);
//        return ResponseEntity.status(HttpStatus.OK).body("상태 변경되었습니다.");
//    }

    @PatchMapping("")
    public ResponseEntity<String> changeAppealStatus(@RequestBody ChangeAppealStatus changeAppealStatus, HttpSession session){
        appealService.changeAppealStatus(changeAppealStatus, session);
        return ResponseEntity.status(HttpStatus.OK).body("상태 변경되었습니다.");
    }
}
