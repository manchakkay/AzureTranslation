package com.manchakkay.azuretranslation.translator;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RetrofitAPI {
    String API_URL = "https://api.cognitive.microsofttranslator.com";
    String key = "";
    String region = "";
    String POST_URL = "/translate?api-version=3.0&to=";

    @GET("/languages?api-version=3.0&scope=translation")
    Call<GSON_Languages> get_languages();

    @POST()
    @Headers({
            "Content-Type: application/json",
            "Ocp-Apim-Subscription-Key: " + key,
            "Ocp-Apim-Subscription-Region: " + region
    })

    Call<GSON_Output[]> get_translation(@Url String URL, @Body GSON_Input[] body);
}
