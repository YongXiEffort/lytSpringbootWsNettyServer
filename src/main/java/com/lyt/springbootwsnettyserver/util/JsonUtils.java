package com.lyt.springbootwsnettyserver.util;
/**
 * version 1.0
 * created by jinbangchen
 */

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		//设置输出时包含属性的风格
		//Include.Include.ALWAYS 默认 
		//Include.NON_DEFAULT 属性为默认值不序列化 
		//Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化 
		//Include.NON_NULL 属性为NULL 不序列化 
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		//设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性(json中多余的字段忽略)
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	/**
	 * Object可以是POJO，也可以是Collection或数组。
	 * 如果对象为Null, 返回"null".
	 * 如果集合为空集合, 返回"[]".
	 */
	public static String toJson(Object object) {

		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 反序列化POJO或简单Collection如List<String>.
	 * <p>
	 * 如果JSON字符串为Null或"null"字符串, 返回Null.
	 * 如果JSON字符串为"[]", 返回空集合.
	 * <p>
	 * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
	 *
	 * @throws IOException
	 * @see #fromJson(String, JavaType)
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isNullOrEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			try {
				throw new Exception();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 反序列化复杂Collection如List<Bean>, 先使用createCollectionType()或contructMapType()构造类型, 然后调用本函数.
	 *
	 * @see #createCollectionType(Class, Class...)
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String jsonString, JavaType javaType) {
		if (StringUtils.isNullOrEmpty(jsonString)) {
			return null;
		}

		try {
			return (T) mapper.readValue(jsonString, javaType);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 转换为通用的map
	 *
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> fromJson(String jsonString) {
		JavaType javaType = contructMapType(Map.class, String.class, Object.class);
		return fromJson(jsonString, javaType);
	}

	/**
	 * 构造Collection类型.
	 */
	public static JavaType contructCollectionType(
			@SuppressWarnings("rawtypes") Class<? extends Collection> collectionClass, Class<?> elementClass) {
		return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
	}

	/**
	 * 构造Map类型.
	 */
	public static JavaType contructMapType(@SuppressWarnings("rawtypes") Class<? extends Map> mapClass,
			Class<?> keyClass, Class<?> valueClass) {
		return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
	}

	/**
	 * 当JSON里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的属性.
	 */
	public static <T> T update(String jsonString, T object) {
		try {
			return mapper.readerForUpdating(object).readValue(jsonString);
		} catch (JsonProcessingException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		} catch (IOException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		}
		return null;
	}

	/**
	 * 输出JSONP格式数据.
	 */
	public static String toJsonP(String functionName, Object object) {
		return toJson(new JSONPObject(functionName, object));
	}

	/**
	 * 取出JsonNode方便操作json字段.
	 */
	public static JsonNode getrootNode(String jsonString) {
		try {
			return mapper.readTree(jsonString);
		} catch (JsonProcessingException e) {
			logger.warn("json read tree node error:" + jsonString, e);
			return null;
		} catch (IOException e) {
			logger.warn("json read tree node error:" + jsonString, e);
			return null;
		}
	}
}
