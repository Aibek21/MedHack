package kz.kbtu.medhack.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aibekkuralbaev on 14.11.16.
 */

public class BaseResponse<T> {
    @SerializedName("success") public Boolean success;
    @SerializedName("error") public Boolean error;
    @SerializedName("message") public String message;
    @SerializedName("data") public T data;
}
