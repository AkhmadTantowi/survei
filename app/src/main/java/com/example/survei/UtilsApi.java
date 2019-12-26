package com.example.survei;

public class UtilsApi {
    public static final String BASE_URL_API = "https://survey-apps-server.herokuapp.com/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient().create(BaseApiService.class);
    }
}
