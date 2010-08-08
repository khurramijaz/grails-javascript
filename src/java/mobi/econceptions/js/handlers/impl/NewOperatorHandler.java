package mobi.econceptions.js.handlers.impl;

import groovy.lang.Closure;
import mobi.econceptions.js.StatementMutator;

public class NewOperatorHandler extends DefaultMethodHandler{

	@Override
	public boolean methodMissing(StatementMutator m, String name, Object[] args) {
		int len = args.length;
		if( name != null && name.length() > 4 && name.startsWith("new_") && ! (args[len-1] instanceof Closure)){
			name = name.substring( 4 );
			createMethod( m, name , args );
			return true;
		}
		return false;
	}
}
