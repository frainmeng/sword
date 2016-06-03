package com.sword.util.json;

import java.util.List;

import com.alibaba.fastjson.JSON;

public class JsonUtils {
	public static <T> List<T> json2ObjectList(String jsonStr,Class<T> objClass) {
		return JSON.parseArray(jsonStr, objClass);
	}
}
