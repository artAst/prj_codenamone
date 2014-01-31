package userclasses.utilities;

import java.io.IOException;
import java.io.InputStream;

import com.codename1.components.WebBrowser;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.javascript.JavascriptContext;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Component;

public class HTTPWebUtils {
	static String response = "";
	
	public static JavascriptContext getJSContextByUrl(String url) {
		ConnectionRequest req = new ConnectionRequest(){
			int chr;
			StringBuffer sb = new StringBuffer();
			
			@Override
			protected void readResponse(InputStream input) throws IOException {
				//reading the answer                      
				while ((chr = input.read()) != -1){
					sb.append((char) chr);
				}
				response = sb.toString();                                           
				response = response.trim();
			}
		};
		req.setPost(false);
		req.setUrl(url);
		NetworkManager.getInstance().addToQueueAndWait(req);
		
		System.out.println(response);
		WebBrowser browser = new WebBrowser();
		browser.setPage(response, "http://localhost/");
		response = "";
		Component c = browser.getInternal();
		BrowserComponent b = (BrowserComponent) c;
		JavascriptContext ctx = new JavascriptContext(b);
		return ctx;
	}
}
