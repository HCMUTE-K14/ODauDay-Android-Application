package com.odauday.ui.view.currencyedittext;

import android.util.Log;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by infamouSs on 4/24/18.
 */
public final class CurrencyTextFormatter {
    
    private CurrencyTextFormatter() {
    }
    
    public static String formatText(String val, Locale locale) {
        return formatText(val, locale, Locale.US, null);
    }
    
    public static String formatText(String val, Locale locale, Locale defaultLocale) {
        return formatText(val, locale, defaultLocale, null);
    }
    
    public static String formatText(String val, Locale locale, Locale defaultLocale,
              Integer decimalDigits) {
        if (val.equals("-")) {
            return val;
        }
        
        int currencyDecimalDigits;
        if (decimalDigits != null) {
            currencyDecimalDigits = decimalDigits;
            
        } else {
            Currency currency = Currency.getInstance(locale);
            try {
                currencyDecimalDigits = currency.getDefaultFractionDigits();
            } catch (Exception e) {
                currencyDecimalDigits = Currency.getInstance(defaultLocale)
                          .getDefaultFractionDigits();
            }
        }
        
        DecimalFormat currencyFormatter;
        try {
            currencyFormatter = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) currencyFormatter)
                      .getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            currencyFormatter.setDecimalFormatSymbols(decimalFormatSymbols);
        } catch (Exception e) {
            try {
                Log.e("CurrencyTextFormatter", "Error detected for locale: " + locale +
                                               ", falling back to default value: " + defaultLocale);
                currencyFormatter = (DecimalFormat) DecimalFormat
                          .getCurrencyInstance(defaultLocale);
                DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) currencyFormatter)
                          .getDecimalFormatSymbols();
                decimalFormatSymbols.setCurrencySymbol("");
                currencyFormatter.setDecimalFormatSymbols(decimalFormatSymbols);
            } catch (Exception e1) {
                currencyFormatter = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.US);
                DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) currencyFormatter)
                          .getDecimalFormatSymbols();
                decimalFormatSymbols.setCurrencySymbol("");
                currencyFormatter.setDecimalFormatSymbols(decimalFormatSymbols);
            }
        }
        
        boolean isNegative = false;
        if (val.contains("-")) {
            isNegative = true;
        }
        
        val = val.replaceAll("[^\\d]", "");
        
        if (!val.equals("")) {
            
            if (val.length() <= currencyDecimalDigits) {
                String formatString = "%" + currencyDecimalDigits + "s";
                val = String.format(formatString, val).replace(' ', '0');
            }
            
            String preparedVal = new StringBuilder(val)
                      .insert(val.length() - currencyDecimalDigits, '.').toString();
            
            double newTextValue = Double.valueOf(preparedVal);
            
            newTextValue *= isNegative ? -1 : 1;
            
            currencyFormatter.setMinimumFractionDigits(currencyDecimalDigits);
            val = currencyFormatter.format(newTextValue);
        } else {
            throw new IllegalArgumentException(
                      "Invalid amount of digits found (either zero or too many) in argument val");
        }
        return val;
    }
    
}
