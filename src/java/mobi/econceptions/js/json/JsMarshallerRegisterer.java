package mobi.econceptions.js.json;

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.converters.configuration.ObjectMarshallerRegisterer;

public class JsMarshallerRegisterer extends ObjectMarshallerRegisterer {
	public JsMarshallerRegisterer(){
		setConverterClass(JSON.class);
		setMarshaller( new JsMarshaller());
		setPriority(100);
	}
}
