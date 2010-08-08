package mobi.econceptions.js.json;
import org.codehaus.groovy.grails.web.json.JSONObject;

public class JsJSONObject extends JSONObject{
	private String value;
	public JsJSONObject(String v){ value = v; }

	@Override
	public String toString() {
		return value;
	}
}
