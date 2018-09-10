package pos.com.pos.Util;

import org.junit.Test;

import pos.com.pos.util.Util;

import static org.junit.Assert.assertEquals;

/**
 * Created by HJ Chin on 31/12/2017.
 */

public class UtilTest {

    @Test
    public void testFormatDisplay(){
        double value = 0.0;
        assertEquals("0.00",Util.formatDisplay(value));

        double v2 = 0.01;
        assertEquals("0.01",Util.formatDisplay(v2));

        double v3 = 0.11;
        assertEquals("0.11",Util.formatDisplay(v3));

        double v4 = 1.11;
        assertEquals("1.11",Util.formatDisplay(v4));

        double v5 = 11.11;
        assertEquals("11.11",Util.formatDisplay(v5));

        double v6 = 11111;
        assertEquals("11111.00",Util.formatDisplay(v6));
    }

    @Test
    public void testFormatMoney(){
        double value = 0.0;
        assertEquals("$0.00",Util.formatMoney(value));

        double v3 = 0.11;
        assertEquals("$0.11",Util.formatMoney(v3));


        String v4 = "100.00";
        assertEquals("$100.00",Util.formatMoney(v4));
    }
}
