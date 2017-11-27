package kz.kbtu.medhack.utils.factories;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import kz.kbtu.medhack.models.BaseResponse;
import kz.kbtu.medhack.utils.exceptions.BaseResponseException;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by aibekkuralbaev on 14.11.16.
 */

public class ResponseConverterFactory extends Converter.Factory {


    private ResponseConverterFactory() {
        // Private constructor.
    }

    public static ResponseConverterFactory create() {
        return new ResponseConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations,
                                                            Retrofit retrofit) {

        Type rpcType = Types.newParameterizedType(BaseResponse.class, type);
        Converter<ResponseBody, BaseResponse> delegate =
                retrofit.nextResponseBodyConverter(this, rpcType, annotations);
        //noinspection unchecked
        return new ResponseBodyConverter(delegate);
    }

    static class ResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        final Converter<ResponseBody, BaseResponse<T>> delegate;


        ResponseBodyConverter(Converter<ResponseBody, BaseResponse<T>> delegate) {
            this.delegate = delegate;
        }

        @Override
        public T convert(ResponseBody responseBody) throws IOException {
            BaseResponse<T> response = delegate.convert(responseBody);


            if (response.error!=null && response.error)
                throw new BaseResponseException(response.message);

            return response.data;

        }
    }


}
