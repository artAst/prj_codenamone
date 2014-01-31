package userclasses.entitites;

import java.util.ArrayList;
import java.util.List;

import com.codename1.io.FileSystemStorage;
import com.codename1.io.Storage;
import com.codename1.io.services.ImageDownloadService;
import com.codename1.javascript.JavascriptContext;

public abstract class MangaWebsite {
	private String homePageUri;
	private String mangaListUri;
	private String popularMangaUri;
	private String mangaElemClassPath;
	private String mangaElemIdPath;
	private String mangaCoverPath = "mq_Covers/";
	
	public void createMangaDir() {
		if(!FileSystemStorage.getInstance().isDirectory("mq_Covers")) {
			FileSystemStorage.getInstance().mkdir("mq_Covers");
		}
	}
	
	public boolean isMangaAdded(JavascriptContext ctx) {
		return false;
	}
	
	public boolean isMangaAdded(List<String> mangaList, String title) {
		if(mangaList != null && mangaList.size() > 0) {
			if(!mangaList.contains(title)) {
				return false;
			}
		}
		else {
			if(mangaList == null) {
				mangaList = new ArrayList<String>();
			}
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public void saveManga(Manga manga, String mangaCover) {
		List<String> mangaList = (ArrayList<String>) Storage.getInstance().readObject("MyMangaQueue");
		if(!isMangaAdded(mangaList, manga.getTitle())) {
			if(!FileSystemStorage.getInstance().exists(getMangaCoverPath()+manga.getMangaCover()))
				ImageDownloadService.createImageToFileSystem(mangaCover, null, getMangaCoverPath()+manga.getMangaCover());
			if(mangaList == null)
				mangaList = new ArrayList<String>();
			mangaList.add(manga.getTitle());
			Storage.getInstance().writeObject(manga.getTitle(), manga);
			Storage.getInstance().writeObject("MyMangaQueue", mangaList);
		}
		else {
			Storage.getInstance().deleteStorageFile(manga.getTitle());
			Storage.getInstance().writeObject(manga.getTitle(), manga);
		}
	}
	
	public void addManga(JavascriptContext ctx) {
	}
	
	public void removeManga(JavascriptContext ctx) {}
	
	@SuppressWarnings("unchecked")
	public void removeManga(Manga manga) {
		List<String> mangaList = (ArrayList<String>) Storage.getInstance().readObject("MyMangaQueue");
		if(manga!= null && isMangaAdded(mangaList, manga.getTitle())) {
			if(FileSystemStorage.getInstance().exists(getMangaCoverPath()+manga.getMangaCover()))
				FileSystemStorage.getInstance().delete(getMangaCoverPath()+manga.getMangaCover());
			mangaList.remove(manga.getTitle());
			Storage.getInstance().deleteStorageFile(manga.getTitle());
			Storage.getInstance().writeObject("MyMangaQueue", mangaList);
		}
	}
	
	public boolean isMangaPage(JavascriptContext ctx) {
		return false;
	}
	
	public boolean isChapterPage(JavascriptContext ctx) {
		return false;
	}

	public String getHomePageUri() {
		return homePageUri;
	}

	public void setHomePageUri(String homePageUri) {
		this.homePageUri = homePageUri;
	}

	public String getMangaListUri() {
		return mangaListUri;
	}

	public void setMangaListUri(String mangaListUri) {
		this.mangaListUri = mangaListUri;
	}

	public String getPopularMangaUri() {
		return popularMangaUri;
	}

	public void setPopularMangaUri(String popularMangaUri) {
		this.popularMangaUri = popularMangaUri;
	}

	public String getMangaElemClassPath() {
		return mangaElemClassPath;
	}

	public void setMangaElemClassPath(String mangaElemClassPath) {
		this.mangaElemClassPath = mangaElemClassPath;
	}

	public String getMangaElemIdPath() {
		return mangaElemIdPath;
	}

	public void setMangaElemIdPath(String mangaElemIdPath) {
		this.mangaElemIdPath = mangaElemIdPath;
	}

	public String getMangaCoverPath() {
		return mangaCoverPath;
	}

	public void setMangaCoverPath(String mangaCoverPath) {
		this.mangaCoverPath = mangaCoverPath;
	}
}
