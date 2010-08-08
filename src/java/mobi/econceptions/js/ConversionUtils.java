package mobi.econceptions.js;

import grails.converters.JSON;
import groovy.lang.Closure;
import org.codehaus.groovy.grails.web.json.JSONException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import java.util.LinkedList;


public class ConversionUtils {
	public static String js( Object arg){
		if( arg == null ) return "null";
		/**
		 * handle here if possible.
		 */
		if( arg instanceof Javascript || arg instanceof Statement ){
			LinkedList<Object> renderedItems = new LinkedList<Object>();
			if( arg instanceof Javascript){
				((Javascript)arg).getMutator().render( renderedItems);
			}
			else{
				StatementMutator m = ((Statement)arg).getMutator();
				m.setParameter(true);
				
				m.render( renderedItems);
			}
			StringWriter out = new StringWriter();
			try{
				render( out , renderedItems);
				return out.toString();
			}catch(IOException e){
				throw new JSONException(e);
			}

		}

		JSON json = new JSON(new Object[]{ arg} );
		String converted = json.toString();
		return converted.substring( 1 ,  converted.length()-1 );
		
	}
	/* public static void executeAndRender(Closure c , Writer out , StatementFactory stmts , ScriptHandlerFactory scripts) throws IOException{
		Javascript script = Javascript.createJavascriptObject( stmts , scripts);
		executeAndRender( script, c , out);
	}*/
	public static void executeAndRender(Closure c , Writer out ) throws IOException{
		//Javascript script = Javascript.createJavascriptObject( stmts , scripts);
		Javascript script = new Javascript();
		executeAndRender( script, c , out);


	}
	public static void executeAndRender(Javascript script, Closure closure,Writer writer)throws IOException{
		execute(script, closure);
		render( writer , script);
	}
	public static void execute(Javascript script, Closure closure){
		script.getMutator().execute( closure );
	}
	public static void render(Writer out , Javascript js)throws IOException{
		LinkedList<Object> output = new LinkedList<Object>();
		js.getMutator().render( output );
		render( out , output);
	}
	public static void render(Writer out , LinkedList<Object > list)throws IOException{
		for(Object obj: list){
			if( obj instanceof LinkedList ){
				render(out , (LinkedList)obj);
			}
			else{
				out.write( (String)obj);
			}
		}
	}
	
}
