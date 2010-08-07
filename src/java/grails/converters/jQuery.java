package grails.converters;


import mobi.econceptions.js.internal.JQueryScriptHandlerFactory;


public class jQuery extends Javascript{
	public jQuery(){
		super();
		scriptHandlerFactory = new JQueryScriptHandlerFactory();
	}
	public jQuery(Object target){
		super(target);
		scriptHandlerFactory = new JQueryScriptHandlerFactory();
	}
	
}
