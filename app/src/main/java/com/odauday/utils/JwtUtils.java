package com.odauday.utils;

import android.util.Base64;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/23/18.
 */

public class JwtUtils {
    
    
    public static JwtModel decoded(String JWTEncoded)
        throws Exception {
        String[] split = JWTEncoded.split("[.]");
        
        String header = getJson(split[0]);
        String body = getJson(split[1]);
        
        Timber.tag("JWT_DECODED").d("Header: %s", header);
        Timber.tag("JWT_DECODED").d("Body: %s", body);
        
        return new JwtModel(header, body);
    }
    
    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
    
    public static <T> T parseBody(JwtModel jwtModel, Class<T> clazz) {
        return parseBody(jwtModel.getBody(), clazz);
    }
    
    public static <T> T parseBody(String body, Class<T> clazz) {
        return new Gson().fromJson(body, clazz);
    }
    
    public static <T> String toJson(Object obj, Class<T> clazz) {
        return new Gson().toJson(obj, clazz);
    }
    
    public static class JwtModel {
        
        private String header;
        private String body;
        
        public JwtModel(String header, String body) {
            this.header = header;
            this.body = body;
        }
        
        public String getHeader() {
            return header;
        }
        
        public void setHeader(String header) {
            this.header = header;
        }
        
        public String getBody() {
            return body;
        }
        
        public void setBody(String body) {
            this.body = body;
        }
        
        @Override
        public String toString() {
            return "JWT{" +
                   "header='" + header + '\'' +
                   ", body='" + body + '\'' +
                   '}';
        }
    }
    
    
}
