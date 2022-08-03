import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.temporal.ChronoField;

public class DateTest {
    private static final Logger log = LoggerFactory.getLogger(DateTest.class);
    @Test
    public void testMain12() {
        log.debug("Date chapter12 Test main Start");
        LocalDate date = LocalDate.now();
        // LocalDate date = LocalDate.of(2017, 9, 21);  // 2017-09-21
        int year = date.getYear();                      // 2017
        Month month = date.getMonth();                  // SEPTEMBER
        int day = date.getDayOfMonth();                 // 21
        DayOfWeek dow = date.getDayOfWeek();            // THURSDAY
        int len = date.lengthOfMonth();                 // 해당 월의 일 수, 30
        boolean leap = date.isLeapYear();               // 윤년 여부, false
        log.debug("{} {} {} {} {} {} {}", date, year, month, day, dow, len, leap);
        year = date.get(ChronoField.YEAR);
        int mon = date.get(ChronoField.MONTH_OF_YEAR);
        day = date.get(ChronoField.DAY_OF_MONTH);
        log.debug("{} {} {} {} {} {} {}", date, year, mon, day, dow, len, leap);

        LocalTime time = LocalTime.now();
        // LocalTime time = LocalTime.of(13, 45, 20);
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
        log.debug("{} {} {}", hour, minute, second);

        LocalDateTime dt1 = LocalDateTime.now();
        LocalDateTime dt2 = LocalDateTime.of(2017, 9, 21, 13, 45, 20);
        LocalDateTime dt3 = LocalDateTime.of(date, time);
        LocalDateTime dt4 = time.atDate(date);
        LocalDateTime dt5 = date.atTime(time);
        log.debug("dt : {}", dt1);
        log.debug("dt : {}", dt2);
        log.debug("dt : {}", dt3);
        log.debug("dt : {}", dt4);
        log.debug("dt : {}", dt5);

        LocalTime time1 = LocalTime.of(20, 20, 20);
        LocalTime time2 = LocalTime.of(16, 20, 20);
        LocalDateTime dateTime1 = time1.atDate(LocalDate.of(2017, 12, 25));
        LocalDateTime dateTime2 = time1.atDate(LocalDate.now());
        Instant instant1 = Instant.now();
        Instant instant2 = Instant.ofEpochSecond(2000000000);
        Duration d1 = Duration.between(time1, time2);
        Duration d2 = Duration.between(dateTime1, dateTime2);
        Duration d3 = Duration.between(instant1, instant2);
        log.debug("d : {}", d1.getSeconds());
        log.debug("d : {}", d2.getSeconds());
        log.debug("d : {}", d3.getSeconds());

        Period p1 = Period.between(LocalDate.of(2020, 5, 15), LocalDate.of(2021, 6, 25));
        log.debug("p : {}", p1.getYears());
        log.debug("p : {}", p1.getMonths());
        log.debug("p : {}", p1.getDays());

        log.debug("Date chapter12 Test main End");
    }
}
