package mobi.econceptions.js.handlers.impl;

import mobi.econceptions.js.handlers.Handler;
import mobi.econceptions.js.handlers.HandlerType;

public abstract class AbstractHandler implements Handler {
	protected HandlerType type = null;

	public AbstractHandler( HandlerType type){
		
		setType( type );
	}
	

	public HandlerType getType() {
		return type;
	}
	public void setType( HandlerType t){
		if( t != null ){
			type = t;
		}
	}
}
