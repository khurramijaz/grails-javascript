package mobi.econceptions.js;




public interface SerializableJs extends Js{
	public String __toJsString__();//complex so that name does not clash with other js calls.
}
