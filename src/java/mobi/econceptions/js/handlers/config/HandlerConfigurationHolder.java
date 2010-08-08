package mobi.econceptions.js.handlers.config;

public class HandlerConfigurationHolder {
	private static HandlerConfigurationHolder instance = new HandlerConfigurationHolder();

	HandlerConfiguration configuration = null;
	private HandlerConfigurationHolder(){
		
	}
	public void reset(){
		configuration = null;
	}
	public HandlerConfiguration getConfiguration(){
		if( configuration == null ) HandlerConfigurationInitializer.init( true );//if get is called before set..Test environment.
		return configuration;
	}
	public void setConfiguration(HandlerConfiguration c){ configuration = c; }


	public static HandlerConfigurationHolder getInstance(){
		return instance;
	}
}
