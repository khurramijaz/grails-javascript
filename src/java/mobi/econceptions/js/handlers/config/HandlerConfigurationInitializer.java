package mobi.econceptions.js.handlers.config;

import mobi.econceptions.js.handlers.impl.*;
import org.springframework.context.ApplicationContext;

public class HandlerConfigurationInitializer {


	public static void init( boolean emptyHandler ){
		init( null , emptyHandler);
	}
	public static void init( ApplicationContext context , boolean emptyHandler){
		DefaultHandlerConfiguration config = new DefaultHandlerConfiguration();
		if( emptyHandler){
			config.addMethodHandler( new CallMethodHandler() ,0 );
			config.addPropertyHandler( new EmptyPropertyNameHandler() , 0 );
		}
		else{
			config.addMethodHandler( new DefaultMethodHandler(), 0);
			config.addPropertyHandler( new DefaultPropertyHandler() , 0);
		}
		int i = 1;
		//Add property handlers.

		//Add Method handlers.
		i = 1;
		config.addMethodHandler( new AsisMethodHandler(), i++);
		config.addMethodHandler( new FunctionDefinitionHandler(), i++ );
		config.addMethodHandler( new VarDefinitionHandler() , i++ );
		config.addMethodHandler( new BinaryOperatorHandler(), i++);
		config.addMethodHandler( new UnaryOperatorHandler() , i++ );
		config.addMethodHandler( new ArrayIndexHandler() , i++ );
		config.addMethodHandler( new NewOperatorHandler() , i++ );

		if( context != null ) config.registerFromApplicationContext( context );

		config.freeze();
		HandlerConfigurationHolder.getInstance().setConfiguration( config );

	}

}
