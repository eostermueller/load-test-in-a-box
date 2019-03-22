package com.github.eostermueller.havoc.workload.model.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class InterfaceAdaptor implements JsonSerializer, JsonDeserializer {

	 /****** Helper method to get the className of the object to be deserialized *****/
    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
                throw new JsonParseException(e.getMessage());
            }
    }
    private static final String CLASSNAME = "com.github.eostermueller.havoc.annotations.json.TestMethodWrapper";
    //private static final String CLASSNAME = "methodWrapper";
    private static final String DATA = "methodWrapper";
	@Override
	public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		  JsonObject jsonObject = json.getAsJsonObject();
	        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
	        String className = prim.getAsString();
	        Class klass = getObjectClass(className);
	            return context.deserialize(jsonObject.get(DATA), klass);
	}
	@Override
	public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, jsonObject.getClass().getName());
        jsonObject.add(DATA, context.serialize(jsonObject));
        return jsonObject;
	}
}
