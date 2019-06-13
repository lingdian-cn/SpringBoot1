import com.dling.springboot.kit.DateKit;
import com.dling.springboot.kit.IOKit;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class DateKitTest {
    @Test
    public void testYearMonthDay(){
        Date today = new Date();
        Date d1 = DateKit.toDate("2019-06-17 13:20:30");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d1);
        System.out.println("year:"+calendar.get(Calendar.YEAR));
        System.out.println("month:"+(calendar.get(Calendar.MONTH)+1));
        System.out.println("day:"+calendar.get(Calendar.DATE));
        System.out.println("hour:"+calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println("minute:"+calendar.get(Calendar.MINUTE));
        System.out.println("second:"+calendar.get(Calendar.SECOND));
        System.out.println("WEEK_OF_MONTH:"+calendar.get(Calendar.WEEK_OF_MONTH));
        System.out.println("WEEK_OF_YEAR:"+calendar.get(Calendar.WEEK_OF_YEAR));
        System.out.println("DAY_OF_WEEK:"+calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println("SUNDAY:"+calendar.get(Calendar.SUNDAY));
        System.out.println("week:"+DateKit.getWeekly(d1));
    }

    @Test
    public void testDateDiff(){
        int type = 0; // mills 0, second 1, minute 2, hour 3

        Date d1 = DateKit.toDate("2018-12-18 13:00:00");
        Date d2 = DateKit.toDate("2019-01-17 13:00:00");
        Long mills = d1.getTime()-d2.getTime();
        System.out.println("mills:" + mills);
        boolean abs = false;
        System.out.println("ms：" + DateKit.dateDiff(d1, d2 , DateKit.MILLS, abs));
        System.out.println("秒：" + DateKit.dateDiff(d1, d2 , DateKit.SECOND, abs));
        System.out.println("分：" + DateKit.dateDiff(d1, d2 , DateKit.MINUTE, abs));
        System.out.println("时：" + DateKit.dateDiff(d1, d2 , DateKit.HOUR, abs));
        System.out.println("天：" + DateKit.dateDiff(d1, d2 , DateKit.DAY, abs));
        System.out.println("月：" + DateKit.dateDiff(d1, d2 , DateKit.MONTH, abs));
        System.out.println("年：" + DateKit.dateDiff(d1, d2 , DateKit.YEAR, abs));
        System.out.println("-------------------------------------------------");
        String s1 = "13:00:00";
        String s2 = "13:02:01";
        System.out.println("ms：" + DateKit.dateDiff(s1, s2 , DateKit.MILLS, abs));
        System.out.println("秒：" + DateKit.dateDiff(s1, s2 , DateKit.SECOND, abs));
        System.out.println("分：" + DateKit.dateDiff(s1, s2 , DateKit.MINUTE, abs));
        System.out.println("时：" + DateKit.dateDiff(s1, s2 , DateKit.HOUR, abs));
        System.out.println("天：" + DateKit.dateDiff(s1, s2 , DateKit.DAY, abs));
        System.out.println("月：" + DateKit.dateDiff(s1, s2 , DateKit.MONTH, abs));
        System.out.println("年：" + DateKit.dateDiff(s1, s2 , DateKit.YEAR, abs));
        System.out.println("-------------------------------------------------");
        System.out.println(DateKit.toDate(s1));
        System.out.println(DateKit.toDate(s2));
        System.out.println(DateKit.toStr(new Date(), DateKit.DATE_TIME_WEEK_PATTERN));
    }

    @Test
    public void testDateAdd(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 2);
        c.add(Calendar.MONTH, 2);
        c.add(Calendar.DAY_OF_MONTH, 20);
        c.add(Calendar.HOUR_OF_DAY, 2);
        c.add(Calendar.MINUTE, 2);
        c.add(Calendar.SECOND, 2);
        c.add(Calendar.MILLISECOND, 2);
        c.add(Calendar.DAY_OF_WEEK, 2);

        System.out.println(DateKit.toStr(c.getTime(), DateKit.DATE_TIME_WEEK_PATTERN));

        System.out.println(DateKit.dateAdd(new Date(), DateKit.YEAR, -1));
    }

    @Test
    public void testIOKit(){
//        IOKit.toByteArray();
    }
}
