package common;


import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class JsonDataManager

{
	public static TestDataManager loadData(String filePath) throws FileNotFoundException, UnsupportedEncodingException {
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
		Gson gson = new Gson();
		TestDataManager data = (TestDataManager) gson.fromJson(reader, TestDataManager.class);
		return data;
	}

}

