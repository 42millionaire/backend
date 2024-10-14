package _2._millionaire.task;

import _2._millionaire.group.GroupRepository;
import _2._millionaire.group.Groups;
import _2._millionaire.group.exception.GroupCustomException;
import _2._millionaire.group.exception.GroupErrorCode;
import _2._millionaire.groupmember.GroupMember;
import _2._millionaire.groupmember.GroupMemberRepository;
import _2._millionaire.groupmember.GroupMemberServiceImpl;
import _2._millionaire.groupmember.exception.GroupMemberCustomException;
import _2._millionaire.groupmember.exception.GroupMemberErrorCode;
import _2._millionaire.member.Member;
import _2._millionaire.member.MemberRepository;
import _2._millionaire.member.exception.MemberCustomException;
import _2._millionaire.member.exception.MemberErrorCode;
import _2._millionaire.task.dto.*;
import _2._millionaire.task.exception.TaskCustomException;
import _2._millionaire.task.exception.TaskErrorCode;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public void createTask(CreateTaskRequest createTaskRequest) {
        final Member member = memberRepository.findById(createTaskRequest.memberId())
                .orElseThrow(() -> new TaskCustomException(TaskErrorCode.MEMBER_NOT_FOUND));
        final Groups groups = groupRepository.findById(createTaskRequest.groupId())
                .orElseThrow(() -> new TaskCustomException(TaskErrorCode.GROUP_NOT_FOUND));

        GroupMember groupMember = member.getGroupMembers().stream()
                .filter(gm -> gm.getGroups().equals(groups))
                .findFirst()
                .orElseThrow(() -> new TaskCustomException(TaskErrorCode.MEMBER_NOT_IN_GROUP));

        Task task = Task.builder()
                .type(createTaskRequest.type())
                .groupMember(groupMember)
                .dueDate(createTaskRequest.dueDate())
                .content(createTaskRequest.content())
                .build();
        taskRepository.save(task);
    }
//    @Transactional
//    public void createTask(CreateTaskRequest createTaskRequest, HttpSession session) {
//        Groups group = groupRepository.findById(createTaskRequest.groupId()).
//                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));
//
//        Member loginMember = (Member) session.getAttribute("loginMember");
//
//        boolean isGroupMember = group.getGroupMembers().stream()
//                .anyMatch(groupMember -> groupMember.getMember() == loginMember);
//
//        if (!isGroupMember) {
//            throw new IllegalStateException("해당그룹의 멤버가 아닙니다.");
//        }
//
//        final Member member = memberRepository.findById(createTaskRequest.memberId())
//                .orElseThrow(() -> new MemberCustomException(MemberErrorCode.MEMBER_NOT_FOUND));
//        final Groups groups = groupRepository.findById(createTaskRequest.groupId())
//                .orElseThrow(() -> new GroupCustomException(GroupErrorCode.GROUP_NOT_FOUND));
//
//        GroupMember groupMember = member.getGroupMembers().stream()
//                .filter(gm -> gm.getGroups().equals(groups))
//                .findFirst()
//                .orElseThrow(() -> new GroupMemberCustomException(GroupMemberErrorCode.MEMBER_NOT_IN_GROUP));
//
//        Task task = Task.builder()
//                .type(createTaskRequest.type())
//                .groupMember(groupMember)
//                .dueDate(createTaskRequest.dueDate())
//                .content(createTaskRequest.content())
//                .build();
//        taskRepository.save(task);
//    }

    @Transactional
    public void updateTask(UpdateTaskRequest updateTaskRequest) {
        final Task task = taskRepository.findById(updateTaskRequest.taskId())
                .orElseThrow(() -> new TaskCustomException(TaskErrorCode.TASK_NOT_FOUND));
        task.updateContent(updateTaskRequest.content());
    }
//    @Transactional
//    public void updateTask(UpdateTaskRequest updateTaskRequest, HttpSession session) {
//        final Task task = taskRepository.findById(updateTaskRequest.taskId())
//                .orElseThrow(() -> new TaskCustomException(TaskErrorCode.TASK_NOT_FOUND));
//
//        Member loginMember = (Member) session.getAttribute("loginMember");
//        boolean isTaskOwner = task.getGroupMember().getMember().getId().equals(loginMember.getId());
//        if (!isTaskOwner) {
//            throw new IllegalStateException("본인의 task가 아닙니다.");
//        }
//
//        task.updateContent(updateTaskRequest.content());
//    }

    @Transactional
    public void deleteTask(final Long taskId) {
        final Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskCustomException(TaskErrorCode.TASK_NOT_FOUND));
        //태스크의 주인이 로그인 한 주인과 같은지 확인 후 삭제해야함 추후 추가할 예정
        taskRepository.delete(task);
    }
//    @Transactional
//    public void deleteTask(final Long taskId, final HttpSession session) {
//        final Task task = taskRepository.findById(taskId)
//                .orElseThrow(() -> new TaskCustomException(TaskErrorCode.TASK_NOT_FOUND));
//        //태스크의 주인이 로그인 한 주인과 같은지 확인 후 삭제해야함 추후 추가할 예정
//        Member loginMember = (Member) session.getAttribute("loginMember");
//        boolean isTaskOwner = task.getGroupMember().getMember().getId().equals(loginMember.getId());
//        if (!isTaskOwner) {
//            throw new IllegalStateException("본인의 task가 아닙니다.");
//        }
//
//        taskRepository.delete(task);
//    }

    @Transactional(readOnly = true)
    public SearchTaskResponse searchTask(final Long taskId) {
        final Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskCustomException(TaskErrorCode.TASK_NOT_FOUND));
        return SearchTaskResponse.builder()
                .taskId(task.getId())
                .memberName(task.getGroupMember().getMember().getName())
                .content(task.getContent())
                .dueDate(task.getDueDate())
                .createdTime(task.getCreatedAt())
                .updatedTime(task.getUpdatedAt())
                .status(task.getStatus())
                .build();
    }

//    @Transactional(readOnly = true)
//    public SearchTaskResponse searchTask(final Long taskId, HttpSession session) {
//        final Task task = taskRepository.findById(taskId)
//                .orElseThrow(() -> new TaskCustomException(TaskErrorCode.TASK_NOT_FOUND));
//
//
//        Member loginMember = (Member) session.getAttribute("loginMember");
//        boolean isTaskOwner = task.getGroupMember().getMember().getId().equals(loginMember.getId());
//        if (!isTaskOwner) {
//            throw new IllegalStateException("본인의 task가 아닙니다.");
//        }
//
//
//        return SearchTaskResponse.builder()
//                .taskId(task.getId())
//                .content(task.getContent())
//                .dueDate(task.getDueDate())
//                .createdTime(task.getCreatedAt())
//                .updatedTime(task.getUpdatedAt())
//                .status(task.getStatus())
//                .build();
//    }

    @Transactional(readOnly = true)
    public SearchTaskListResponse searchTaskByMemberAndMonth(final Long groupId,
                                                             final Long memberId,
                                                             final Integer year,
                                                             final Integer month) {
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupCustomException(GroupErrorCode.GROUP_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberCustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        GroupMember groupMember = member.getGroupMembers().stream()
                .filter(gm -> gm.getGroups().equals(group))
                .findFirst()
                .orElseThrow(() -> new GroupMemberCustomException(GroupMemberErrorCode.MEMBER_NOT_IN_GROUP));

        YearMonth yearMonth = YearMonth.of(year, month);

        List<Task> tasksInMonth = groupMember.getTasks().stream()
                .filter(task -> {
                    // Task의 dueDate 필드가 주어진 연도 및 월과 일치하는지 확인
                    YearMonth taskDueDateYearMonth = YearMonth.from(task.getDueDate());
                    if (task.getType().equals("daily"))
                        return taskDueDateYearMonth.equals(yearMonth);
                    YearMonth taskCreateDateYearMonth = YearMonth.from(task.getCreatedAt());
                    return taskDueDateYearMonth.equals(yearMonth) || taskCreateDateYearMonth.equals(yearMonth);
                })
                .toList();

        // Task 리스트를 SearchTaskResponse로 변환
        List<SearchTaskResponse> taskResponses = tasksInMonth.stream()
                .map(task -> SearchTaskResponse.builder()
                        .taskId(task.getId())
                        .content(task.getContent())
                        .type(task.getType())
                        .memberName(task.getGroupMember().getMember().getName())
                        .dueDate(task.getDueDate())
                        .createdTime(task.getCreatedAt())
                        .updatedTime(task.getUpdatedAt())
                        .status(task.getStatus())
                        .build())
                .collect(Collectors.toList());

        // SearchTaskListResponse로 반환
        return SearchTaskListResponse.builder()
                .tasks(taskResponses)
                .build();
    }
//    @Transactional(readOnly = true)
//    public SearchTaskListResponse searchTaskByMemberAndMonth(final Long groupId,
//                                                             final Long memberId,
//                                                             final Integer year,
//                                                             final Integer month,
//                                                             HttpSession session) {
//        Groups group = groupRepository.findById(groupId)
//                .orElseThrow(() -> new GroupCustomException(GroupErrorCode.GROUP_NOT_FOUND));
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new MemberCustomException(MemberErrorCode.MEMBER_NOT_FOUND));
//
//
//        Member loginMember = (Member) session.getAttribute("loginMember");
//        boolean isGroupMember = group.getGroupMembers().stream()
//                .anyMatch(groupMember -> groupMember.getMember() == loginMember);
//        if (!isGroupMember) {
//            throw new IllegalStateException("해당그룹의 멤버가 아닙니다.");
//        }
//
//
//        GroupMember groupMember = member.getGroupMembers().stream()
//                .filter(gm -> gm.getGroups().equals(group))
//                .findFirst()
//                .orElseThrow(() -> new GroupMemberCustomException(GroupMemberErrorCode.MEMBER_NOT_IN_GROUP));
//
//        YearMonth yearMonth = YearMonth.of(year, month);
//
//        List<Task> tasksInMonth = groupMember.getTasks().stream()
//                .filter(task -> {
//                    // Task의 dueDate 필드가 주어진 연도 및 월과 일치하는지 확인
//                    YearMonth taskYearMonth = YearMonth.from(task.getDueDate());
//                    return taskYearMonth.equals(yearMonth);
//                })
//                .toList();
//
//        // Task 리스트를 SearchTaskResponse로 변환
//        List<SearchTaskResponse> taskResponses = tasksInMonth.stream()
//                .map(task -> SearchTaskResponse.builder()
//                        .taskId(task.getId())
//                        .content(task.getContent())
//                        .dueDate(task.getDueDate())
//                        .createdTime(task.getCreatedAt())
//                        .updatedTime(task.getUpdatedAt())
//                        .status(task.getStatus())
//                        .build())
//                .collect(Collectors.toList());
//
//        // SearchTaskListResponse로 반환
//        return SearchTaskListResponse.builder()
//                .tasks(taskResponses)
//                .build();
//    }

    @Transactional
    public void updateTaskStatus(UpdateTaskStatusRequest updateTaskStatusRequest) {
        Task task = taskRepository.findById(updateTaskStatusRequest.taskId())
                .orElseThrow(() -> new TaskCustomException(TaskErrorCode.TASK_NOT_FOUND));
        task.updateStatus(updateTaskStatusRequest.status());
    }
//    @Transactional
//    public void updateTaskStatus(UpdateTaskStatusRequest updateTaskStatusRequest, HttpSession session) {
//
//
//        final Task task = taskRepository.findById(updateTaskStatusRequest.taskId())
//                .orElseThrow(() -> new TaskCustomException(TaskErrorCode.TASK_NOT_FOUND));
//
//        Member loginMember = (Member) session.getAttribute("loginMember");
//        boolean isTaskOwner = task.getGroupMember().getMember().getId().equals(loginMember.getId());
//        if (!isTaskOwner) {
//            throw new IllegalStateException("본인의 task가 아닙니다.");
//        }
//
//        task.updateStatus(updateTaskStatusRequest.status());
//    }

    public SearchPendingTaskListResponse searchPendingTask(Long groupId) {
        // 1. 그룹 조회
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupCustomException(GroupErrorCode.GROUP_NOT_FOUND));

        // 2. 그룹의 그룹 멤버들 조회
        List<GroupMember> groupMembers = group.getGroupMembers();

        // 3. 그룹 멤버들의 Task 중 status가 "pend"인 Task만 필터링하여 DTO로 변환
        List<SearchPendingTaskListResponse.SearchPendingTaskResponse> pendingTasks = groupMembers.stream()
                .flatMap(groupMember -> groupMember.getTasks().stream()) // 그룹 멤버별 Task 스트림 펼치기
                .filter(task -> "pend".equals(task.getStatus()))          // 상태가 "pend"인 Task만 필터링
                .map(task -> SearchPendingTaskListResponse.SearchPendingTaskResponse.builder()
                        .taskId(task.getId())
                        .content(task.getContent())
                        .memberName(task.getGroupMember().getMember().getName())
                        .dueDate(task.getDueDate())
                        .createdTime(task.getCreatedAt())
                        .updatedTime(task.getUpdatedAt())
                        .status(task.getStatus())
                        .build())  // 빌더 패턴 완료
                .collect(Collectors.toList());  // 리스트로 변환

        // 4. 필터링된 Task 리스트 반환
        return SearchPendingTaskListResponse.builder()
                .tasks(pendingTasks)
                .build();
    }
//    public SearchPendingTaskListResponse searchPendingTask(Long groupId, HttpSession session) {
//        // 1. 그룹 조회
//        Groups group = groupRepository.findById(groupId)
//                .orElseThrow(() -> new GroupCustomException(GroupErrorCode.GROUP_NOT_FOUND));
//
//        Member loginMember = (Member) session.getAttribute("loginMember");
//        boolean isGroupMember = group.getGroupMembers().stream()
//                .anyMatch(groupMember -> groupMember.getMember() == loginMember);
//        if (!isGroupMember) {
//            throw new IllegalStateException("해당그룹의 멤버가 아닙니다.");
//        }
//
//        GroupMember targetGroupMember = loginMember.getGroupMembers().stream()
//                .filter(gm -> gm.getGroups().equals(group))
//                .findFirst()
//                .orElseThrow(() -> new GroupMemberCustomException(GroupMemberErrorCode.MEMBER_NOT_IN_GROUP));
//
//        if (!targetGroupMember.getRole().equals("admin")) {
//            throw new IllegalStateException("admin이 아닙니다.");
//        }
//
//        // 2. 그룹의 그룹 멤버들 조회
//        List<GroupMember> groupMembers = group.getGroupMembers();
//
//        // 3. 그룹 멤버들의 Task 중 status가 "pend"인 Task만 필터링하여 DTO로 변환
//        List<SearchPendingTaskListResponse.SearchPendingTaskResponse> pendingTasks = groupMembers.stream()
//                .flatMap(groupMember -> groupMember.getTasks().stream()) // 그룹 멤버별 Task 스트림 펼치기
//                .filter(task -> "pend".equals(task.getStatus()))          // 상태가 "pend"인 Task만 필터링
//                .map(task -> SearchPendingTaskListResponse.SearchPendingTaskResponse.builder()
//                        .taskId(task.getId())
//                        .content(task.getContent())
//                        .dueDate(task.getDueDate())
//                        .createdTime(task.getCreatedAt())
//                        .updatedTime(task.getUpdatedAt())
//                        .status(task.getStatus())
//                        .build())  // 빌더 패턴 완료
//                .collect(Collectors.toList());  // 리스트로 변환
//
//        // 4. 필터링된 Task 리스트 반환
//        return SearchPendingTaskListResponse.builder()
//                .tasks(pendingTasks)
//                .build();
//    }

    @Transactional
    public void createTaskForTomorrow(LocalDate dueDate) {
        List<GroupMember> groupMembers = groupMemberRepository.findAll();
        for (GroupMember groupMember : groupMembers) {
            // 해당 그룹 멤버에게 내일 할일(Task)이 있는지 확인
            boolean taskExists = taskRepository.existsByGroupMemberAndDueDate(groupMember, dueDate);

            if (!taskExists) {
                // Task가 없으면 새로운 Task 생성
                Task newTask = Task.builder()
                        .content("일일 목표 설정 기한이 지났습니다.") // 자동 생성 Task에 대한 기본 내용
                        .dueDate(dueDate)
                        .type("daily")
                        .status("deny") // 상태를 "deny"로 설정
                        .groupMember(groupMember) // 해당 그룹 멤버 설정
                        .build();
                taskRepository.save(newTask);
            }
        }
    }

    @Transactional
    public void createTaskForNextWeek(LocalDate dueDate) {
        // 그룹 멤버들 조회
        List<GroupMember> groupMembers = groupMemberRepository.findAll();

        // 주차의 시작일(월요일)과 끝일(일요일)을 계산
        LocalDate startOfWeek = dueDate.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = dueDate.with(DayOfWeek.SUNDAY);

        for (GroupMember groupMember : groupMembers) {
            // 해당 그룹 멤버가 해당 주에 'weekly' 타입의 Task가 있는지 확인
            boolean taskExists = taskRepository.existsByGroupMemberAndTypeAndDueDateBetween(
                    groupMember, "weekly", startOfWeek, endOfWeek
            );

            if (!taskExists) {
                // 주차의 일요일을 dueDate로 하는 새로운 Task 생성
                Task newTask = Task.builder()
                        .content("주간 목표 설정 기한이 지났습니다.") // 기본 Task 내용
                        .dueDate(endOfWeek) // 해당 주차의 일요일을 기한으로 설정
                        .type("weekly") // 주간 Task
                        .status("deny") // 상태를 'pending'으로 설정
                        .groupMember(groupMember) // 해당 그룹 멤버 설정
                        .build();
                taskRepository.save(newTask);
            }
        }
    }

    @Transactional
    public void createTaskForThisMonth(LocalDate today) {
        // 모든 그룹 멤버를 조회
        List<GroupMember> groupMembers = groupMemberRepository.findAll();

        // 이번 달의 첫 번째 날 (1일)
        LocalDate startOfMonth = today.withDayOfMonth(1);
        // 이번 달의 마지막 날 계산
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        for (GroupMember groupMember : groupMembers) {
            // 해당 그룹 멤버가 이번 달에 'monthly' 타입의 Task를 가지고 있는지 확인
            boolean taskExists = taskRepository.existsByGroupMemberAndTypeAndDueDateBetween(
                    groupMember, "monthly", startOfMonth, endOfMonth
            );

            if (!taskExists) {
                // monthly Task가 없을 경우 새로운 Task 생성
                Task newTask = Task.builder()
                        .content("월 목표 설정 기한이 지났습니다.") // 기본 Task 내용
                        .dueDate(startOfMonth) // 이번 달 1일을 기한으로 설정
                        .type("monthly") // 월간 Task
                        .status("deny") // 상태를 'pending'으로 설정
                        .groupMember(groupMember) // 해당 그룹 멤버 설정
                        .build();
                taskRepository.save(newTask);
            }
        }
    }

    @Transactional
    public void updateNoneToDeny(LocalDate date){
        List<Task> tasks = taskRepository.findByDueDateAndStatus(date, "none");
        for (Task task : tasks) {
            task.updateStatus("deny");
        }
    }


}
