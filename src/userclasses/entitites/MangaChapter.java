package userclasses.entitites;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;

public class MangaChapter implements Externalizable {
	private String chapterTitle;
	private Integer chapterNumber;
	private Integer numPages;
	private List<String> pagesImgPath;
	
	static {
		Util.register("MangaChapter", MangaChapter.class);
	}
	
	public String getChapterTitle() {
		return chapterTitle;
	}
	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}
	public Integer getChapterNumber() {
		return chapterNumber;
	}
	public void setChapterNumber(Integer chapterNumber) {
		this.chapterNumber = chapterNumber;
	}
	public Integer getNumPages() {
		return numPages;
	}
	public void setNumPages(Integer numPages) {
		this.numPages = numPages;
	}
	public List<String> getPagesImgPath() {
		return pagesImgPath;
	}
	public void setPagesImgPath(List<String> pagesImgPath) {
		this.pagesImgPath = pagesImgPath;
	}
	public int getVersion() {
		// TODO Auto-generated method stub
		return 1;
	}
	public void externalize(DataOutputStream out) throws IOException {
		// TODO Auto-generated method stub
		Util.writeUTF(chapterTitle, out);
		out.write(chapterNumber);
		out.write(numPages);
		Util.writeObject(pagesImgPath, out);
	}
	@SuppressWarnings("unchecked")
	public void internalize(int version, DataInputStream in) throws IOException {
		// TODO Auto-generated method stub
		chapterTitle = Util.readUTF(in);
		chapterNumber = in.readInt();
		numPages = in.readInt();
		pagesImgPath = (List<String>) Util.readObject(in);
	}
	public String getObjectId() {
		// TODO Auto-generated method stub
		return "MangaChapter";
	}
}
