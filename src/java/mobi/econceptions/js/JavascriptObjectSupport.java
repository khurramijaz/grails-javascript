package mobi.econceptions.js;

import groovy.lang.GroovyObjectSupport;



public abstract class JavascriptObjectSupport extends GroovyObjectSupport{
	@Override
	public Object getProperty(String property) {
		return propertyMissing(property);
	}

	@Override
	public Object invokeMethod(String name, Object args) {
		return methodMissing(name , args);
	}

	@Override
	public void setProperty(String property, Object newValue) {
		propertyMissing( property , newValue);
	}

	public abstract Statement propertyMissing(String name);
	public abstract void propertyMissing(String name, Object value);
	public abstract Statement methodMissing(String name, Object args);
}
