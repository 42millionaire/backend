package _2._millionaire.task;

import _2._millionaire.group.GroupRepository;
import _2._millionaire.group.Groups;
import _2._millionaire.groupmember.GroupMember;
import _2._millionaire.groupmember.GroupMemberRepository;
import _2._millionaire.member.Member;
import _2._millionaire.member.MemberRepository;
import _2._millionaire.task.dto.CreateTaskRequest;
import _2._millionaire.task.dto.SearchTaskListResponse;
import _2._millionaire.task.dto.SearchTaskResponse;
import _2._millionaire.task.dto.UpdateTaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void createTask(CreateTaskRequest createTaskRequest) {
        final Member member = memberRepository.findById(createTaskRequest.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));
        final Groups groups = groupRepository.findById(createTaskRequest.groupId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        GroupMember groupMember = member.getGroupMembers().stream()
                .filter(gm -> gm.getGroups().equals(groups))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 해당 그룹에 포함되어 있지 않습니다."));

        Task task = Task.builder()
                .type(createTaskRequest.type())
                .groupMember(groupMember)
                .content(createTaskRequest.content())
                .build();
        taskRepository.save(task);
    }

    @Transactional
    public void updateTask(UpdateTaskRequest updateTaskRequest) {
        final Task task = taskRepository.findById(updateTaskRequest.taskId())
                .orElseThrow();
        task.updateContent(updateTaskRequest.content());
    }

    @Transactional
    public void deleteTask(final Long taskId) {
        final Task task = taskRepository.findById(taskId)
                .orElseThrow();
        //태스크의 주인이 로그인 한 주인과 같은지 확인 후 삭제해야함 추후 추가할 예정
        taskRepository.delete(task);
    }

    @Transactional(readOnly = true)
    public SearchTaskResponse searchTask(final Long taskId) {
        final Task task = taskRepository.findById(taskId)
                .orElseThrow();
        return SearchTaskResponse.builder()
                .taskId(task.getId())
                .content(task.getContent())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .status(task.getStatus())
                .build();
    }

    @Transactional(readOnly = true)
    public SearchTaskListResponse searchTaskByMemberAndMonth(final Long groupId,
                                                             final Long memberId,
                                                             final Integer year,
                                                             final Integer month) {
        Groups group = groupRepository.findById(groupId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        GroupMember groupMember = member.getGroupMembers().stream()
                .filter(gm -> gm.getGroups().equals(group))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 해당 그룹에 포함되어 있지 않습니다."));

        YearMonth yearMonth = YearMonth.of(year, month);

        List<Task> tasksInMonth = groupMember.getTasks().stream()
                .filter(task -> {
                    // Task의 createdAt 필드가 주어진 연도 및 월과 일치하는지 확인
                    YearMonth taskYearMonth = YearMonth.from(task.getCreatedAt());
                    return taskYearMonth.equals(yearMonth);
                })
                .toList();

        // Task 리스트를 SearchTaskResponse로 변환
        List<SearchTaskResponse> taskResponses = tasksInMonth.stream()
                .map(task -> SearchTaskResponse.builder()
                        .taskId(task.getId())
                        .content(task.getContent())
                        .createdAt(task.getCreatedAt())
                        .updatedAt(task.getUpdatedAt())
                        .status(task.getStatus())
                        .build())
                .collect(Collectors.toList());

        // SearchTaskListResponse로 반환
        return SearchTaskListResponse.builder()
                .tasks(taskResponses)
                .build();
    }
}
