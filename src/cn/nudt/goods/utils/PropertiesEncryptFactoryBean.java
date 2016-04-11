package cn.nudt.goods.utils;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;

public class PropertiesEncryptFactoryBean implements FactoryBean<Object> {

	private Properties properties;

	public Object getObject() throws Exception {
		return getProperties();
	}

	@SuppressWarnings("unchecked")
	public Class getObjectType() {
		return java.util.Properties.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties inProperties) {
		try {
			if(inProperties.getProperty("namespace")!=null)
			ConnectionSafer.Builder.builder.setNamespace(inProperties.getProperty("namespace"));
			if(inProperties.getProperty("path")!=null)
			ConnectionSafer.Builder.builder.setPath(inProperties.getProperty("path"));
			this.properties = ConnectionSafer.getProperties(inProperties
					.getProperty("key"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
