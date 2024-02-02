package com.booking.com.base;

import java.io.FileReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonDataConvertor {

	JsonObject jsonObject;
	
	public JsonObject getJson(String filePath) {
		try {	
			FileReader reader = new FileReader(System.getProperty("user.dir") + filePath);
			jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
