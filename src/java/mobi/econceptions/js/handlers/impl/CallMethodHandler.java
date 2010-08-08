package mobi.econceptions.js.handlers.impl;

import mobi.econceptions.js.StatementMutator;


public class CallMethodHandler extends DefaultMethodHandler{
	@Override
	public boolean methodMissing(StatementMutator m, String name, Object[] args) {
		if( m.size() == 0 && "call".equals( name )){// Only replace if first.
			name = "$";

		}
		return super.methodMissing(m, name, args);
	}
}
