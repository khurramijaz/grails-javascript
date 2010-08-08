package mobi.econceptions.js.handlers.impl;

import mobi.econceptions.js.StatementMutator;

public class VarDefinitionHandler extends FunctionDefinitionHandler{

	@Override
	public boolean methodMissing(StatementMutator m, String name, Object[] args) {
		if( m.size() == 0 && "var".equals(name ) && areStatements( args )){
			m.appendName("var ");
			boolean comma = false;
			for( Object o: args){
				if( comma ) m.appendOperator(",");
				m.appendValue( o );
				comma = true;
			}
			return true;
		}
		return false;
	}
}
