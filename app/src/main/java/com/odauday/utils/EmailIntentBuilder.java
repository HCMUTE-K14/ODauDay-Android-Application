package com.odauday.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Patterns;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by infamouSs on 5/18/18.
 */
public class EmailIntentBuilder {
    
    private final Context context;
    private final Set<String> to = new LinkedHashSet<>();
    private final Set<String> cc = new LinkedHashSet<>();
    private final Set<String> bcc = new LinkedHashSet<>();
    private String subject;
    private String body;
    
    
    private EmailIntentBuilder(@NonNull Context context) {
        this.context = checkNotNull(context);
    }
    

    public static EmailIntentBuilder from(@NonNull Context context) {
        return new EmailIntentBuilder(context);
    }
    
    public EmailIntentBuilder to(@NonNull String to) {
        checkEmail(to);
        this.to.add(to);
        return this;
    }
    
    public EmailIntentBuilder to(@NonNull Collection<String> to) {
        checkNotNull(to);
        for (String email : to) {
            checkEmail(email);
        }
        this.to.addAll(to);
        
        return this;
    }
    
    public EmailIntentBuilder cc(@NonNull String cc) {
        checkEmail(cc);
        this.cc.add(cc);
        return this;
    }
    
    public EmailIntentBuilder cc(@NonNull Collection<String> cc) {
        checkNotNull(cc);
        for (String email : cc) {
            checkEmail(email);
        }
        this.cc.addAll(cc);
        
        return this;
    }
    
    public EmailIntentBuilder bcc(@NonNull String bcc) {
        checkEmail(bcc);
        this.bcc.add(bcc);
        return this;
    }
    
    public EmailIntentBuilder bcc(@NonNull Collection<String> bcc) {
        checkNotNull(bcc);
        for (String email : bcc) {
            checkEmail(email);
        }
        this.bcc.addAll(bcc);
        
        return this;
    }
    
    public EmailIntentBuilder subject(@NonNull String subject) {
        checkNotNull(subject);
        checkNoLineBreaks(subject);
        this.subject = subject;
        return this;
    }
    

    public EmailIntentBuilder body(@NonNull String body) {
        checkNotNull(body);
        this.body = fixLineBreaks(body);
        return this;
    }
    
    public boolean start() {
        Intent emailIntent = build();
        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            return false;
        }
        
        return true;
    }
    
    private void startActivity(Intent intent) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        
        context.startActivity(intent);
    }
    

    public Intent build() {
        Uri mailtoUri = constructMailtoUri();
        return new Intent(Intent.ACTION_SENDTO, mailtoUri);
    }
    
    private Uri constructMailtoUri() {
        StringBuilder mailto = new StringBuilder(1024);
        mailto.append("mailto:");
        addRecipients(mailto, to);
        
        boolean hasQueryParameters;
        hasQueryParameters = addRecipientQueryParameters(mailto, "cc", cc, false);
        hasQueryParameters = addRecipientQueryParameters(mailto, "bcc", bcc, hasQueryParameters);
        hasQueryParameters = addQueryParameter(mailto, "subject", subject, hasQueryParameters);
        addQueryParameter(mailto, "body", body, hasQueryParameters);
        
        return Uri.parse(mailto.toString());
    }
    
    private boolean addQueryParameter(StringBuilder mailto, String field, String value,
        boolean hasQueryParameters) {
        if (value == null) {
            return hasQueryParameters;
        }
        
        mailto.append(hasQueryParameters ? '&' : '?').append(field).append('=')
            .append(Uri.encode(value));
        
        return true;
    }
    
    private boolean addRecipientQueryParameters(StringBuilder mailto, String field,
        Set<String> recipients,
        boolean hasQueryParameters) {
        if (recipients.isEmpty()) {
            return hasQueryParameters;
        }
        
        mailto.append(hasQueryParameters ? '&' : '?').append(field).append('=');
        addRecipients(mailto, recipients);
        
        return true;
    }
    
    private void addRecipients(StringBuilder mailto, Set<String> recipients) {
        if (recipients.isEmpty()) {
            return;
        }
        
        for (String recipient : recipients) {
            mailto.append(encodeRecipient(recipient));
            mailto.append(',');
        }
        
        mailto.setLength(mailto.length() - 1);
    }
    
    private void checkEmail(String email) {
        checkNotNull(email);
        
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new IllegalArgumentException(
                "Argument is not a valid email address (according to " +
                "Patterns.EMAIL_ADDRESS)");
        }
    }
    
    private void checkNoLineBreaks(String text) {
        boolean containsCarriageReturn = text.indexOf('\r') != -1;
        boolean containsLineFeed = text.indexOf('\n') != -1;
        
        if (containsCarriageReturn || containsLineFeed) {
            throw new IllegalArgumentException("Argument must not contain line breaks");
        }
    }
    
    private static <T> T checkNotNull(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
        
        return object;
    }
    
    static String encodeRecipient(String recipient) {
        int index = recipient.lastIndexOf('@');
        String localPart = recipient.substring(0, index);
        String host = recipient.substring(index + 1);
        return Uri.encode(localPart) + "@" + Uri.encode(host);
    }
    
    static String fixLineBreaks(String text) {
        return text.replaceAll("\r\n", "\n").replace('\r', '\n').replaceAll("\n", "\r\n");
    }
}
