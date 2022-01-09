package utiles;

import java.sql.Date;

public class DateMath {
	public static long getNumberOfDays(Date startDate, Date endDate) {
        long difference = (endDate.getTime() - startDate.getTime()) / 86400000;  // 86400000 is the number of miliseconds in a day
        return Math.abs(difference);
    }
}
