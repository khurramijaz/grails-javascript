import grails.converters.Javascript
class JsCodec {
	static encode = { target ->
		return target as Javascript;
	}
}
