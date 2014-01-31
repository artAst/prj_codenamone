package userclasses.entitites;

import java.util.ArrayList;
import java.util.List;

import userclasses.utilities.HTMLWebUtils;
import userclasses.utilities.HTTPWebUtils;

import com.codename1.io.Storage;
import com.codename1.javascript.JSObject;
import com.codename1.javascript.JavascriptContext;

public class MangaReader extends MangaWebsite {
	
	public MangaReader() {
		setHomePageUri("http://www.mangareader.net/");
		setMangaListUri("http://www.mangareader.net/alphabetical");
		setPopularMangaUri("http://www.mangareader.net/popular");
		setMangaElemClassPath("aname");
	}
	
	@SuppressWarnings("unchecked")
	public boolean isMangaAdded(JavascriptContext ctx) {
		String mangaTitle = getMangaTitleOnPage(ctx);
		List<String> mangaList = (ArrayList<String>) Storage.getInstance().readObject("MyMangaQueue");
		return isMangaAdded(mangaList, mangaTitle);
	}
	
	public String getMangaTitleOnPage(JavascriptContext ctx) {
		String mangaTitle = "";
		if(isMangaPage(ctx)) {
			mangaTitle = HTMLWebUtils.jq_getInnerHTML(ctx, ".aname");
		} else if(isChapterPage(ctx)) {
			mangaTitle = (String) HTMLWebUtils.getElementById(ctx, "mangainfo").get("find('h2.c2 > a').html()");
			mangaTitle = mangaTitle.substring(0, mangaTitle.lastIndexOf("Manga")-1);
		}
		return mangaTitle;
	}
	
	public boolean isMangaPage(JavascriptContext ctx) {
		boolean isMangaPage = false;
		
		int len = ((Double) HTMLWebUtils.getElementByClass(ctx, "aname").get("length")).intValue();
		if(len > 0) {
			isMangaPage = true;
		}
		
		return isMangaPage;
	}
	
	public boolean isChapterPage(JavascriptContext ctx) {
		boolean isChapterPage = false;
		
		int len = ((Double) HTMLWebUtils.getElementById(ctx, "imgholder").get("length")).intValue();
		if(len > 0) {
			isChapterPage = true;
		}
		
		return isChapterPage;
	}
	
	public void removeManga(JavascriptContext ctx) {
		String mangaTitle = getMangaTitleOnPage(ctx);
		Manga manga = (Manga) Storage.getInstance().readObject(mangaTitle);
		removeManga(manga);
	}
	
	public Manga getMangaFromStorage(JavascriptContext ctx) {
		Manga manga = new Manga();
		String mangaTitle = getMangaTitleOnPage(ctx);
		manga = (Manga) Storage.getInstance().readObject(mangaTitle);
		return manga;
	}
	
	public Manga createManga(JavascriptContext ctx, String mangaCover) {
		Manga manga = new Manga();
		manga.setTitle(HTMLWebUtils.jq_getInnerHTML(ctx, ".aname"));
		manga.setAuthor((String) HTMLWebUtils.getElementById(ctx, "mangaproperties").get("find('table tr:nth-child(5) > td:nth-child(2)').html()"));
		manga.setArtist((String) HTMLWebUtils.getElementById(ctx, "mangaproperties").get("find('table tr:nth-child(6) > td:nth-child(2)').html()"));
		manga.setGenre((String) HTMLWebUtils.getElementById(ctx, "mangaproperties").get("find('table tr:nth-child(8) > td:nth-child(2) > a span.genretags').html()"));
		manga.setMangaDescription((String) HTMLWebUtils.getElementById(ctx, "readmangasum").get("find('p').html()"));
		mangaCover = (String) HTMLWebUtils.getElementById(ctx, "mangaimg").get("find('img').attr('src')");
		manga.setMangaCover(mangaCover.substring(mangaCover.lastIndexOf("/")+1));
		//manga.setChapters(getMangaChapters(ctx));
		manga.setLatestChapters(getLatestChapters(ctx));
		return manga;
	}
	
	public void addManga(JavascriptContext ctx) {
		Manga manga = new Manga();
		String mangaCover = "";
		if(isMangaPage(ctx)) {
			manga = createManga(ctx, mangaCover);
		}
		else if(isChapterPage(ctx)) {
			// check if manga is already added
			if(!isMangaAdded(ctx)) {
				String mangaUri = (String) HTMLWebUtils.getElementById(ctx, "mangainfo").get("find('h2.c2 > a').attr('href')");
				mangaUri = mangaUri.substring(mangaUri.indexOf("/")+1);
				JavascriptContext contx = HTTPWebUtils.getJSContextByUrl(getHomePageUri()+mangaUri);
				manga = createManga(contx, mangaCover);
			}
			else {
				manga = getMangaFromStorage(ctx);
			}
		}
		
		// make the covers directory
		createMangaDir();
		// save the Manga
		saveManga(manga, mangaCover);
	}
	
	public List<MangaChapter> getMangaChapters(JavascriptContext ctx) {
		List<MangaChapter> mangaList = new ArrayList<MangaChapter>();
		JSObject trs = (JSObject) HTMLWebUtils.getElementById(ctx, "listing").get("find('tr')");
		int trLength = ((Double) trs.get("length")).intValue();
		for(int x=2; x < trLength; x++) {
			MangaChapter chap = new MangaChapter();
			chap.setChapterNumber(x-1);
			String chapTitle = (String) HTMLWebUtils.getElementById(ctx, "listing").get("find('tr:nth-child("+x+") > td').html()");
			chapTitle = chapTitle.substring(chapTitle.indexOf(':') + 1).trim(); // http://jsfiddle.net/nick_craver/sRBsS/1/
			chap.setChapterTitle(chapTitle);
			chap.setNumPages(trLength);
			mangaList.add(chap);
		}
		return mangaList;
	}
	
	public List<Integer> getLatestChapters(JavascriptContext ctx) {
		List<Integer> lastestChaps = new ArrayList<Integer>();
		JSObject listItems = (JSObject) HTMLWebUtils.getElementById(ctx, "latestchapters").get("find('ul li')");
		int liLength = ((Double) listItems.get("length")).intValue();
		try {
			for(int x=1; x < liLength; x++) {
				String href = (String) HTMLWebUtils.getElementById(ctx, "latestchapters").get("find('ul li:nth-child("+x+") > a').attr('href')");
				href = href.substring(href.lastIndexOf("/")+1); // http://stackoverflow.com/questions/10549504/obtain-name-from-absolute-path-substring-from-last-slash-java-android
				lastestChaps.add(Integer.parseInt(href));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return lastestChaps;
	}
}
