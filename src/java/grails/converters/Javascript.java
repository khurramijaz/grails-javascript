package grails.converters;

import groovy.lang.Closure;

import org.codehaus.groovy.grails.web.converters.AbstractConverter;
import org.codehaus.groovy.grails.web.converters.Converter;
import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException;
import org.codehaus.groovy.grails.web.converters.marshaller.ObjectMarshaller;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.Writer;
import static mobi.econceptions.js.ConversionUtils.*;
public class Javascript extends AbstractConverter<PrintWriter> implements Converter<PrintWriter>{
	//protected ScriptHandlerFactory scriptHandlerFactory = new DefaultScriptHandlerFactory();
	//protected StatementFactory statementFactory = new DefaultStatementFactory();
	PrintWriter out = null;
	Object target;
	public Javascript(){ this(null);}
	public Javascript(Object target){ setTarget(target); }

	
	public void value(Object o) throws ConverterException {
		if( o instanceof Closure){
			Closure c = (Closure)o;
			try{
				//executeAndRender( c , out , statementFactory, scriptHandlerFactory);
				executeAndRender( c , out );
			}catch(IOException e){
				throw new ConverterException(e);
			}
			return;
		}
		throw new ConverterException("Only closure arguments are converted by Javascript converter.");
	}

	
	public void render(HttpServletResponse response) throws ConverterException {
        response.setContentType("text/javascript");
        try {
            render(response.getWriter());
        }
        catch (IOException e) {
            throw new ConverterException(e);
        }
    }

	@Override
	public void setTarget(Object target) {
		this.target = target;
	}
	
	public void render(Writer out) throws ConverterException {
		this.out = new PrintWriter(out);
		value( target );
		try{
			out.flush();
		}catch(IOException e){
			
		}
	}

	public PrintWriter getWriter() throws ConverterException {
		return out;
	}

	public void convertAnother(Object o) throws ConverterException {
		value(o);
	}

	public void build(Closure c) throws ConverterException {
		c.setDelegate(this);
		c.call( new Object[]{ this});
	}

	public ObjectMarshaller<? extends Converter> lookupObjectMarshaller(Object target) {
		return null; 
	}
}
