package base;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteBook implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Folder> folders;
	
	public NoteBook() {folders = new ArrayList<Folder>();}
	
	public boolean createTextNote(String folderName, String title) {
		TextNote note = new TextNote(title);
		return insertNote(folderName, note);
	}
	

	public boolean createTextNote(String folderName, String title, String content) {
		TextNote note = new TextNote(title, content);
		return insertNote(folderName, note);
	}
	
	public boolean createImageNote(String folderName, String title) {
		ImageNote note = new ImageNote(title);
		return insertNote(folderName, note);
	}
	
	public ArrayList<Folder> getFolders() {return folders;}
	
	public boolean insertNote(String folderName, Note note) {
		Folder f = null;
		for(Folder f1: folders) {
			if (f1.getName() == folderName) {f = f1;}
		}
		if (f == null) {
			f = new Folder(folderName);
			folders.add(f);
		}
		
		for (Note n:f.getNotes()) {
			if(n.equals(note)) {
				System.out.println("Creating note " + note.getTitle() + " under folder " + folderName + " failed");
				return false;
			}
		}
		f.addNote(note);
		return true;
	}
	
	public void sortFolders() {
		for(Folder f: folders) {
			f.sortNotes();
		}
		Collections.sort(folders);
	}
	
	public List<Note> searchNotes(String keywords){
		List<Note> found_notes = new ArrayList<Note>();
		for (Folder f: folders) {
			found_notes.addAll(f.searchNotes(keywords));
		}
		return found_notes;
	}
	
	public boolean save(String file){
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
