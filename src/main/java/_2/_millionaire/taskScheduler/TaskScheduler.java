package _2._millionaire.taskScheduler;

import _2._millionaire.task.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
        String apiUrl = "https://holidayapi.com/v1/holidays?country=KR&year=" + date.getYear() + "&key=YOUR_API_KEY";

        // 공휴일 리스트를 호출하여 List<LocalDate>로 변환
        List<LocalDate> holidayList = getHolidayListFromApi(apiUrl);

        // 2. 오늘 날짜가 공휴일 리스트에 있는지 확인
        if (holidayList.contains(date)) {
            return; // 오늘이 공휴일이면 바로 리턴
        }

        // 3. 오늘이 공휴일이 아니라면 내일의 Task를 생성
        taskService.createTaskForTomorrow(date);
    }

    // 공휴일 API를 호출하고 LocalDate 리스트로 반환하는 메서드
    private List<LocalDate> getHolidayListFromApi(String apiUrl) {
        // Holiday API로부터 공휴일 데이터를 받아옴
        HolidayApiResponse response = restTemplate.getForObject(apiUrl, HolidayApiResponse.class);

        // 응답에서 공휴일 날짜를 LocalDate 리스트로 변환
        return Arrays.stream(response.getHolidays())
                .map(holiday -> LocalDate.parse(holiday.getDate()))
                .toList();
    }

    // Holiday API 응답을 매핑할 클래스
    public static class HolidayApiResponse {
        private Holiday[] holidays;

        public Holiday[] getHolidays() {
            return holidays;
        }

        public void setHolidays(Holiday[] holidays) {
            this.holidays = holidays;
        }
    }

    // 공휴일 객체를 매핑할 클래스
    public static class Holiday {
        private String date; // 날짜를 String으로 받음 (ISO 8601 형식)

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
