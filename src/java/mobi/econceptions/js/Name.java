package mobi.econceptions.js;

public class Name extends Node{
	String name;
	public Name(String name){
		this.name = name;
	}
	@Override
	public String render() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
