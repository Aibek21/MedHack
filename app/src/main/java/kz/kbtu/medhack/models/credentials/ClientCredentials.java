package kz.kbtu.medhack.models.credentials;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import kz.kbtu.medhack.models.User;

/**
 * Created by aibekkuralbaev on 28.05.17.
 */

public class ClientCredentials {

    @SerializedName("client")
    @Expose
    private User client;

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }
}
