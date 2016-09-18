package hackthenorth.neighborcater.utils;

import java.text.DecimalFormat;

/**
 * Created by rowandempster on 9/17/16.
 */

public class RoundingUtils {
    public static String roundDoubleToTwoDecimals(double doubleToRound){
        DecimalFormat df = new DecimalFormat("#.##");
        return String.valueOf(df.format(doubleToRound));
    }
}
