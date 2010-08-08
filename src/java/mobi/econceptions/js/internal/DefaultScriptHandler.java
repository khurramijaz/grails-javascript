package mobi.econceptions.js.internal;

import groovy.lang.Closure;
import groovy.lang.GString;
import mobi.econceptions.js.*;
import mobi.econceptions.js.nodes.Name;
import mobi.econceptions.js.nodes.Operator;

import static mobi.econceptions.js.AccessUtils.*;
import java.util.HashMap;
import java.util.Map;

public class DefaultScriptHandler implements ScriptHandler {
	protected Map<String,String> binaryOperators = new HashMap<String,String>();
	protected Map<String,String> propertyReplacements = new HashMap<String,String>();
	protected Map<String,String> prefixUnaryOperators = new HashMap<String,String>();
	protected Map<String,String> postfixUnaryOperators = new HashMap<String,String>();
	public DefaultScriptHandler(){
		binaryOperators.put("plus","+");
		binaryOperators.put("minus","-");
		binaryOperators.put("multiply","*");
		binaryOperators.put("power","**");
		binaryOperators.put("div","/");
		binaryOperators.put("mod","%");
		binaryOperators.put("or","|");
		binaryOperators.put("and","&");
		binaryOperators.put("xor","^");
		//binaryOperators.put("and" , " && ");
		//binaryOperators.put("or" , "||");
		prefixUnaryOperators.put("bitwiseNegate","~");
		prefixUnaryOperators.put("negative","-");
		prefixUnaryOperators.put("positive","+");
		//postfixUnaryOperators.put("next","+1");//Method missing handled out of sequence.
		//binaryOperators.put("prev","-1");//Method missing handled out of sequence.
		/*binaryOperators.put("bitwiseNegate","~");
		binaryOperators.put("negative","-");
		binaryOperators.put("positive","+");*/
	}

	public void callPrefix(StatementMutator mutator) {
		//None.
	}
	private void appendDot(StatementMutator m){
		if( m.size() > 0){
			m.appendOperator(".");
		}
	}
	
	public void propertyMissing(StatementMutator mutator, String name, Object value) {
		appendDot(mutator);
		mutator.appendName( name );
		mutator.appendOperator("=");
		mutator.appendValue( value );
	}

	public void propertyMissing(StatementMutator mutator, String name) {
		appendDot(mutator);
		if( name == "This") name = "this";

		mutator.appendName(name);
	}

	public void methodMissing(StatementMutator mutator, String name, Object[] args) {
		for( MethodNodeBuilder b : methodNodeBuilders){
			if( b.apply( mutator , name, args)) return;
		}
	}
	protected void callMethod( StatementMutator m , String name, Object[] args){
		m.appendName(name);
		m.appendName("(");
		int end = args.length;
		if( end == 0){
			m.appendOperator(")");
			return;
		}
		if( args[end-1] instanceof Closure ) end--;
		boolean comma = false;
		for( int i=0; i < end; i++){
			if( comma) m.appendOperator(",");
			m.appendValue( args[i]);
			comma = true;
		}
		m.appendOperator(")");
		if( end != args.length ) handleClosure( m, (Closure)args[end]);
	}
	protected void handleClosure(StatementMutator m , Closure c){
		m.appendOperator("{");
		Javascript js =mutator( m.getParent()).createChild();
		mutator(js).execute(c);
		m.appendValue( js );
		m.appendOperator("}");
	}
	
	protected void getAt(StatementMutator m , Object index ){
		
		m.appendOperator("[");
		m.appendValue(index);
		m.appendOperator("]");
		
	}
	protected void putAt(StatementMutator m , Object index, Object value ){
		getAt(m, index);
		m.appendOperator("=");
		m.appendValue(value);
	}
	protected void asis( StatementMutator mutator, String js){
		mutator.setPlainText(true);
		mutator.appendName( js );
	}
	protected boolean checkBinaryOperators(StatementMutator mutator, String name , Object arg){
		int len = mutator.size();
		if(len > 0 && binaryOperators.containsKey( name )){
			mutator.addNode( 0 , new Operator("("));
			mutator.appendOperator( binaryOperators.get( name ));
			mutator.appendValue( arg);
			mutator.appendOperator(")");
			return true;
		}
		return false;
	}
	protected boolean checkUnaryOperators(StatementMutator mutator , String name ){
		int len = mutator.size();
		if(len > 0 && prefixUnaryOperators.containsKey( name )){
			mutator.addNode(0 , new Operator( prefixUnaryOperators.get( name )));
			mutator.addNode( 0 , new Operator("("));
			mutator.appendOperator(")");
			return true;
		}
		return false;
	}
	protected boolean checkPostfixOperators(StatementMutator mutator , String name , Object args){
		int len = mutator.size();
		if(len > 0 && postfixUnaryOperators.containsKey( name )){
			mutator.appendOperator( postfixUnaryOperators.get( name ));
			return true;
		}
		return false;
	}
	protected boolean areAllStatements(Object[] sts){
		
		for(Object o : sts){
			if( ! (o instanceof Statement) ){
				return false;
			}
		}

		return true;
	}
	protected boolean var(StatementMutator m, Object[] definitions){
		int len = m.size();
		if( len == 0){
			m.appendName("var ");
			boolean comma =false;
			for(Object o : definitions){
				if( comma) m.appendOperator(",");
				m.appendValue( o);
				comma = true;
			}
			return true;
		}
		
		return false;
	}
	protected boolean function(StatementMutator m,  Object definition){
		if( m.size() == 0){
			m.appendName("function ");
			m.appendValue( definition);
			return true;
		}
		return false;
	}
	protected boolean newObject(StatementMutator m ,String name, Object[] args){
		if( m.size() == 0){
			callMethod(m,name, args);
			m.addNode(0 , new Name("new "));//For future
			return true;
		}
		return false;
	}

	protected interface MethodNodeBuilder{
		public boolean apply(StatementMutator m , String name , Object[] args);
	}
	protected MethodNodeBuilder[] methodNodeBuilders = new MethodNodeBuilder[]{
		//In order of precedence(How i think they will be used). Default method call is at the end.
		//binary operators.
		new MethodNodeBuilder(){
			public boolean apply(StatementMutator m, String name, Object[] args) {
				return m.size() > 0 && args.length == 1 && checkBinaryOperators(m,name,args[0]);
			}
		},
		//new_Array() or any class.
		new MethodNodeBuilder(){
			public boolean apply(StatementMutator m, String name, Object[] args) {
				return name.startsWith("new_") && name.length() >4 && newObject( m, name.substring(4), args);
			}
		},
		//a[0] subscript operations.
		new MethodNodeBuilder(){
			public boolean apply(StatementMutator m, String name, Object[] args) {
				boolean ret =  "getAt".equals(name) && args.length == 1;
				if( ret) getAt( m , args[0]);
				return ret;
			}
		},
		//a[0]=1 subscript operations with value set.
		new MethodNodeBuilder(){
			public boolean apply(StatementMutator m, String name, Object[] args) {
				boolean ret = "putAt".equals(name) && args.length == 2;
				if( ret ) putAt( m , args[0], args[1]);
				return ret;
			}
		},
		//var definitions.
		new MethodNodeBuilder(){
			public boolean apply(StatementMutator m, String name, Object[] args) {
				return "var".equals(name) && areAllStatements(args) && var( m, args);
			}
		},
		//function definition.
		new MethodNodeBuilder(){
			public boolean apply(StatementMutator m, String name, Object[] args) {
				return "function".equals(name) && args.length == 1 && areAllStatements(args) && function(m, args[0] );
			}
		},
		//Unary operators.
		new MethodNodeBuilder(){
			public boolean apply(StatementMutator m, String name, Object[] args) {
				return args.length == 0 && checkUnaryOperators( m, name);
			}
		},
		//asis method outputs whatever is in parameters.
		new MethodNodeBuilder(){
			public boolean apply(StatementMutator m, String name, Object[] args) {
				boolean ret = "asis".equals(name) && args.length == 1 && (args[0] instanceof String || args[0] instanceof GString );
				if( ret ) asis( m , args[0].toString());
				return ret;
			}
		}
		//Final handler creates a method call.
		,new MethodNodeBuilder(){
			public boolean apply(StatementMutator m, String name, Object[] args) {
				if( name.trim().length() > 0)appendDot(m);
				callMethod( m, name, args);
				return true;
			}
		}
	};
}
