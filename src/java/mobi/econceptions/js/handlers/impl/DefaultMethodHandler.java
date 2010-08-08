package mobi.econceptions.js.handlers.impl;

import mobi.econceptions.js.Javascript;
import groovy.lang.Closure;
import mobi.econceptions.js.StatementMutator;
import mobi.econceptions.js.handlers.HandlerType;
import mobi.econceptions.js.handlers.MethodHandler;

import static mobi.econceptions.js.AccessUtils.*;
public class DefaultMethodHandler extends AbstractHandler implements MethodHandler {

	public DefaultMethodHandler(){
		super(  HandlerType.Method );
	}
	

	public boolean methodMissing(StatementMutator m, String name, Object[] args) {
		if( m.size() > 0 && ! "".equals(name )){//can be called like (a.b.c)()
			m.appendOperator(".");
		}
		createMethod(m, name, args);
		return true;
	}

	protected void createMethod(StatementMutator m, String name, Object[] args) {
		m.appendName(name);
		m.appendOperator("(");
		int end = args.length;
		if( end == 0 ){
			m.appendOperator(")");
			return;
		}
		if(  args[end-1] instanceof Closure)	end --;


		boolean comma = false;
		for( int i =0; i < end; i++ ){
			if( comma )m.appendOperator(",");
			m.appendValue( args[i]);
			comma = true;
		}
		m.appendOperator(")");

		if( end != args.length ){
			handleClosure( m , (Closure)args[end]);
		}
	}
	protected void handleClosure(StatementMutator m , Closure c){
		m.appendOperator("{");
		Javascript js = mutator( m.getParent()).createChild();
		mutator(js).execute(c);
		m.appendValue( js );
		m.appendOperator("}");
	}
}
