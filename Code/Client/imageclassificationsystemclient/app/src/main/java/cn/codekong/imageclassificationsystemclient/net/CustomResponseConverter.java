package cn.codekong.imageclassificationsystemclient.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import cn.codekong.imageclassificationsystemclient.bean.HttpResult;
import okhttp3.ResponseBody;
import retrofit2.Converter;

final class CustomResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private TypeAdapter<T> adapter;
    private Type mType;

    CustomResponseConverter(Gson gson, TypeAdapter<T> mAdapter, Type mType) {
        this.gson = gson;
        this.adapter = mAdapter;
        this.mType = mType;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        HttpResult mResponse = new HttpResult();
        try {
            String body = value.string();
            JSONObject json = new JSONObject(body);
            String code = json.getString("code");
            String msg = json.getString("msg");
            //返回数据成功
            if (code.equals("200")) {
                return gson.fromJson(body, mType);
            } else {
                return (T) mResponse.setData(json.opt("data")).setMsg(msg).setCode(code);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }

    }
}