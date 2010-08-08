package mobi.econceptions.js.handlers.impl;

import mobi.econceptions.js.Statement;
import mobi.econceptions.js.StatementMutator;
import mobi.econceptions.js.handlers.HandlerType;
import mobi.econceptions.js.handlers.MethodHandler;

public class FunctionDefinitionHandler extends AbstractHandler implements MethodHandler {
	public FunctionDefinitionHandler(){
		super(HandlerType.Method );
	}

	public boolean methodMissing(StatementMutator m, String name, Object[] args) {
		if( m.size() == 0 && "function".equals(name ) && args.length ==1  && areStatements( args )){
			m.appendName( "function ");
			m.appendValue( args[0]);
		}
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}
	protected boolean areStatements(Object[] arg){
		for( Object o : arg){
			if( ! (o instanceof Statement)) return false;
		}
		return true;
	}
}
