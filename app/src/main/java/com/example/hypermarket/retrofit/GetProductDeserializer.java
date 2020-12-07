package com.example.hypermarket.retrofit;

import com.example.hypermarket.model.Product;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetProductDeserializer implements JsonDeserializer<List<Product>> {
    @Override
    public List<Product> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Product> items = new ArrayList<>();
        JsonObject bodyObject = json.getAsJsonObject();
        JsonArray productArray = bodyObject.getAsJsonArray();

        for (int i = 0; i < productArray.size(); i++) {
            JsonObject productObject = productArray.get(i).getAsJsonObject();

            if (!productObject.has("permalink"))
                continue;

            String id = productObject.get("id").getAsString();
            String url = productObject.get("permalink").getAsString();
            String title = productObject.get("name").getAsString();
            String price = productObject.get("price").getAsString();
            Product item = new Product(title, price, url);
            items.add(item);
        }
        return items;
    }
}
