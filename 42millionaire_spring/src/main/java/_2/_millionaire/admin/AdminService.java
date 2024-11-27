package _2._millionaire.admin;

import _2._millionaire.admin.dto.AdminMemberResponse;
import _2._millionaire.admin.exception.AdminCustomException;
import _2._millionaire.admin.exception.AdminErrorCode;
import _2._millionaire.member.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements AdminServiceImpl{
    @Override
    public AdminMemberResponse checkAdmin(Long groupId, HttpSession httpSession) {
        Member loginMember = (Member) httpSession.getAttribute("user");

        if (loginMember == null)
            throw new AdminCustomException(AdminErrorCode.NOT_ADMIN);

        // 로그인한 사용자의 그룹 멤버십을 확인
        boolean isAdmin = loginMember.getGroupMembers().stream()
                .anyMatch(groupMember -> groupMember.getRole().equals("admin")
                        && groupMember.getGroups().getId().equals(groupId));

        if (isAdmin) {
            // AdminMemberResponse 생성 및 반환
            return new AdminMemberResponse(loginMember.getId(), loginMember.getName(), true);
        } else {
            throw new AdminCustomException(AdminErrorCode.NOT_ADMIN);
        }
    }
}
