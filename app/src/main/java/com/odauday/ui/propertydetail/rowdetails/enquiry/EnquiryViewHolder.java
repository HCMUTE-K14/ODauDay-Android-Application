package com.odauday.ui.propertydetail.rowdetails.enquiry;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.model.MyEmail;
import com.odauday.ui.propertydetail.rowdetails.BaseRowViewHolder;
import com.odauday.ui.propertydetail.rowdetails.enquiry.ChoiceEmailDialog.OnSelectedEmail;
import com.odauday.utils.EmailIntentBuilder;
import com.odauday.utils.TextUtils;
import com.odauday.utils.ValidationHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 5/17/18.
 */
public class EnquiryViewHolder extends BaseRowViewHolder<EnquiryDetailRow> {
    
    TextView mStarterText;
    
    TextInputLayout mTextInputName;
    TextInputLayout mTextInputPhone;
    TextInputLayout mTextInputFromEmail;
    
    EditText mName;
    EditText mPhone;
    EditText mToEmail;
    EditText mFromEmail;
    EditText mMessage;
    
    Button mButtonSendMail;
    
    List<String> mToEmailList = new ArrayList<>();
    
    public EnquiryViewHolder(View itemView) {
        super(itemView);
        this.mStarterText = itemView.findViewById(R.id.enquiry_form_starter_text);
        this.mTextInputName = itemView.findViewById(R.id.text_input_name);
        this.mTextInputPhone = itemView.findViewById(R.id.text_input_phone);
        this.mTextInputFromEmail = itemView.findViewById(R.id.text_input_from_email);
        
        this.mName = itemView.findViewById(R.id.name);
        this.mPhone = itemView.findViewById(R.id.phone);
        this.mToEmail = itemView.findViewById(R.id.to_email);
        this.mToEmail.setOnClickListener(this::onClickToChoiceToEmail);
        this.mFromEmail = itemView.findViewById(R.id.from_email);
        this.mMessage = itemView.findViewById(R.id.message);
        
        this.mButtonSendMail = itemView.findViewById(R.id.send_email);
        this.mButtonSendMail.setOnClickListener(this::onClickSendMail);
    }
    
    
    @Override
    protected void update(EnquiryDetailRow enquiryDetailRow) {
        super.update(enquiryDetailRow);
        if (enquiryDetailRow.getData() == null) {
            return;
        }
        String address = TextUtils.formatAddress(enquiryDetailRow.getData().getAddress());
        String starterText = itemView.getContext()
            .getString(R.string.txt_enquiry_starter_text, address);
        
        mStarterText.setText(starterText);
    }
    
    private boolean[] mCheckedEmails;
    
    public void onClickToChoiceToEmail(View view) {
        List<MyEmail> listEmailFromData = getRow().getData().getEmails();
        if (listEmailFromData != null && !listEmailFromData.isEmpty()) {
            ChoiceEmailDialog choiceEmailDialog = new ChoiceEmailDialog(itemView.getContext(),
                mCheckedEmails,
                listEmailFromData,
                new OnSelectedEmail() {
                    @Override
                    public void onSelectedEmail(List<String> emails) {
                        mToEmailList = emails;
                        mToEmail.setText(toStringEmails(emails));
                        mCheckedEmails = makeCheckedEmails(emails);
                    }
                    
                    @Override
                    public void onClearAll() {
                        mToEmailList.clear();
                        mToEmail.setText("");
                    }
                }
            );
            
            choiceEmailDialog.show();
        } else {
            Toast.makeText(itemView.getContext(), R.string.message_email_list_is_empty,
                Toast.LENGTH_SHORT).show();
        }
        
    }
    
    private boolean[] makeCheckedEmails(List<String> selectedEmails) {
        boolean[] checked = new boolean[selectedEmails.size()];
        if (getRow().getData() != null && getRow().getData().getEmails() != null) {
            for (int i = 0; i < selectedEmails.size(); i++) {
                if (isContainsInListEmail(selectedEmails.get(i))) {
                    checked[i] = true;
                } else {
                    checked[i] = false;
                }
            }
        }
        return checked;
    }
    
    private boolean isContainsInListEmail(String email) {
        return getRow().getData().getEmails().contains(new MyEmail(email));
    }
    
    public void onClickSendMail(View view) {
        String name = mName.getText().toString();
        String phone = mPhone.getText().toString();
        String toEmail = mToEmail.getText().toString();
        String fromEmail = mFromEmail.getText().toString();
        
        String message = mMessage.getText().toString();
        
        boolean isValidForm = validate(name, phone, toEmail, fromEmail);
        if (isValidForm) {
            EmailIntentBuilder.from(itemView.getContext())
                .to(mToEmailList)
                .cc(fromEmail)
                .subject(buildSubject(name, phone, message))
                .start();
        }
    }
    
    private String buildSubject(String name, String phone, String message) {
        StringBuilder builder = new StringBuilder();
        builder.append("Hi").append("Name: ").append(name)
            .append(" Phone: ").append(phone).append(message);
        
        return builder.toString();
    }
    
    private boolean validate(String name, String phone, String toEmail, String fromEmail) {
        mTextInputName.setErrorEnabled(false);
        mTextInputPhone.setErrorEnabled(false);
        mTextInputFromEmail.setErrorEnabled(false);
        
        if (ValidationHelper.isEmpty(name)) {
            mTextInputName.setError(getString(R.string.message_name_is_required));
            mName.requestFocus();
            return false;
        }
        
        if (ValidationHelper.isEmpty(phone)) {
            mTextInputPhone.setError(getString(R.string.message_phone_is_required));
            mPhone.requestFocus();
            return false;
        }
        
        if (ValidationHelper.isEmpty(toEmail)) {
            mToEmail.setError(getString(R.string.message_email_is_required));
            mToEmail.requestFocus();
            return false;
        }
        
        if (ValidationHelper.isEmpty(fromEmail)) {
            mTextInputFromEmail.setError(getString(R.string.message_email_is_required));
            mFromEmail.requestFocus();
            return false;
        }
        
        if (!ValidationHelper.isEmail(fromEmail)) {
            mTextInputFromEmail.setError(getString(R.string.message_email_is_invalid));
            mFromEmail.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private String getString(int resourceId) {
        return itemView.getContext().getString(resourceId);
    }
    
    private String toStringEmails(List<String> myEmails) {
        StringBuilder builder = new StringBuilder();
        
        for (String email : myEmails) {
            if (myEmails.indexOf(email) == myEmails.size() - 1) {
                builder.append(email);
                break;
            }
            builder
                .append(email)
                .append(", ");
        }
        
        return builder.toString();
    }
    
    @Override
    public void unbind() {
        super.unbind();
        mStarterText = null;
        mTextInputPhone = null;
        mTextInputName = null;
        mTextInputFromEmail = null;
        
        mPhone = null;
        mToEmail = null;
        mFromEmail = null;
        mButtonSendMail = null;
    }
}
