
/**
 * Created by IntelliJ IDEA.
 * User: khurram
 * Date: Aug 5, 2010
 * Time: 6:28:16 PM
 * To change this template use File | Settings | File Templates.
 */
import grails.converters.jQuery;
class JqueryCodec {
	static encode = { target ->
		return target as jQuery
	}
}
