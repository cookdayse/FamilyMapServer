package Handlers;

import com.google.gson.Gson;

public class JsonSerializer {
    public <T> T deserialize(String value, Class<T> returnType) {
        return (new Gson()).fromJson(value, returnType);
    }
    public String serialize(Object obj) {
        return (new Gson()).toJson(obj);
    }
}
