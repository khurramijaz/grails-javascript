package mobi.econceptions.js.handlers.impl;

import mobi.econceptions.js.StatementMutator;
import mobi.econceptions.js.handlers.HandlerType;
import mobi.econceptions.js.handlers.PropertyHandler;


public class DefaultPropertyHandler extends AbstractHandler implements PropertyHandler {
	public DefaultPropertyHandler(){
		super(HandlerType.Property);
	}
	
	public boolean propertyMissing(StatementMutator m, String name) {
		if( m.size() > 0){
			m.appendOperator(".");
		}
		m.appendName(name );
		//Returns true in call case as it is the default handler.
		return true;
	}

	public boolean propertyMissing(StatementMutator m, String name, Object value) {
		if( m.size() > 0){
			m.appendOperator(".");
		}
		m.appendName(name );
		
		//Returns true in call case as it is the default handler.
		return true;
	}
}
