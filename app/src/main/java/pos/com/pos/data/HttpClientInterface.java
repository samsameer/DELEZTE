package pos.com.pos.data;

import retrofit2.Retrofit;

/**
 * Created by HJ Chin on 29/12/2017.
 */

public interface HttpClientInterface {

    Retrofit getRetrofit();
    POSApi getPOSApi();
}
