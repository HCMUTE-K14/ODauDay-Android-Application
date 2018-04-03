package com.odauday.api;

import javax.inject.Inject;
import okhttp3.Headers;

/**
 * Created by infamouSs on 2/28/18.
 */

public class APIHeader {

    private final ProtectApiHeader mProtectApiHeader;

    private final PublicApiHeader mPublicApiHeader;

    @Inject
    public APIHeader(ProtectApiHeader protectApiHeader, PublicApiHeader publicApiHeader) {
        this.mProtectApiHeader = protectApiHeader;
        this.mPublicApiHeader = publicApiHeader;
    }


    public ProtectApiHeader getProtectApiHeader() {
        return mProtectApiHeader;
    }


    public PublicApiHeader getPublicApiHeader() {
        return mPublicApiHeader;
    }


    @Override
    public String toString() {
        return "APIHeader{" +
            "mProtectApiHeader=" + mProtectApiHeader +
            ", mPublicApiHeader=" + mPublicApiHeader +
            '}';
    }

    public static class ProtectApiHeader extends AbstractApiHeader {

        private String access_token;
        private String api_key;
        private String user_id;

        private Headers header;

        public ProtectApiHeader(String access_token, String api_key, String user_id) {
            this.access_token = access_token;
            this.api_key = api_key;
            this.user_id = user_id;
        }


        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getApi_key() {
            return api_key;
        }

        public void setApi_key(String api_key) {
            this.api_key = api_key;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public Headers getHeader() {

            if (header != null) {
                return header;
            }

            Headers.Builder builder = new Headers.Builder();

            builder.add(APIHelper.ACCESS_TOKEN, access_token);
            builder.add(APIHelper.API_KEY, api_key);
            builder.add(APIHelper.USER_ID, user_id);

            header = builder.build();

            return header;
        }

        public void setHeader(Headers header) {
            this.header = header;
        }

        @Override
        public String toString() {
            return "ProtectApiHeader{" +
                "access_token='" + access_token + '\'' +
                ", api_key='" + api_key + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
        }
    }

    public static class PublicApiHeader extends AbstractApiHeader {

        private String api_key;

        private Headers header;

        public PublicApiHeader(String api_key) {
            this.api_key = api_key;
        }

        public String getApi_key() {
            return api_key;
        }

        public void setApi_key(String api_key) {
            this.api_key = api_key;
        }

        public Headers getHeader() {
            if (header != null) {
                return header;
            }

            Headers.Builder builder = new Headers.Builder();

            builder.add(APIHelper.API_KEY, api_key);

            header = builder.build();

            return header;
        }

        public void setHeader(Headers header) {
            this.header = header;
        }

        @Override
        public String toString() {
            return "PublicApiHeader{" +
                "api_key='" + api_key + '\'' +
                '}';
        }
    }
}
