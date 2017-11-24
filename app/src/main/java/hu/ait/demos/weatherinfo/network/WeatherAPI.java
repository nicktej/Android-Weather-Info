package hu.ait.demos.weatherinfo.network;

import hu.ait.demos.weatherinfo.data.WeatherResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nicktan on 11/13/17.
 */

public interface WeatherAPI {

    @GET("weather")
    Call<WeatherResult> getCityByID(@Query("id") String city_id, @Query("appid") String app_id);

    @GET("weather")
    Call<WeatherResult> getCityByName(@Query("q") String city_name, @Query("units") String units, @Query("appid") String app_id);

}
