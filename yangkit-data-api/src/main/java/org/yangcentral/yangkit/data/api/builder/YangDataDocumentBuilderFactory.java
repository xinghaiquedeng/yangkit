package org.yangcentral.yangkit.data.api.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class YangDataDocumentBuilderFactory {
    public static YangDataDocumentBuilder getBuilder(){
        InputStream inputStream = YangDataDocumentBuilderFactory.class.getResourceAsStream("/builder.json");
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String result = s.hasNext()?s.next():"";
        JsonElement builderElement = JsonParser.parseString(result);
        JsonObject jsonObject = builderElement.getAsJsonObject();
        String builderClassStr = jsonObject.get("docbuilder").getAsString();
        try {
            Class<YangDataDocumentBuilder> builderClass = (Class<YangDataDocumentBuilder>) Class.forName(builderClassStr);
            return builderClass.getConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}