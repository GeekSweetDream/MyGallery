package com.dreamsofpines.mygallery.model.deserializer;

import com.dreamsofpines.mygallery.model.Answer;
import com.dreamsofpines.mygallery.model.LentaImageItem;
import com.dreamsofpines.mygallery.ui.interfaces.mItemView;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AnswerDeserializer implements JsonDeserializer<Answer> {

    @Override
    public Answer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsObject = json.getAsJsonObject();

        JsonArray items = jsObject.getAsJsonArray("items");
        int offset = jsObject.get("offset").getAsInt();
        List<mItemView> list = new ArrayList<>();
        for(int i = 0; i < items.size();++i){
            list.add(new LentaImageItem()
                    .setPath(getStringFromJsonElement(items.get(i).getAsJsonObject().get("file")))
                    .setPreview(getStringFromJsonElement(items.get(i).getAsJsonObject().get("preview")))
            );
        }
        return new Answer().setList(list).setOffset(offset);
    }

    private static String getStringFromJsonElement(JsonElement element){
        return element != null ? element.getAsString() :null;
    }

}
