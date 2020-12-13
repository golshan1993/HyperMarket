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
        JsonArray productArray = json.getAsJsonArray();

        for (int i = 0; i < productArray.size(); i++) {
            JsonObject productObject = productArray.get(i).getAsJsonObject();

            if (!productObject.has("permalink"))
                continue;

            String id = productObject.get("id").getAsString();
            String title = productObject.get("name").getAsString();
            String price = productObject.get("price").getAsString();
            String urlAddress = productObject.get("permalink").getAsString();
            JsonArray productImages = productObject.get("images").getAsJsonArray();
            String [] url = new String [productImages.size()];
            for (int j = 0; j < url.length; j++){
                JsonObject imageObject = productImages.get(j).getAsJsonObject();
                url[j] = imageObject.get("src").getAsString();
            }
            JsonArray productCategories = productObject.get("categories").getAsJsonArray();
            String [] category = new String [productCategories.size()];
            for (int j = 0; j < category.length; j++){
                JsonObject categoryObject = productCategories.get(j).getAsJsonObject();
                category[j] = categoryObject.get("name").getAsString();
            }
            Product item = new Product(title, price, urlAddress, category, url);
            items.add(item);
        }
        return items;
    }
}
