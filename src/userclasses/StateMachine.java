/**
 * Your application code goes here
 */

package userclasses;

import generated.StateMachineBase;
import userclasses.entitites.MangaReader;
import userclasses.entitites.MangaWebsite;

import com.codename1.components.WebBrowser;
import com.codename1.javascript.JavascriptContext;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Your name here
 */
public class StateMachine extends StateMachineBase {
	WebBrowser browser;
	MangaWebsite site;
	JavascriptContext ctx;
	
    public StateMachine(String resFile) {
        super(resFile);
        // do not modify, write code in initVars and initialize class members there,
        // the constructor might be invoked too late due to race conditions that might occur
    }
    
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
	protected void initVars(Resources res){
	}

    @Override
    protected void beforeMain(Form f) {
    	this.browser = new WebBrowser() {
			@Override
			public void onLoad(String url) {
				Component c = getInternal();
				if (c instanceof BrowserComponent) {
					BrowserComponent b = (BrowserComponent) c;
					JavascriptContext ctx = new JavascriptContext(b);
					onloadBrowserHandler(ctx);
				}
			}
			
			@Override
			public void onStart(String url) {
				findUrlField().setText(this.getURL());
			}
		};
		findBrowserWebContainer(f).addComponent(BorderLayout.CENTER, browser);
		browser.setURL("http://www.mangareader.net/");
    }
    
    public void onloadBrowserHandler(JavascriptContext ctx) {
    	String inpUrl = findUrlField().getText();
    	this.ctx = ctx;
    	
    	// added download button
    	Button dload = new Button("Download");
    	dload.setName("Download");
    	dload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dloadBtnAction(evt);
			}
		});
    	
    	// added remove button
    	Button remove = new Button("Remove");
    	remove.setName("Remove");
    	remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				removeBtnAction(evt);
			}
		});
    	// reset the container
    	findDLbuttonContainer().removeComponent(remove);
    	findDLbuttonContainer().removeComponent(dload);
    	if(inpUrl.indexOf("mangareader") != -1) {
    		site = new MangaReader();
    		if((site.isMangaPage(ctx) || site.isChapterPage(ctx)) && !findDLbuttonContainer().contains(dload) && !site.isMangaAdded(ctx)) 
    			findDLbuttonContainer().addComponent(dload);
    		else if(site.isMangaPage(ctx) && !findDLbuttonContainer().contains(remove))
    			findDLbuttonContainer().addComponent(remove);
    	}
    	
    	findDLbuttonContainer().revalidate();
    }
    
    public void dloadBtnAction(ActionEvent evt) {
    	site.addManga(this.ctx);
    }
    
    public void removeBtnAction(ActionEvent evt) {
    	site.removeManga(this.ctx);
    }

    @Override
    protected void onMain_UrlFieldAction(Component c, ActionEvent event) {
    	browser.setURL(findUrlField(c).getText());
    }
}
