package slim3.controller.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slim3.controller.Navigation;

import slim3.controller.AbstractController;
public class TestController extends AbstractController {

    @Override
    public Navigation run() throws Exception {
        java.util.Date shopEndTime = null;
        try {
            String str = "2015/12/15 22:00";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            shopEndTime = sdf.parse(str);
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        Calendar intervalTimeCal = Calendar.getInstance();
        intervalTimeCal.setTime(shopEndTime);
        intervalTimeCal.add(Calendar.MINUTE, 30);
        String year = Integer.toString(intervalTimeCal.get(intervalTimeCal.YEAR));
        String month = Integer.toString(intervalTimeCal.get(intervalTimeCal.MONTH) + 1);
        String date = Integer.toString(intervalTimeCal.get(intervalTimeCal.DATE));
        String hour = Integer.toString(intervalTimeCal.get(intervalTimeCal.HOUR_OF_DAY));
        String minute = Integer.toString(intervalTimeCal.get(intervalTimeCal.MINUTE));
        log.info(String.format("%s/%s/%s %s:%s", year, month, date, hour, minute));
        
        return null;
    }
}
