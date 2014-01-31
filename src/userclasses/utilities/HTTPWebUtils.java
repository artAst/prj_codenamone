package userclasses.utilities;

import com.codename1.components.WebBrowser;
import com.codename1.javascript.JavascriptContext;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Component;

public class HTTPWebUtils {
	
	public static JavascriptContext getJSContextByUrl(String url) {
		/*boolean hasLoaded = false;
		WebBrowser browser = new WebBrowser(){
			@Override
            public void onLoad(String url) {
				
			}
		};
		browser.setURL(url);
		Component c = browser.getInternal();*/
		BrowserComponent b = new BrowserComponent();
		b.setURL(url);
		JavascriptContext ctx = new JavascriptContext(b);
		return ctx;
	}
}
