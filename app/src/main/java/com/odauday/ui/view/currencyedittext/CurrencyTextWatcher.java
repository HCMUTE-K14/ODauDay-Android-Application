package com.odauday.ui.view.currencyedittext;

import android.text.Editable;
import android.text.TextWatcher;
import java.math.BigInteger;

/**
 * Created by infamouSs on 4/24/18.
 */
class CurrencyTextWatcher implements TextWatcher {
    
    private CurrencyEditText editText;
    
    private boolean ignoreIteration;
    private String lastGoodInput;
    CurrencyTextWatcher(CurrencyEditText textBox) {
        editText = textBox;
        lastGoodInput = "";
        ignoreIteration = false;
    }
    
   
    @Override
    public void afterTextChanged(Editable editable) {
        if (!ignoreIteration) {
            ignoreIteration = true;
            String newText = editable.toString();
            
            if (newText.equals(" VND")) {
                lastGoodInput = "";
                editText.setRawValue(BigInteger.valueOf(0));
                editText.setText("");
                return;
            }
            
            String textToDisplay;
            
            if (newText.length() < 1) {
                lastGoodInput = "";
                editText.setRawValue(BigInteger.valueOf(0));
                editText.setText("");
                return;
            }
            
            newText = (editText.areNegativeValuesAllowed()) ? newText.replaceAll("[^0-9/-]", "")
                      : newText.replaceAll("[^0-9]", "");
            
            if (!newText.equals("") && !newText.equals("-")) {
                editText.setRawValue(new BigInteger(newText));
            }
            try {
                
                textToDisplay = CurrencyTextFormatter
                                          .formatText(newText, editText.getLocale(),
                                                    editText.getDefaultLocale(),
                                                    editText.getDecimalDigits()) + " VND";
            } catch (IllegalArgumentException exception) {
                textToDisplay = lastGoodInput;
            }
            
            editText.setText(textToDisplay);
            lastGoodInput = textToDisplay;
            
            String currentText = editText.getText().toString();
            int cursorPosition = indexOfLastDigit(currentText) + 1;
            
            if (currentText.length() >= cursorPosition) {
                editText.setSelection(cursorPosition);
            }
            
        } else {
            ignoreIteration = false;
        }
        
    }
    private int indexOfLastDigit(String str) {
        int result = 0;
        
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                result = i;
            }
        }
        
        return result;
    }
    
    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
    }
    
    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
    }
}
