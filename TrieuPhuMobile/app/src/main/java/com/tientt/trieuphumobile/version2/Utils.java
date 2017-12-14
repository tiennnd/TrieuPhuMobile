package com.tientt.trieuphumobile.version2;

import android.content.res.Resources;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by nguyentien on 10/10/17.
 */

public class Utils {
    public static String formatMoney(String money) {
        String result = "";
        money = money.replace(" ", "");
        char[] chars = money.toCharArray();
        int count = 0;
        for (int i = money.length() - 1; i >= 0; i--) {
            count++;
            if (count < money.length() && count % 3 == 0)
                result = "." + chars[i] + result;
            else
                result = chars[i] + result;

        }
        return result;
    }


    public static String formatAmount(int num) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols decimalFormateSymbol = new DecimalFormatSymbols();
        decimalFormateSymbol.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormateSymbol);
        return decimalFormat.format(num);
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
}
