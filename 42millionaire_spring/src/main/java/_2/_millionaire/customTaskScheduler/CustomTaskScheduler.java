package _2._millionaire.customTaskScheduler;

import _2._millionaire.task.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomTaskScheduler {

    private static final Logger logger = LoggerFactory.getLogger(CustomTaskScheduler.class);
    private final TaskServiceImpl taskService;
    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate 초기화

//    @Scheduled(cron = "*/1 * * * * *")
    @Scheduled(cron = "0 0 3 ? * MON-FRI")
    public void checkAndCreatePendingTasks() {
        LocalDate today = LocalDate.now();

        if (!isHoliday(today)) {
            logger.info("공휴일이 아닙니다. task를 생성합니다.");
            taskService.createTaskForTomorrow(today); // 오늘이 공휴일이 아니라면 오늘의 테스트 생성
        } else {
            logger.info("공휴일입니다. task를 생성하지 않습니다.");
        }
    }

    @Scheduled(cron = "0 0 3 ? * MON-FRI")
    public void updateTaskStatusToDeny(){
        LocalDate today = LocalDate.now();

        if (!isHoliday(today))
            taskService.updateNoneToDeny(today);
    }

    boolean isHoliday(LocalDate date) {
        // 1. 한국 공휴일 API URL 생성
        String holidayUrlRoot = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";
        String holidayServiceKey = "EAMJ%2FgCv4wL7M2aNd6JDH%2BG9EHyV8URFsc3AJ53kfcC1%2FvsS%2Bxlwn%2BaaMjioeH6mopuAe4vroiZxxXsAB3Islw%3D%3D";  // 발급받은 서비스 키를 여기에 입력

        // URL 구성
        StringBuilder url = new StringBuilder(holidayUrlRoot);
        url.append("?").append("serviceKey").append("=").append(holidayServiceKey);
        url.append("&").append("solYear").append("=").append(date.getYear());
        url.append("&").append("solMonth").append("=").append(String.format("%02d", date.getMonthValue()));
        url.append("&").append("_type").append("=").append("json");

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "*/*;q=0.9");

        // HttpEntity 생성
        HttpEntity<String> httpRequest = new HttpEntity<>(null, headers);

        // RestTemplate을 통한 API 호출
        ResponseEntity<Map> httpResponse = null;
        try {
            URI uri = new URI(url.toString());
            httpResponse = restTemplate.exchange(uri, HttpMethod.GET, httpRequest, Map.class);

            // 응답에서 공휴일 리스트를 추출
            Map<String, Object> responseBody = (Map<String, Object>) httpResponse.getBody().get("response");
            Map<String, Object> body = (Map<String, Object>) responseBody.get("body");
            Object itemsObject = body.get("items");

            // 공휴일이 없을 경우 즉시 false 반환
            if (itemsObject == null || itemsObject.equals("")) {
//                logger.info("공휴일이 없습니다.");
                return false;
            }

            Map<String, Object> items = (Map<String, Object>) itemsObject;
            List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");

            // 공휴일이 없을 경우 즉시 false 반환
            if (itemList == null || itemList.isEmpty()) {
//                logger.info("공휴일이 없습니다.");
                return true;
            }

            // 공휴일 리스트를 LocalDate 리스트로 변환
            List<LocalDate> holidays = new ArrayList<>();
            for (Map<String, Object> item : itemList) {
                Integer locdate = (Integer) item.get("locdate");
                // locdate는 yyyyMMdd 형식이므로 이를 LocalDate로 변환
                LocalDate holiday = LocalDate.parse(locdate.toString(), DateTimeFormatter.ofPattern("yyyyMMdd"));
                holidays.add(holiday);
            }

            // 오늘 날짜와 공휴일 리스트를 비교
            if (holidays.contains(date)) {
                return true; // 오늘이 공휴일이면 true 반환
            }

        } catch (Exception e) {
            logger.error("API 호출 중 오류 발생: ", e);
        }

        return false; // 공휴일이 아니면 false 반환
    }
}