package kz.kbtu.medhack.api;


import java.util.List;

import kz.kbtu.medhack.models.Order;
import kz.kbtu.medhack.models.User;
import kz.kbtu.medhack.models.credentials.ClientCredentials;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by aibekkuralbaev on 05.10.16.
 */

public interface Api {


//    @FormUrlEncoded
    @POST("api/clients/login")
    Observable<User> doLogin(@Body ClientCredentials clientCredentials);

    @GET("api/requests/client/all")
    Observable<List<Order>> getOrders();
}
