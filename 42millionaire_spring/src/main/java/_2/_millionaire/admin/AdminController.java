package _2._millionaire.admin;

import _2._millionaire.admin.dto.AdminMemberResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminService;

    @GetMapping("")
    public ResponseEntity<AdminMemberResponse> checkAdminAuthController(@RequestParam("groupId") Long groupId,
                                                                        HttpSession httpSession) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.checkAdmin(groupId, httpSession));
    }
}
