package mobi.econceptions.js.handlers.impl;

import mobi.econceptions.js.StatementMutator;
import mobi.econceptions.js.handlers.HandlerType;
import mobi.econceptions.js.handlers.MethodHandler;

public class ArrayIndexHandler extends AbstractHandler implements MethodHandler{

	public ArrayIndexHandler(){
		super(HandlerType.Method);
	}

	public boolean methodMissing(StatementMutator m, String name, Object[] args) {
		int len = args.length;
		if( len == 1 && "getAt".equals( name )){
			getAt(m , args[0]);
		}
		else if( len == 2 && "putAt".equals( name )){
			putAt( m, args[0] , args[1]);
		}
		else return false;
		return true;
	}
	public void getAt( StatementMutator m ,Object index){
		m.appendOperator("[");
		m.appendValue( index );
		m.appendOperator("]");
	}
	public void putAt(StatementMutator m , Object index, Object value){
		getAt(m,index);
		m.appendOperator( "=");
		m.appendValue( value );
	}
}
