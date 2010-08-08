package mobi.econceptions.js;

import mobi.econceptions.js.handlers.MethodHandler;
import mobi.econceptions.js.handlers.PropertyHandler;
import mobi.econceptions.js.handlers.config.HandlerConfiguration;
import mobi.econceptions.js.handlers.config.HandlerConfigurationHolder;

public class ScriptGenerator implements ScriptHandler{
	PropertyHandler[] properties;
	MethodHandler[] methods;
	public ScriptGenerator(){
		HandlerConfiguration config = HandlerConfigurationHolder.getInstance().getConfiguration();
		properties = config.getPropertyHandlers();
		methods = config.getMethodHandlers();
		assert properties != null;
		assert methods != null;
	}

	

	public void propertyMissing(StatementMutator mutator, String name, Object value) {
		for( PropertyHandler handler : properties ){
			if( handler.propertyMissing( mutator, name , value)) return;
		}
	}

	public void propertyMissing(StatementMutator mutator, String name) {
		for( PropertyHandler handler : properties ){
			if( handler.propertyMissing( mutator, name )) return;
		}
	}

	public void methodMissing(StatementMutator mutator, String name, Object[] args) {
		for( MethodHandler m: methods){
			if( m.methodMissing( mutator , name, args)) return;
		}
	}
}
