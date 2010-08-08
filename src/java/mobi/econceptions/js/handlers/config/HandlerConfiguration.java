package mobi.econceptions.js.handlers.config;

import mobi.econceptions.js.handlers.Handler;
import mobi.econceptions.js.handlers.MethodHandler;
import mobi.econceptions.js.handlers.PropertyHandler;

public interface HandlerConfiguration {
	
	public void addHandler(Handler handler, int priority);
	public void freeze();
	public MethodHandler[] getMethodHandlers();
	public PropertyHandler[] getPropertyHandlers();
}
