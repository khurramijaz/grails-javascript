package mobi.econceptions.js.json;

import grails.converters.JSON;

import mobi.econceptions.js.*;
import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException;
import org.codehaus.groovy.grails.web.converters.marshaller.ObjectMarshaller;

import static mobi.econceptions.js.ConversionUtils.*;

public class JsMarshaller implements ObjectMarshaller<JSON>{
	public void marshalObject(Object object, JSON converter) throws ConverterException {
		String content = js( object );
		if( content == null ) return;
		converter.getWriter().value( new JsJSONObject( content ));

	}

	public boolean supports(Object object) {
		return object instanceof Statement || object instanceof Javascript;
	}
}
