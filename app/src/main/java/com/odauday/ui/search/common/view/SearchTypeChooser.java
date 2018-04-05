package com.odauday.ui.search.common.view;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.ui.search.common.SearchType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 4/1/18.
 */

public class SearchTypeChooser extends LinearLayout {
    
    private Spinner mSpinner;
    private TextView mTextView;
    private OnSelectedSearchType mListener;
    
    public SearchTypeChooser(Context context) {
        super(context);
        init(context);
    }
    
    public SearchTypeChooser(Context context,
              @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public SearchTypeChooser(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_search_type_chooser, this, true);
        
        mSpinner = rootView.findViewById(R.id.spinner);
        mTextView = rootView.findViewById(R.id.txt_title);
        
        setupSpinner(context);
    }
    
    private void setupSpinner(Context context) {
        List<String> searchTypes = getSearchTypes(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                  android.R.layout.simple_spinner_item,
                  searchTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                if (mListener != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onSelectedSearchType(SearchType.getByValue(i));
                        }
                    }, 10);
                }
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            
            }
        });
        mSpinner.setSelection(SearchType.ALL.getValue());
    }
    
    private List<String> getSearchTypes(Context context) {
        List<String> data = new ArrayList<>();
        
        for (SearchType searchType : SearchType.values()) {
            String type = context.getString(searchType.getResourceId());
            data.add(type);
        }
        
        return data;
    }
    
    public Spinner getSpinner() {
        return mSpinner;
    }
    
    public void setTitle(String title) {
        mTextView.setText(title);
    }
    
    public void setListener(
              OnSelectedSearchType listener) {
        mListener = listener;
    }
    
    public void reset() {
    
    }
    
    public interface OnSelectedSearchType {
        
        void onSelectedSearchType(SearchType searchType);
    }
}
