package kz.kbtu.medhack.api;

import java.util.List;

import kz.kbtu.medhack.models.Order;
import kz.kbtu.medhack.models.User;
import kz.kbtu.medhack.models.credentials.ClientCredentials;
import rx.Observable;

/**
 * Created by aibekkuralbaev on 05.10.16.
 */

public interface Request {

    Observable<User> doLogin(ClientCredentials clientCredentials);
    Observable<List<Order>> getOrders();


}
