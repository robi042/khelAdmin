package code.fortomorrow.kheloNowAdmin.API;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConfig {
    //private static String baseUrl = "http://khelo.ap-south-1.elasticbeanstalk.com/api/";
    private static String baseUrl = "http://192.168.10.111:3001/api/";
    public static OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build();
    public static Retrofit getRetrofit() {

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
