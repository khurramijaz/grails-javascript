package mobi.econceptions.js.handlers.impl;

import mobi.econceptions.js.StatementMutator;
import mobi.econceptions.js.nodes.Operator;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

public class UnaryOperatorHandler extends DefaultMethodHandler{
	SortedMap<String,String> ops = Collections.synchronizedSortedMap( new TreeMap<String,String>());

	public UnaryOperatorHandler(){
		add("bitwiseNegate","~");
		add("negative","-");
		add("positive","+");
	}
	public void add(String name , String op){
		ops.put(name, op);
	}

	@Override
	public boolean methodMissing(StatementMutator m, String name, Object[] args) {
		if( m.size() == 0 || args.length != 0 || !ops.containsKey( name ) ){
			return false;
		}
		m.addNode( 0 , new Operator( ops.get( name )));
		m.addNode( 0 , new Operator("("));
		m.appendOperator(")");
		return true;
	}
}
