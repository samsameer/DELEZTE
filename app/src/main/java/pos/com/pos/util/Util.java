package pos.com.pos.util;

import java.text.DecimalFormat;

/**
 * Created by HJ Chin on 31/12/2017.
 */

public class Util {

    public static String formatDisplay(double value){
        DecimalFormat f = new DecimalFormat("#0.00");
        return f.format(value);
    }

    public static String formatMoney(double value){
        return "$"+formatDisplay(value);
    }

    public static String formatMoney(String value){
        return "$"+value;
    }

    public static String formatChargeText(String chargeInString){
        return "Charge "+formatMoney(chargeInString);
    }
}
