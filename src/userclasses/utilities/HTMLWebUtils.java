package userclasses.utilities;

import com.codename1.javascript.JSObject;
import com.codename1.javascript.JavascriptContext;

public class HTMLWebUtils {
	
	public static Boolean isJQuery(JavascriptContext ctx) {
		String isjQuery = (String) ctx.get("typeof(jQuery)");
		if(!isjQuery.equals("undefined") && isjQuery.equals("function")) {
			return Boolean.TRUE;
		}
		else {
			return Boolean.FALSE;
		}
	}
	
	public static JSObject getElementById(JavascriptContext ctx, String elementId) {
		if(isJQuery(ctx)) {
			return (JSObject) ctx.get("jQuery('#"+elementId+"')");
		}
		else {
			return (JSObject) ctx.get("document.getElementById('"+elementId+"')");
		}
	}
	
	public static JSObject getElementByClass(JavascriptContext ctx, String elementId) {
		if(isJQuery(ctx)) {
			return (JSObject) ctx.get("jQuery('."+elementId+"')");
		}
		else {
			return (JSObject) ctx.get("document.getElementsByClassName('"+elementId+"')");
		}
	}
	
	public static String jq_getInnerHTML(JavascriptContext ctx, String elementPath) {
		if(isJQuery(ctx)) {
			return (String) ctx.get("jQuery('"+elementPath+"').html()");
		}
		else {
			return "";
		}
	}
}
