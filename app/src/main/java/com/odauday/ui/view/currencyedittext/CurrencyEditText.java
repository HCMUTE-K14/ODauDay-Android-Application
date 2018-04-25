package com.odauday.ui.view.currencyedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import com.odauday.R;
import java.math.BigInteger;
import java.util.Currency;
import java.util.Locale;


/**
 * Created by infamouSs on 4/24/18.
 */
public class CurrencyEditText extends EditText {
    
    private Locale currentLocale;
    
    private Locale defaultLocale = Locale.US;
    
    private boolean allowNegativeValues = false;
    
    private BigInteger rawValue = new BigInteger("0");
    
    private CurrencyTextWatcher textWatcher;
    private String hintCache = null;
    
    private int decimalDigits = 0;
    
    public CurrencyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        processAttributes(context, attrs);
    }

    public void setAllowNegativeValues(boolean negativeValuesAllowed) {
        allowNegativeValues = negativeValuesAllowed;
    }

    public boolean areNegativeValuesAllowed() {
        return allowNegativeValues;
    }
    
    public BigInteger getRawValue() {
        return rawValue;
    }
    
    public void setValue(BigInteger value) {
        String formattedText = format(value);
        setText(formattedText);
    }
    
    public Locale getLocale() {
        return currentLocale;
    }
    
    public void setLocale(Locale locale) {
        currentLocale = locale;
        refreshView();
    }
    
    public String getHintString() {
        CharSequence result = super.getHint();
        if (result == null) {
            return null;
        }
        return super.getHint().toString();
    }
    
    public int getDecimalDigits() {
        return decimalDigits;
    }
    
    public void setDecimalDigits(int digits) {
        if (digits < 0 || digits > 340) {
            throw new IllegalArgumentException("Decimal Digit value must be between 0 and 340");
        }
        decimalDigits = digits;
        
        refreshView();
    }
    
    public void configureViewForLocale(Locale locale) {
        this.currentLocale = locale;
        Currency currentCurrency = getCurrencyForLocale(locale);
        decimalDigits = currentCurrency.getDefaultFractionDigits();
        refreshView();
    }
    
    public void setDefaultLocale(Locale locale) {
        this.defaultLocale = locale;
    }

    public Locale getDefaultLocale() {
        return defaultLocale;
    }
    
    public String formatCurrency(String val) {
        return format(val);
    }
    
    public String formatCurrency(BigInteger rawVal) {
        return format(rawVal);
    }
    
    private void refreshView() {
        setText(format(getRawValue()));
        updateHint();
    }
    
    private String format(BigInteger val) {
        return CurrencyTextFormatter
                  .formatText(String.valueOf(val), currentLocale, defaultLocale, decimalDigits);
    }
    
    private String format(String val) {
        return CurrencyTextFormatter.formatText(val, currentLocale, defaultLocale, decimalDigits);
    }
    
    private void init() {
        this.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL |
                          InputType.TYPE_NUMBER_FLAG_SIGNED);
        
        currentLocale = retrieveLocale();
        Currency currentCurrency = getCurrencyForLocale(currentLocale);
        decimalDigits = currentCurrency.getDefaultFractionDigits();
        initCurrencyTextWatcher();
    }
    
    private void initCurrencyTextWatcher() {
        if (textWatcher != null) {
            this.removeTextChangedListener(textWatcher);
        }
        textWatcher = new CurrencyTextWatcher(this);
        this.addTextChangedListener(textWatcher);
    }
    
    private void processAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CurrencyEditText);
        this.hintCache = getHintString();
        updateHint();
        
        this.setAllowNegativeValues(
                  array.getBoolean(R.styleable.CurrencyEditText_allow_negative_values, false));
        this.setDecimalDigits(
                  array.getInteger(R.styleable.CurrencyEditText_decimal_digits, decimalDigits));
        
        array.recycle();
    }
    
    private void updateHint() {
        if (hintCache == null) {
            setHint(getDefaultHintValue());
        }
    }
    
    private String getDefaultHintValue() {
        try {
            return Currency.getInstance(currentLocale).getSymbol();
        } catch (Exception e) {
            try {
                return Currency.getInstance(defaultLocale).getSymbol();
            } catch (Exception e1) {
                return Currency.getInstance(Locale.US).getSymbol();
            }
            
        }
    }
    
    private Locale retrieveLocale() {
        Locale locale;
        try {
            locale = getResources().getConfiguration().locale;
        } catch (Exception e) {
            locale = defaultLocale;
        }
        return locale;
    }
    
    private Currency getCurrencyForLocale(Locale locale) {
        Currency currency;
        try {
            currency = Currency.getInstance(locale);
        } catch (Exception e) {
            try {
               
                currency = Currency.getInstance(defaultLocale);
            } catch (Exception e1) {
                currency = Currency.getInstance(Locale.US);
            }
        }
        return currency;
    }
    
    protected void setRawValue(BigInteger value) {
        rawValue = value;
    }
}
