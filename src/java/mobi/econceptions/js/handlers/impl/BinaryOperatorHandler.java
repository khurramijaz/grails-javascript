package mobi.econceptions.js.handlers.impl;

import mobi.econceptions.js.nodes.Operator;
import mobi.econceptions.js.StatementMutator;

import java.util.*;

public class BinaryOperatorHandler extends DefaultMethodHandler {
	SortedMap<String,String> ops = Collections.synchronizedSortedMap( new TreeMap<String,String>());

	public BinaryOperatorHandler(){
		add("plus", "+");
		add("minus", "-");
		add("div", "/");
		add( "mod" , "%");
		add("multiply","*");
		add("and","&");
		add( "or", "|");
		add("xor", "^");
		add("AND" , "&&");
		add("OR" , "||");
		add("power","**");
	}
	public void add(String method,String op){
		ops.put( method, op );
	}

	@Override
	public boolean methodMissing(StatementMutator m, String name, Object[] args) {
		if( args.length != 1 || m.size()==0 || ! ops.containsKey( name ) ){
			return false;
		}
		

		m.addNode( 0 , new Operator( "(")); // cover the expression in () so that js precedence is the same as groovy's 
		m.appendOperator( ops.get( name ) );
		m.appendValue( args[0]);
		m.appendOperator(")");

		return true;
	}
}
