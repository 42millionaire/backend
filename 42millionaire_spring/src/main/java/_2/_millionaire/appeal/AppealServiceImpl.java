package _2._millionaire.appeal;

import _2._millionaire.appeal.dto.ChangeAppealStatus;
import _2._millionaire.appeal.dto.CreateAppealRequest;
import _2._millionaire.appeal.dto.SearchAppealListResponse;
import _2._millionaire.appeal.dto.SearchAppealResponse;
import _2._millionaire.appeal.exception.AppealCustomException;
import _2._millionaire.appeal.exception.AppealErrorCode;
import _2._millionaire.group.GroupRepository;
import _2._millionaire.group.Groups;
import _2._millionaire.group.exception.GroupCustomException;
import _2._millionaire.group.exception.GroupErrorCode;
import _2._millionaire.groupmember.GroupMember;
import _2._millionaire.groupmember.GroupMemberRepository;
import _2._millionaire.groupmember.exception.GroupMemberCustomException;
import _2._millionaire.groupmember.exception.GroupMemberErrorCode;
import _2._millionaire.member.Member;
import _2._millionaire.task.Task;
import _2._millionaire.task.TaskRepository;
import _2._millionaire.task.exception.TaskCustomException;
import _2._millionaire.task.exception.TaskErrorCode;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService{
    
    private final AppealRepository appealRepository;
    private final TaskRepository taskRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public void createAppeal(CreateAppealRequest createAppealRequest) {
        Task task = taskRepository.findById(createAppealRequest.taskId())
                .orElseThrow(() -> new TaskCustomException(TaskErrorCode.TASK_NOT_FOUND));
        Appeal appeal = new Appeal(createAppealRequest.content(), "pend", task);
        appealRepository.save(appeal);
    }
//    @Transactional
//    public void createAppeal(CreateAppealRequest createAppealRequest, HttpSession session) {
//        Member taskOwnerMember = groupMemberRepository.findByTasks_Id(createAppealRequest.taskId())
//                .orElseThrow(() -> new RuntimeException("해당 Task를 가진 Member가 없습니다."))
//                .getMember();
//        if (!taskOwnerMember.equals(session.getAttribute("loggedUser"))) {
//            throw new IllegalStateException("현재 사용자는 Task의 소유자가 아닙니다.");
//        }
//        Task task = taskRepository.findById(createAppealRequest.taskId())
//                .orElseThrow(() -> new TaskCustomException(TaskErrorCode.TASK_NOT_FOUND));
//        Appeal appeal = new Appeal(createAppealRequest.content(), "pend", task);
//        appealRepository.save(appeal);
//    }

    public SearchAppealListResponse searchAllAppeals(Long groupId) {
        Groups groups = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupCustomException(GroupErrorCode.GROUP_NOT_FOUND));
        List<Task> tasks = groups.getGroupMembers().stream()
                .flatMap(groupMember -> groupMember.getTasks().stream()) // 각 그룹 멤버의 Task 스트림으로 변환
                .toList(); // 하나의 리스트로 수집

        // Task에서 Appeal의 content를 모두 가져와 연결
        List<SearchAppealResponse> searchAppealResponses = tasks.stream()
                .flatMap(task -> task.getAppeals().stream() // 각 Task의 Appeal 스트림으로 변환
                        .filter(appeal -> "pend".equals(appeal.getStatus()))
                        .map(appeal -> SearchAppealResponse.builder()
                                .taskId(task.getId())
                                .appealId(appeal.getId())
                                .memberId(task.getGroupMember().getMember().getId())
                                .memberName(task.getGroupMember().getMember().getName())
                                .content(appeal.getContent()) // Appeal의 content 설정
                                .createdTime(appeal.getCreatedAt())
                                .build()))
                .toList();
        return new SearchAppealListResponse(searchAppealResponses);
    }

//    public SearchAppealListResponse searchAllAppeals(Long groupId, HttpSession session) {
//        Member loginMember = (Member) session.getAttribute("loginMember");
//
//        Groups groups = groupRepository.findById(groupId)
//                .orElseThrow(() -> new GroupCustomException(GroupErrorCode.GROUP_NOT_FOUND));
//
//        GroupMember loginGroupMember = groups.getGroupMembers().stream()
//                .filter(groupMember -> groupMember.getMember().getId().equals(loginMember.getId()))
//                .findFirst()
//                .orElseThrow(() -> new GroupCustomException(GroupMemberErrorCode.MEMBER_NOT_IN_GROUP));
//
//        if (!loginGroupMember.getRole().equals("admin")) {
//            throw new GroupCustomException(GroupErrorCode.NOT_ADMIN_OF_GROUP);
//        }
//
//        List<Task> tasks = groups.getGroupMembers().stream()
//                .flatMap(groupMember -> groupMember.getTasks().stream()) // 각 그룹 멤버의 Task 스트림으로 변환
//                .toList(); // 하나의 리스트로 수집
//
//        // Task에서 Appeal의 content를 모두 가져와 연결
//        List<SearchAppealResponse> searchAppealResponses = tasks.stream()
//                .flatMap(task -> task.getAppeals().stream() // 각 Task의 Appeal 스트림으로 변환
//                        .filter(appeal -> "pend".equals(appeal.getStatus()))
//                        .map(appeal -> SearchAppealResponse.builder()
//                                .taskId(task.getId())
//                                .appealId(appeal.getId())
//                                .memberId(task.getGroupMember().getMember().getId())
//                                .memberName(task.getGroupMember().getMember().getName())
//                                .content(appeal.getContent()) // Appeal의 content 설정
//                                .createdTime(appeal.getCreatedAt())
//                                .build()))
//                .toList();
//        return new SearchAppealListResponse(searchAppealResponses);
//    }

//    @Transactional
//    public void changeAppealStatus(ChangeAppealStatus changeAppealStatus) {
//        Appeal appeal = appealRepository.findById(changeAppealStatus.appealId())
//                .orElseThrow(() -> new AppealCustomException(AppealErrorCode.APPEAL_NOT_FOUND));
//        appeal.setStatus("processed");
//        appeal.getTask().setStatus(changeAppealStatus.status());
//    }

    @Transactional
    public void changeAppealStatus(ChangeAppealStatus changeAppealStatus, HttpSession session) {
        Appeal appeal = appealRepository.findById(changeAppealStatus.appealId())
                .orElseThrow(() -> new AppealCustomException(AppealErrorCode.APPEAL_NOT_FOUND));
        appeal.setStatus("processed");
        appeal.getTask().setStatus(changeAppealStatus.status());
    }

}
