package com.dk.core.common.utils.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.dk.core.common.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * @ClassName JsonHelper
 * @Description Json处理器
 */
public class JsonHelper {

	private static final long	serialVersionUID	= -3674498965562297865L;
	private static Logger		logger				= Logger.getLogger(JsonHelper.class);

	/**
	 * 将不含日期时间格式的Java对象序列化为Json资料格式
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2Json(Object pObject) {
		String jsonString = "[]";
		if (CommonUtils.isEmpty(pObject)) {
			logger.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			if (pObject instanceof ArrayList) {

				JSONArray jsonArray = JSONArray.fromObject(pObject);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject);
				jsonString = jsonObject.toString();
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 将含有日期时间格式的Java对象序列化为Json资料格式
	 * Json-Lib在处理日期时间格式是需要实现其JsonValueProcessor接口,所以在这里提供一个重载的方法对含有
	 * 日期时间格式的Java对象进行序列化
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2Json(Object pObject, String pFormatString) {
		String jsonString = "[]";
		if (CommonUtils.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			JsonConfig cfg = new JsonConfig();
			cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl(pFormatString));
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject, cfg);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject, cfg);
				jsonString = jsonObject.toString();
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 将数据序列化为表单数据填充所需的Json格式
	 * 
	 * @param pObject
	 *            待序列化的对象
	 * @param pFormatString
	 *            日期时间格式化,如果为null则认为没有日期时间型字段
	 * @return
	 */
	public static final String encodeObject2Json4Form(Object pObject, String pFormatString) {
		String jsonString = "";
		String sunJsonString = "";
		if (CommonUtils.isEmpty(pFormatString)) {
			sunJsonString = encodeObject2Json(pObject);
		} else {
			sunJsonString = encodeObject2Json(pObject, pFormatString);
		}
		if (logger.isInfoEnabled()) {
			logger.info("序列化后的JSON资料输出:\n" + sunJsonString);
		}
		jsonString = "{success:" + (!CommonUtils.isEmpty(pObject) ? "true" : "false") + ",data:" + sunJsonString + "}";
		return jsonString;
	}

	/**
	 * 将分页信息压入JSON字符串 此类内部使用,不对外暴露
	 * 
	 * @param JSON字符串
	 * @param totalCount
	 * @return 返回合并后的字符串
	 */
	private static String encodeJson2PageJson(String jsonString, Integer totalCount) {
		jsonString = "{totalProperty:" + totalCount + ", root:" + jsonString + "}";
		if (logger.isInfoEnabled()) {
			logger.info("合并后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 直接将List转为分页所需要的Json资料格式
	 * 
	 * @param list
	 *            需要编码的List对象
	 * @param totalCount
	 *            记录总数
	 * @param pDataFormat
	 *            时间日期格式化,传null则表明List不包含日期时间属性
	 */
	public static final String encodeList2PageJson(List<?> list, Integer totalCount, String dataFormat) {
		String subJsonString = "";
		if (CommonUtils.isEmpty(dataFormat)) {
			subJsonString = encodeObject2Json(list);
		} else {
			subJsonString = encodeObject2Json(list, dataFormat);
		}
		String jsonString = "{totalProperty:" + totalCount + ", root:" + subJsonString + "}";
		return jsonString;
	}

	/***************************************************************************
	 * 将List对象序列化为JSON文本
	 */
	public static <T> String toJSONString(List<T> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}

	/***************************************************************************
	 * 将对象序列化为JSON文本
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return jsonArray.toString();
	}

	/***************************************************************************
	 * 将JSON对象数组序列化为JSON文本
	 * 
	 * @param jsonArray
	 * @return
	 */
	public static String toJSONString(JSONArray jsonArray) {
		return jsonArray.toString();
	}

	/***************************************************************************
	 * 将JSON对象序列化为JSON文本
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static String toJSONString(JSONObject jsonObject) {
		return jsonObject.toString();
	}

	/***************************************************************************
	 * 将对象转换为List对象
	 * 
	 * @param object
	 * @return
	 */

	public static List<Object> toArrayList(Object object) {
		List<Object> arrayList = new ArrayList<Object>();
		JSONArray jsonArray = JSONArray.fromObject(object);
		Iterator it = jsonArray.iterator();
		while (it.hasNext()) {
			JSONObject jsonObject = (JSONObject) it.next();
			Iterator keys = jsonObject.keys();
			while (keys.hasNext()) {
				Object key = keys.next();
				Object value = jsonObject.get(key);
				arrayList.add(value);
			}
		}
		return arrayList;
	}

	/***************************************************************************
	 * 将对象转换为HashMap
	 * 
	 * @param object
	 * @return
	 */
	public static HashMap toHashMap(Object object) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		JSONObject jsonObject = toJSONObject(object);
		Iterator it = jsonObject.keys();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			Object value = jsonObject.get(key);
			data.put(key, value);
		}
		return data;
	}

	/***************************************************************************
	 * 将对象转换为List<Map<String,Object>>
	 * 
	 * @param object
	 * @return
	 */

	// 返回非实体类型(Map<String,Object>)的List
	public static List<Map<String, Object>> toList(Object object) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray jsonArray = JSONArray.fromObject(object);
		for (Object obj : jsonArray) {
			JSONObject jsonObject = (JSONObject) obj;
			Map<String, Object> map = new HashMap<String, Object>();
			Iterator it = jsonObject.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = jsonObject.get(key);
				map.put((String) key, value);
			}
			list.add(map);
		}
		return list;
	}

	/***************************************************************************
	 * 将对象转换为Collection对象
	 * 
	 * @param object
	 * @return
	 */
	public static Collection toCollection(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return JSONArray.toCollection(jsonArray);
	}

	/***************************************************************************
	 * 将对象转换为JSON对象数组
	 * 
	 * @param object
	 * @return
	 */
	public static JSONArray toJSONArray(Object object) {
		return JSONArray.fromObject(object);
	}

	/***************************************************************************
	 * 将对象转换为JSON对象
	 * 
	 * @param object
	 * @return
	 */
	public static JSONObject toJSONObject(Object object) {
		return JSONObject.fromObject(object);
	}

	/***************************************************************************
	 * 将JSON对象数组转换为传入类型的List
	 * 
	 * @param <T>
	 * @param jsonArray
	 * @param objectClass
	 * @return
	 */
	public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass) {
		return JSONArray.toList(jsonArray, objectClass);
	}

	/***************************************************************************
	 * 将对象转换为传入类型的List
	 * 
	 * @param <T>
	 * @param jsonArray
	 * @param objectClass
	 * @return
	 */
	public static <T> List<T> toList(Object object, Class<T> objectClass) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return JSONArray.toList(jsonArray, objectClass);
	}
	
	/***************************************************************************
	 * 将对象转换为传入类型的List
	 * 
	 * @param <T>
	 * @param jsonArray
	 * @param objectClass
	 * @return
	 */
	public static <T> List<T> toList(String jsonStr, Class<T> objectClass) {
		JSONObject object = JSONObject.fromObject(jsonStr); 
		JSONArray jsonArray = JSONArray.fromObject(object);
		return JSONArray.toList(jsonArray, objectClass);
	}

	/***************************************************************************
	 * 将JSON对象转换为传入类型的对象
	 * 
	 * @param <T>
	 * @param jsonObject
	 * @param beanClass
	 * @return
	 */
	public static <T> T toBean(JSONObject jsonObject, Class<T> beanClass) {
		return (T) JSONObject.toBean(jsonObject, beanClass);
	}
	
	/***************************************************************************
	 * 将JSON字符串转换为传入类型的对象
	 * 
	 * @param <T>
	 * @param jsonStr
	 * @param beanClass
	 * @return
	 */
	public static <T> T toBean(String jsonStr, Class<T> beanClass) {
		JSONObject jsonBean = JSONObject.fromObject(jsonStr); 
		return (T) JSONObject.toBean(jsonBean, beanClass);
	}

	/***************************************************************************
	 * 将将对象转换为传入类型的对象
	 * 
	 * @param <T>
	 * @param object
	 * @param beanClass
	 * @return
	 */
	public static <T> T toBean(Object object, Class<T> beanClass) {
		JSONObject jsonObject = JSONObject.fromObject(object);
		return (T) JSONObject.toBean(jsonObject, beanClass);
	}

	/***************************************************************************
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>
	 *            泛型T 代表主实体类型
	 * @param <D>
	 *            泛型D 代表从实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailName
	 *            从实体类在主实体类中的属性名称
	 * @param detailClass
	 *            从实体类型
	 */
	public static <T, D> T toBean(String jsonString, Class<T> mainClass, String detailName, Class<D> detailClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray = (JSONArray) jsonObject.get(detailName);
		T mainEntity = toBean(jsonObject, mainClass);
		List<D> detailList = toList(jsonArray, detailClass);
		try {
			BeanUtils.setProperty(mainEntity, detailName, detailList);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}
		return mainEntity;
	}

	/***************************************************************************
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>泛型T
	 *            代表主实体类型
	 * @param <D1>泛型D1
	 *            代表从实体类型
	 * @param <D2>泛型D2
	 *            代表从实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailName1
	 *            从实体类在主实体类中的属性
	 * @param detailClass1
	 *            从实体类型
	 * @param detailName2
	 *            从实体类在主实体类中的属性
	 * @param detailClass2
	 *            从实体类型
	 * @return
	 */
	public static <T, D1, D2> T toBean(String jsonString, Class<T> mainClass, String detailName1, Class<D1> detailClass1, String detailName2,
			Class<D2> detailClass2) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
		JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
		T mainEntity = toBean(jsonObject, mainClass);
		List<D1> detailList1 = toList(jsonArray1, detailClass1);
		List<D2> detailList2 = toList(jsonArray2, detailClass2);
		try {
			BeanUtils.setProperty(mainEntity, detailName1, detailList1);
			BeanUtils.setProperty(mainEntity, detailName2, detailList2);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}
		return mainEntity;
	}

	/***************************************************************************
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>泛型T
	 *            代表主实体类型
	 * @param <D1>泛型D1
	 *            代表从实体类型
	 * @param <D2>泛型D2
	 *            代表从实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailName1
	 *            从实体类在主实体类中的属性
	 * @param detailClass1
	 *            从实体类型
	 * @param detailName2
	 *            从实体类在主实体类中的属性
	 * @param detailClass2
	 *            从实体类型
	 * @param detailName3
	 *            从实体类在主实体类中的属性
	 * @param detailClass3
	 *            从实体类型
	 * @return
	 */

	public static <T, D1, D2, D3> T toBean(String jsonString, Class<T> mainClass, String detailName1, Class<D1> detailClass1, String detailName2,
			Class<D2> detailClass2, String detailName3, Class<D3> detailClass3) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
		JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
		JSONArray jsonArray3 = (JSONArray) jsonObject.get(detailName3);
		T mainEntity = toBean(jsonObject, mainClass);
		List<D1> detailList1 = toList(jsonArray1, detailClass1);
		List<D2> detailList2 = toList(jsonArray2, detailClass2);
		List<D3> detailList3 = toList(jsonArray3, detailClass3);
		try {
			BeanUtils.setProperty(mainEntity, detailName1, detailList1);
			BeanUtils.setProperty(mainEntity, detailName2, detailList2);
			BeanUtils.setProperty(mainEntity, detailName3, detailList3);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}
		return mainEntity;
	}

	/***************************************************************************
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>
	 *            主实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailClass
	 *            存放了多个从实体在主实体中属性名称和类型
	 * @return
	 */
	public static <T> T toBean(String jsonString, Class<T> mainClass, HashMap<String, Class> detailClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		T mainEntity = toBean(jsonObject, mainClass);
		for (Object key : detailClass.keySet()) {
			try {
				Class value = (Class) detailClass.get(key);
				BeanUtils.setProperty(mainEntity, key.toString(), value);
			} catch (Exception ex) {
				throw new RuntimeException("主从关系JSON反序列化实体失败！");
			}
		}
		return mainEntity;
	}
	
	/**
	 * 将map的内容转换成json
	 * 
	 * @param map
	 *            <String, String>
	 * @return
	 */
	public static String getJsonContentFromMap(Map<String, String> map) {
		Type type = new TypeToken<Map<String, String>>() {
		}.getType();
		Gson gson = new Gson();

		String jsonContent = gson.toJson(map, type);
		return jsonContent;
	}
	
	/**
	 * 将map的内容转换成json
	 * 
	 * @param map
	 *            <String, Object>
	 * @return
	 */
	public static String getJsonContentFromObjMap(Map<String, Object> map) {
		Type type = new TypeToken<Map<String, Object>>() {
		}.getType();
		Gson gson = new Gson();

		String jsonContent = gson.toJson(map, type);
		return jsonContent;
	}

	
	public static void main(String[] args)
	{
//		Set<String> set = new HashSet<String>();
//		set.add("111");
//		set.add("123");
//		String json = toJSONString(set);
//		System.out.println(json);
//		JSONArray array = JSONArray.fromObject(json);
//		for(int i=0; i<array.size(); i++){
//			System.out.println(array.getString(i));
//		}
		
		Map<String,Object> map = toHashMap("{'1':['abc','abc'],'2':[{'1':'abc'},{'2':'abc'}]}");
		System.out.println(map.get("2"));
	}

}
