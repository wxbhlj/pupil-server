package com.vivid.common.utils;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

public class DozerUtil {
	private static Mapper baseMapper = new DozerBeanMapper();
	private DozerUtil() {
		
	}
	
	public static <T> T convert(Object obj, Class<T> c) {
		return baseMapper.map(obj, c);
	}
}
