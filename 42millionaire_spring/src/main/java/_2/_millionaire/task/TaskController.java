package _2._millionaire.task;

import _2._millionaire.task.dto.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskServiceImpl taskService;

    @PostMapping("")
    public ResponseEntity<String> createTask(@RequestBody CreateTaskRequest createTaskRequest){
        taskService.createTask(createTaskRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("목표가 생성되었습니다.");
    }
//    @PostMapping("")
//    public ResponseEntity<String> createTask(@RequestBody CreateTaskRequest createTaskRequest, HttpSession session){
//        taskService.createTask(createTaskRequest, session);
//        return ResponseEntity.status(HttpStatus.CREATED).body("목표가 생성되었습니다.");
//    }

    @PatchMapping("")
    public ResponseEntity<String> updateTask(@RequestBody UpdateTaskRequest updateTaskRequest){
        taskService.updateTask(updateTaskRequest);
        return ResponseEntity.status(HttpStatus.OK).body("목표 변경되었습니다.");
    }
//    @PatchMapping("")
//    public ResponseEntity<String> updateTask(@RequestBody UpdateTaskRequest updateTaskRequest,
//                                             HttpSession session){
//        taskService.updateTask(updateTaskRequest, session);
//        return ResponseEntity.status(HttpStatus.OK).body("목표 변경되었습니다.");
//    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteTask(@RequestParam(name = "taskId") Long taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.status(HttpStatus.OK).body("목표 삭제되었습니다.");
    }
//    @DeleteMapping("")
//    public ResponseEntity<String> deleteTask(@RequestParam(name = "taskId") Long taskId,
//                                             HttpSession session){
//        taskService.deleteTask(taskId, session);
//        return ResponseEntity.status(HttpStatus.OK).body("목표 삭제되었습니다.");
//    }

    @GetMapping("/{task_id}")
    public ResponseEntity<SearchTaskResponse> searchTask(@PathVariable("task_id") Long taskId){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.searchTask(taskId));
    }
//    @GetMapping("/{task_id}")
//    public ResponseEntity<SearchTaskResponse> searchTask(@PathVariable("task_id") Long taskId, HttpSession session){
//        return ResponseEntity.status(HttpStatus.OK).body(taskService.searchTask(taskId, session));
//    }

    @GetMapping("")
    public ResponseEntity<SearchTaskListResponse> searchTaskByMemberAndMonth(@RequestParam(name = "groupId") Long groupId,
                                                                             @RequestParam(name = "memberId") Long memberId,
                                                                             @RequestParam(name = "year") Integer year,
                                                                             @RequestParam(name = "month") Integer month){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.searchTaskByMemberAndMonth(groupId, memberId, year, month));
    }
//    @GetMapping("")
//    public ResponseEntity<SearchTaskListResponse> searchTaskByMemberAndMonth(@RequestParam(name = "groupId") Long groupId,
//                                                                             @RequestParam(name = "memberId") Long memberId,
//                                                                             @RequestParam(name = "year") Integer year,
//                                                                             @RequestParam(name = "month") Integer month,
//                                                                             HttpSession session){
//        return ResponseEntity.status(HttpStatus.OK).body(taskService.searchTaskByMemberAndMonth(groupId, memberId, year, month, session));
//    }

    @PatchMapping("/status")
    public ResponseEntity<String> updateTaskStatus(@RequestBody UpdateTaskStatusRequest updateTaskStatusRequest){
        taskService.updateTaskStatus(updateTaskStatusRequest);
        return ResponseEntity.status(HttpStatus.OK).body("인증 상태 변경 되었습니다.");
    }
//    @PatchMapping("/status")
//    public ResponseEntity<String> updateTaskStatus(@RequestBody UpdateTaskStatusRequest updateTaskStatusRequest, HttpSession session){
//        taskService.updateTaskStatus(updateTaskStatusRequest, session);
//        return ResponseEntity.status(HttpStatus.OK).body("인증 상태 변경 되었습니다.");
//    }

    @GetMapping("/pend/{groupId}")
    public ResponseEntity<SearchPendingTaskListResponse> searchPendingTask(@PathVariable ("groupId") Long groupId){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.searchPendingTask(groupId));
    }
//    @GetMapping("/pend/{groupId}")
//    public ResponseEntity<SearchPendingTaskListResponse> searchPendingTask(@PathVariable ("groupId") Long groupId, HttpSession session){
//        return ResponseEntity.status(HttpStatus.OK).body(taskService.searchPendingTask(groupId, session));
//    }
}
