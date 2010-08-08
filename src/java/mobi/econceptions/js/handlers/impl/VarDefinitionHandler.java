package mobi.econceptions.js.handlers.impl;

import mobi.econceptions.js.StatementMutator;

/**
 * Created by IntelliJ IDEA.
 * User: khurram
 * Date: Aug 8, 2010
 * Time: 11:46:38 PM

 */
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
		}
		return false;
	}
}
