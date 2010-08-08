package grails.converters

import grails.test.*
import mobi.econceptions.js.json.JsMarshaller
/**
 * Example use case.
 * def index ={
 *      def count = ...
 *      def list = ....
 *      def model = [ count: count , list: list ]
 *      if( request.js){
 *          render { jq ->
 *              jq("#main").html( g.render( template:"list" , model:model));
 *              var k
 *              k = jq("#rows_${row.id}").previous()
 *              //Generated Js
 *              //$("#main").html( "...template content generated...");
 *
 *          }
 *      }
 *      else return model
 * }
 }
 */
class JavascriptTests extends GrailsUnitTestCase {
    Javascript js
    protected void setUp() {
        super.setUp()
	    JSON.registerObjectMarshaller(new JsMarshaller() , 100)
	    js = new Javascript();
    }
	private void executeAndRender( String expected , Closure c){
		expected = expected.replaceAll(/\s+|\t|\r|\n/,"").replaceAll(/@/," ")

		
		js.setTarget( c);


		def ret =js.toString();

		println( expected )
		println( ret )
		assertEquals expected, ret
	}
    protected void tearDown() {
        super.tearDown()
    }

    void testRender() {
		executeAndRender( "h=1;a(\"Me\").you(1,23,1.1).ajax().y=true;"){
			h=1;
			a("Me").you(1,23,1.1).ajax().y = true
		}
    }
	void testNestedClosure(){
		String expected ="""jQuery("#id").ajax({
			"onSuccess":function(){
			jQuery("#status").hide();
			},
				"onError":function(data){
					jQuery("#error").html(data.message);
					jQuery("#error").show();		
				
				}
		});abcd=defi; """
		executeAndRender(expected){
			jQuery("#id").ajax(
			   [
					   onSuccess: function(){
						   jQuery("#status").hide();
					   },
					   onError:function(data){
						   jQuery("#error").html( data.message);
						   jQuery("#error").show();
					   }

			   ]
			);
			abcd = defi;
		}
	}
	void testSubscripts(){
		String expected = """a(1).b[1]=b.c;
		a.c.d[0].hell();
		q=function(a,d){ ayz.Hello=["od","it"];
		};
		"""
		executeAndRender expected,{
			a(1).b[1] = b.c
			a.c.d[0].hell();
			q = function(a,d){
				ayz["Hello"] = ["od","it"]
			}
		}
	}
	void testAsis(){
		String expected=""" for(i=0;i<20;i++){
		st[i] = (i*i);
		a = b[0][0].name;
		};
		"""
		executeAndRender expected,{
			asis("for(i=0;i<20;i++){")
			st[i] = i*i;
			a = b[0][0].name
			asis("};")
		}
	}
	void testBinaryOperators(){
		String expected="""
		a = (a+1);
		a = (a-1);
		a =(a/b);
		a=(a%c);
		a = (a**2);
		a = (a|c);
		a =(a&c);
		a=(a^c);
		"""

		executeAndRender expected,{
			a = a+1;
			a= a-1;
			a=a/b;
			a=a%c
			a=a**2
			a = a|c
			a = a&c
			a=a^c;
		}
	}
	void testUnaryOperators(){
		String expected = """
		a = ((+b)+1);
		c = (-a).render();
		d = ((~q)&c);
		"""
		executeAndRender expected,{
			a = +b + 1
			c = (-a).render();
			d = ~q & c
		}
	}
	void testVarAndFunction(){
		String expected="""
		function@onSuccess(data){
			jQuery("#id").html( data.message);
		};
		var@i,q,r;
			i=1;
			q=23;
			r="abcd";
		"""
		executeAndRender expected,{
			function onSuccess(data){
				jQuery("#id").html( data.message )
			}
			var i,q,r
			i=1
			q=23
			r="abcd"
		}
	}
	void testNewClass(){
		String expected="""
			var@a;
			a = new@Array(1,2,3);
			this.me = true;
			
		"""
		executeAndRender expected,{
			var a
			a = new_Array(1,2,3)
			This.me=true;

		}
	}

}
