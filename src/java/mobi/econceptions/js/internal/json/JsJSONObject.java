package mobi.econceptions.js.internal.json;
import org.codehaus.groovy.grails.web.json.JSONObject;
/**
 * This is the only way to append to a JSONWriter.
 * Created by IntelliJ IDEA.
 * User: khurram
 * Date: Aug 4, 2010
 * Time: 5:47:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsJSONObject extends JSONObject{
	private String value;
	public JsJSONObject(String v){ value = v; }

	@Override
	public String toString() {
		return value;
	}
}
