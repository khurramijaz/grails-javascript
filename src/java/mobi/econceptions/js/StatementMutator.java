package mobi.econceptions.js;

import java.util.LinkedList;


public interface StatementMutator {
	public void append(Node node);
	public void append(Name name);
	public void append(Operator operator);
	public void append(Value value);
	public void appendName(String name);
	public void appendOperator(String operator);
	public void appendValue( Object value);
	public void addNode(int index , Node node);
	public void removeNode(int index);
	public Node getNode(int index);
	public int size();
	public void setParameter(boolean parameter);
	public boolean getParameter();
	public Javascript getParent();
	public void setParent(Javascript javascript);
	public Statement getStatement();
	public boolean render(LinkedList<Object> r);
	public boolean isPlainText();
	public void setPlainText(boolean p);
}
