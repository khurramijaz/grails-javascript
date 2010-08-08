package codecs

import grails.converters.Javascript
class JsCodec {
	static encode = { target ->
		return  new Javascript(target);
	}
}
