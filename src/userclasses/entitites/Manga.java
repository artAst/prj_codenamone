package userclasses.entitites;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;

public class Manga implements Externalizable {
	private String title;
	private String author;
	private String artist;
	private String genre;
	private String mangaDescription;
	private String mangaCover;
	private List<Integer> latestChapters;
	private List<MangaChapter> chapters;
	
	static {
		Util.register("Manga", Manga.class);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getMangaDescription() {
		return mangaDescription;
	}
	public void setMangaDescription(String mangaDescription) {
		this.mangaDescription = mangaDescription;
	}
	public String getMangaCover() {
		return mangaCover;
	}
	public void setMangaCover(String mangaCover) {
		this.mangaCover = mangaCover;
	}
	public List<Integer> getLatestChapters() {
		return latestChapters;
	}
	public void setLatestChapters(List<Integer> latestChapters) {
		this.latestChapters = latestChapters;
	}
	public List<MangaChapter> getChapters() {
		return chapters;
	}
	public void setChapters(List<MangaChapter> chapters) {
		this.chapters = chapters;
	}
	public int getVersion() {
		// TODO Auto-generated method stub
		return 1;
	}
	public void externalize(DataOutputStream out) throws IOException {
		Util.writeUTF(title, out);
		Util.writeUTF(author, out);
		Util.writeUTF(artist, out);
		Util.writeUTF(genre, out);
		Util.writeUTF(mangaDescription, out);
		Util.writeUTF(mangaCover, out);
		Util.writeObject(latestChapters, out);
		Util.writeObject(chapters, out);
	}
	
	@SuppressWarnings("unchecked")
	public void internalize(int version, DataInputStream in) throws IOException {
		// TODO Auto-generated method stub
		title = Util.readUTF(in);
		author = Util.readUTF(in);
		artist = Util.readUTF(in);
		genre = Util.readUTF(in);
		mangaDescription = Util.readUTF(in);
		mangaCover = Util.readUTF(in);
		latestChapters = (List<Integer>) Util.readObject(in);
		chapters = (List<MangaChapter>) Util.readObject(in);
	}
	public String getObjectId() {
		// TODO Auto-generated method stub
		return "Manga";
	}
}
