import grails.converters.Javascript
/**
 * Created by IntelliJ IDEA.
 * User: khurram
 * Date: Aug 5, 2010
 * Time: 6:28:01 PM
 * To change this template use File | Settings | File Templates.
 */
class JsCodec {
	static encode = { target ->
		return target as Javascript;
	}
}
