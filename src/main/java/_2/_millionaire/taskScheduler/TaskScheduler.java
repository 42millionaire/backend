package _2._millionaire.taskScheduler;

import _2._millionaire.task.Task;
import _2._millionaire.task.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskScheduler {
    private final TaskServiceImpl taskService;

    // 매일 새벽 03:00에 실행
    @Scheduled(cron = "0 0 3 ? * MON-FRI")
    public void checkAndCreatePendingTasks() {
        LocalDate date = LocalDate.now();
        taskService.createTaskForTomorrow(date);
    }

    @Scheduled(cron = "59 59 23 ? * MON-FRI")
    public void updateTaskStatusToDeny(){
        LocalDate today = LocalDate.now();
        taskService.updateTaskStatus(today);
    }
}
