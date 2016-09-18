package hackthenorth.neighborcater.networking;


import android.util.Base64;

import hackthenorth.neighborcater.models.XEResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface FoodService {

        @GET("convert_from.json/")
        Call<XEResponse> getXETest(@Query("to") String to, @Query("from") String from, @Query("amount") String amount);
}
