package pos.com.pos.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HJ Chin on 29/12/2017.
 */

public class HttpClient implements HttpClientInterface{

    private static HttpClient httpClient;
    private Retrofit retrofit;
    private String baseUrl = "https://jsonplaceholder.typicode.com/";

    private HttpClient(){
    }

    public static HttpClient getInstance(){
        if(httpClient == null){
            httpClient = new HttpClient();
        }
        return httpClient;
    }

    public Retrofit getRetrofit(){
        if(retrofit == null){

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.addInterceptor(logging);

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClientBuilder.build())
                    .build();
        }

        return retrofit;
    }

    public POSApi getPOSApi(){
        return getRetrofit().create(POSApi.class);
    }
}
