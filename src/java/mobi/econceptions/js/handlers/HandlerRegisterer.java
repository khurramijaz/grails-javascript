package mobi.econceptions.js.handlers;

public interface HandlerRegisterer {
	int getPriority();
	Handler getHandler();
}
