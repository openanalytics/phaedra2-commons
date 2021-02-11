package eu.openanalytics.phaedra.util;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class ObjectCopyUtils {

	public static void copyNonNullValues(Object from, Object to) {
		BeanUtils.copyProperties(from, to, getNullPropertyNames(from));
	}
	
	public static String[] getNullPropertyNames(Object bean) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(bean);
		return Stream.of(beanWrapper.getPropertyDescriptors())
				.map(FeatureDescriptor::getName)
				.filter(prop -> beanWrapper.getPropertyValue(prop) == null)
				.toArray(String[]::new);
	}

}
