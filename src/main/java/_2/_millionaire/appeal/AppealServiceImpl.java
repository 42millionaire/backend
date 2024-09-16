package _2._millionaire.appeal;

import _2._millionaire.appeal.dto.CreateAppealRequest;
import _2._millionaire.appeal.dto.SearchAppealListResponse;
import _2._millionaire.appeal.dto.SearchAppealResponse;
import _2._millionaire.group.GroupRepository;
import _2._millionaire.group.Groups;
import _2._millionaire.task.Task;
import _2._millionaire.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService{
    
    private final AppealRepository appealRepository;
    private final TaskRepository taskRepository;
    private final GroupRepository groupRepository;
    @Transactional
    public void createAppeal(CreateAppealRequest createAppealRequest) {
        Task task = taskRepository.findById(createAppealRequest.taskId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 task입니다."));
        Appeal appeal = new Appeal(createAppealRequest.content(), "pend", task);
        appealRepository.save(appeal);
    }

    public SearchAppealListResponse searchAllAppeals(Long groupId) {
        Groups groups = groupRepository.findById(groupId).orElseThrow();
        List<Task> tasks = groups.getGroupMembers().stream()
                .flatMap(groupMember -> groupMember.getTasks().stream()) // 각 그룹 멤버의 Task 스트림으로 변환
                .toList(); // 하나의 리스트로 수집

        // Task에서 Appeal의 content를 모두 가져와 연결
        List<SearchAppealResponse> searchAppealResponses = tasks.stream()
                .flatMap(task -> task.getAppeals().stream() // 각 Task의 Appeal 스트림으로 변환
                        .map(appeal -> SearchAppealResponse.builder()
                                .taskId(task.getId())
                                .memberId(task.getGroupMember().getMember().getId())
                                .memberName(task.getGroupMember().getMember().getName())
                                .content(appeal.getContent()) // Appeal의 content 설정
                                .createTime(appeal.getCreatedAt())
                                .build()))
                .toList();
        return new SearchAppealListResponse(searchAppealResponses);
    }
}
