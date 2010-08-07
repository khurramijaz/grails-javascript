package mobi.econceptions.js;


import java.util.LinkedList;

import static mobi.econceptions.js.ConversionUtils.*;
import static mobi.econceptions.js.AccessUtils.*;


public class Value extends Node{
	Object jsonObject;
	public Value(Object jsonObject){
		this.jsonObject = jsonObject;
	}
	@Override
	public String toString() {
		return jsonObject == null ? "null" :  jsonObject.toString();
	}

	@Override
	public void render(LinkedList<Object> list) {
		if( jsonObject instanceof Statement){
			StatementMutator m = mutator( (Statement) jsonObject );
			m.setParameter(true);
			m.render( list );
			return;
		}
		else if( jsonObject instanceof Javascript ){
			JavascriptMutator m = mutator( (Javascript)jsonObject );
			m.render( list );
			return;
		}
		super.render(list);
	}
	
	@Override
	public String render() {
		return js( jsonObject );
	}
}
