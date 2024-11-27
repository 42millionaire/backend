package _2._millionaire.admin;

import _2._millionaire.admin.dto.AdminMemberResponse;
import jakarta.servlet.http.HttpSession;

public interface AdminServiceImpl {
    AdminMemberResponse checkAdmin(Long groupId, HttpSession httpSession);
}
