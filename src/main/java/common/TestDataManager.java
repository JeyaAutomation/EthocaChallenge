package common;

import java.util.LinkedHashMap;
import java.util.Set;

import com.google.gson.annotations.Expose;

public class TestDataManager {
	// @Expose(serialize = false)
	@Expose
	private String name;
	@Expose
	private String description;
	@Expose
	private LinkedHashMap<String, LinkedHashMap<String, FieldData>> data;

	public TestDataManager(String name, String description,
			LinkedHashMap<String, LinkedHashMap<String, FieldData>> data) {
		this.name = name;
		this.description = description;
		this.data = data;

	}

	@SuppressWarnings("rawtypes")
	public LinkedHashMap<String, FieldData> getData(Class clazz) throws Exception {
		String pageName = clazz.getName().toString();
		pageName = pageName.substring(pageName.lastIndexOf(".") + 1);
		return getData(pageName);
	}

	public LinkedHashMap<String, FieldData> getData(String pageName) throws Exception {

		if (data.containsKey(pageName)) {
			return data.get(pageName);
		} else {
			throw new Exception(String.format("No data found for the page", pageName));
		}
	}

	public Set<String> keySet() {
		return this.data.keySet();
	}

}
