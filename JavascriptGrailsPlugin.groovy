
import mobi.econceptions.js.internal.json.JsMarshallerRegisterer
import org.codehaus.groovy.grails.web.converters.ConverterUtil
import javax.servlet.http.HttpServletRequest
import grails.converters.Javascript
import grails.converters.jQuery

class JavascriptGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.2 > *"
    // the other plugins this plugin depends on
    def dependsOn = ["converters":"1.3.2"]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]
	def observe = ["controllers"]
    // TODO Fill in these fields
    def author = "Khurram Ijaz"
    def authorEmail = "khurramijaz@gmail.com"
    def title = "Grails javascript generator."
    def description = '''\\
Javascript generator for grails. A prelude to gjs templates.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/javascript"

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before 
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
	    jsMarshallerRegisterer(JsMarshallerRegisterer)
    }

    def doWithDynamicMethods = { ctx ->
        //Mostly copied from Converter plugins as it does not override Closure.asType
	    def asTypeMethod = {java.lang.Class clazz ->
            if (ConverterUtil.isConverterClass(clazz)) {
                return ConverterUtil.createConverter(clazz, delegate, applicationContext)
            } else {
                return ConverterUtil.invokeOriginalAsTypeMethod(delegate, clazz)
            }
        }
	    

	    List targetClasses = [Closure]
	    for(Class clazz in targetClasses) {
            MetaClassRegistry registry = GroovySystem.metaClassRegistry
            def mc = registry.getMetaClass(clazz)
            if (!(mc instanceof ExpandoMetaClass)) {
                registry.removeMetaClass(clazz)
                mc = registry.getMetaClass(clazz)
                if(!(mc instanceof ExpandoMetaClass)) {
                    log.warn "Unable to add Converter Functionality to Class ${className}"
                    return;
                }
            }
            log.debug "Adding Converter asType Method to Class ${clazz} [${clazz.class}] -> [${mc.class}]"
            mc.asType = asTypeMethod
            mc.initialize()
        }

	    HttpServletRequest.metaClass.isAjax = {->
             delegate.getHeader('X-Requested-With') == "XMLHttpRequest"
        }
	    HttpServletRequest.metaClass.isAcceptsJs = {->
             String accept = delegate.getHeader('Accept')
		     if( ! accept) return false
		     ["text/javascript", "application/javascript"].any{ ctType ->
			     accept.indexOf( ctType) >= 0
		     }
        }


	    def controllerClasses = application.controllerClasses
        for (controller in controllerClasses) {
            def mc = controller.metaClass
            mc.jsRender = jsRender
	        mc.jqRender = jqRender
        }

    }
	def jsRender = { Closure c ->
		Javascript script = new Javascript( c )
		delegate.render( script )
		return null //simpler one step return in controllers.
	}
	def jqRender = { Closure c ->
		Javascript script = new jQuery( c )
		delegate.render( script )
		return null
	}
    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
	    def mc = event.source.metaClass
	    mc.jsRender = jsRender
	    mc.jqRender = jqRender
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
