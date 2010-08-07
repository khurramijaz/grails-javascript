package mobi.econceptions.js;

import mobi.econceptions.js.internal.JavascriptObjectSupport;

public class CallArgument extends JavascriptObjectSupport{
	private Javascript js;

	public CallArgument(Javascript js){
		this.js = js;
	}
	public Statement propertyMissing(String name){
		return js.callProperty(name);
	}
	public void propertyMissing(String name, Object value){
		js.callProperty(name,value);
	}
	public Statement methodMissing(String name, Object args){
		return js.callMethod( name , args);
	}
}