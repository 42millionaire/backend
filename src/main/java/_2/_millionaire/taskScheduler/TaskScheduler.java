package _2._millionaire.taskScheduler;

import _2._millionaire.task.Task;
import _2._millionaire.task.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class TaskScheduler {
    private final TaskServiceImpl taskService;
    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate 초기화

    // 매일 새벽 03:00에 실행
    @Scheduled(cron = "0 0 3 ? * MON-FRI")
    public void checkAndCreatePendingTasks() {
        LocalDate date = LocalDate.now();

        // 1. 한국 공휴일 API를 호출하여 공휴일 리스트를 가져옴
        String serviceKey = "EAMJ%2FgCv4wL7M2aNd6JDH%2BG9EHyV8URFsc3AJ53kfcC1%2FvsS%2Bxlwn%2BaaMjioeH6mopuAe4vroiZxxXsAB3Islw%3D%3D";  // 발급받은 서비스 키를 여기에 입력
        String url = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo" +
                "?solYear=" + date.getYear() +
                "&solMonth=" + String.format("%02d", date.getMonthValue()) +
                "&ServiceKey=" + serviceKey +
                "&_type=json";

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        // 응답에서 body를 안전하게 추출하기
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) responseBody.get("response");
        LinkedHashMap<String, Object> bodyMap = (LinkedHashMap<String, Object>) responseMap.get("body");
        LinkedHashMap<String, Object> itemsMap = (LinkedHashMap<String, Object>) bodyMap.get("items");

        // 공휴일 리스트를 가져옴
        List<Map<String, Object>> items = (List<Map<String, Object>>) itemsMap.get("item");

        // 오늘 날짜가 공휴일인지를 확인
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = date.format(formatter);

        for (Map<String, Object> item : items) {
            String holidayDate = item.get("locdate").toString();
            if (today.equals(holidayDate)) {
                return;  // 오늘이 공휴일이면 작업을 중단하고 리턴
            }
        }

        // 오늘이 공휴일이 아니라면 오늘의 테스트 생성
        taskService.createTaskForTomorrow(date);
    }

    @Scheduled(cron = "59 59 23 ? * MON-FRI")
    public void updateTaskStatusToDeny(){
        LocalDate today = LocalDate.now();
        taskService.updateTaskStatus(today);
    }
}
