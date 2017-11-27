package kz.kbtu.medhack.api;

import java.util.List;

import kz.kbtu.medhack.models.Order;
import kz.kbtu.medhack.models.User;
import kz.kbtu.medhack.models.credentials.ClientCredentials;
import rx.Observable;

/**
 * Created by aibekkuralbaev on 05.10.16.
 */

public class RequestHandler implements Request {

    Api service;


    public RequestHandler(Api service) {
        this.service = service;
    }



    @Override
    public Observable<User> doLogin(ClientCredentials clientCredentials) {
        return service.doLogin(clientCredentials);
    }

    @Override
    public Observable<List<Order>> getOrders() {
        return service.getOrders();
    }
}
