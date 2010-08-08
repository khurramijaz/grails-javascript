package mobi.econceptions.js.handlers.config;

import mobi.econceptions.js.handlers.*;
import org.springframework.context.ApplicationContext;


import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;


public class DefaultHandlerConfiguration implements HandlerConfiguration{
	private static final AtomicInteger HANDLERSEQUENCE = new AtomicInteger(0);

	private static final int MAXPRIORITY = Integer.MAX_VALUE - 5000;
	private static final AtomicInteger MAXMETHODPRIORITY = new AtomicInteger(MAXPRIORITY);
	private static final AtomicInteger MAXPROPERTYPRIORITY = new AtomicInteger(MAXPRIORITY);

	private TreeSet<Entry<MethodHandler>> methodHandlers = new TreeSet<Entry<MethodHandler>>();
	private TreeSet<Entry<PropertyHandler>> propertyHandlers = new TreeSet<Entry<PropertyHandler>>();

	MethodHandler[] methods = null;
	PropertyHandler[] properties = null;
	
	
	public void addHandler(Handler handler, int priority) {
		if( handler.getType() == HandlerType.Method ){
			if( priority > MAXPRIORITY ){
				priority = MAXMETHODPRIORITY.incrementAndGet();
			}
			addMethodHandler( (MethodHandler) handler , priority);
		}
		else{
			if( priority > MAXPRIORITY ){
				priority = MAXPROPERTYPRIORITY.incrementAndGet();
			}
			addPropertyHandler((PropertyHandler)handler, priority );
		}
	}

	public void addPropertyHandler(PropertyHandler propertyHandler, int priority){
		propertyHandlers.add( new Entry<PropertyHandler>( propertyHandler , priority));
	}
	public void addMethodHandler(MethodHandler methodHandler , int priority){
		methodHandlers.add( new Entry<MethodHandler>( methodHandler , priority));
	}

	public void freeze() {
		if( methods != null || properties != null ) return;//Freeze once.
		methods = new MethodHandler[ methodHandlers.size() ];
		properties = new PropertyHandler[ propertyHandlers.size() ];
		int index = 0;
		for( Entry<MethodHandler> entry: methodHandlers ){
			methods[ index++] = entry.handler;
		}
		index = 0;
		for( Entry<PropertyHandler> entry : propertyHandlers){
			properties[ index++ ] = entry.handler;
		}
	}
	public void registerFromApplicationContext(ApplicationContext context){
		for( HandlerRegisterer o : context.getBeansOfType(HandlerRegisterer.class).values()){
			addHandler( o.getHandler() , o.getPriority());
		}
	}
	public MethodHandler[] getMethodHandlers() {
		return methods.clone();
	}

	public PropertyHandler[] getPropertyHandlers() {
		return properties.clone();
	}
	private class Entry<T> implements Comparable<Entry<T>>{
		int index = 0;
		int priority = 0;
		T handler;
		public Entry( T handler, int priority){
			this.handler = handler;
			this.priority = priority;
			index = HANDLERSEQUENCE.incrementAndGet();
			
		}
		public int compareTo(Entry o) {
			return o.priority == priority ? o.index - index : o.priority - priority;
		}
	}
}
