package base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder>, Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Note> notes;
	private String name;
	
	public Folder(String name) {
		this.name = name;
		notes = new ArrayList<Note>();
	}
	
	public void addNote(Note newNote) {
		notes.add(newNote);
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Note> getNotes(){
		return notes;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Folder other = (Folder) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		int nText = 0;
		int nImage = 0;
		
		for(Note note: notes) {
			if(note instanceof TextNote) {
				nText++;
			}
			else {nImage++;}
		}
		
		return name + ":" + nText + ":" + nImage;
	}

	@Override
	public int compareTo(Folder o) {
		// TODO Auto-generated method stub
		return name.compareTo(o.name);
	}
	
	public void sortNotes() {
		Collections.sort(notes);
	}
	
	public List<Note> searchNotes(String keywords) {
		List<Note> foundNotes = new ArrayList<Note>();
		String[] keyword = keywords.toLowerCase().split(" ");
		
		List<String> orList = new ArrayList<String>();
		List<String> andList = new ArrayList<String>();
		
		int count = 0;
		while (count < keyword.length) {
			String str = "";
			if ((count+1 < keyword.length) && (keyword[count+1].equals("or"))) {
				while ((count+1 < keyword.length) && (keyword[count+1].equals("or"))) {
					str+= " " + keyword[count];
					count+=2;
				}
				str += " " + keyword[count];
				orList.add(str);
				count++;
			}else {
				andList.add(keyword[count]);
				count++;
			}
		}
		if (orList.size() > 0) {
			andList.addAll(orList);
		}
		
		for (Note note : getNotes()) {
			boolean found = false;
			boolean and_check = false;
			if (note instanceof ImageNote) {
				String img_title = note.getTitle().toLowerCase();
				for (String str : andList) {
					and_check = true;
					boolean or_check = false;
					String[] or_keys = str.trim().split(" ");
					for (int j=0; j < or_keys.length; j++) {
						if (img_title.contains(or_keys[j])) {
							or_check = true;
						}
					}
					found = (and_check && or_check);
				}
				if (found) {
					foundNotes.add(note);
				}
			}else if (note instanceof TextNote) {
				TextNote tNote = (TextNote)note;
				String txt_title = tNote.getTitle().toLowerCase();
				String txt_content = tNote.getContent().toLowerCase();
				for (String str : andList) {
					and_check = true;
					boolean or_check = false;
					String[] or_keys = str.trim().split(" ");
					for (int j=0; j < or_keys.length; j++) {
						if (txt_title.contains(or_keys[j]) || txt_content.contains(or_keys[j])) {
							or_check = true;
						}
					}
					found = (and_check && or_check);
				}
				if (found) {
					foundNotes.add(note);
				}
			}
		}
				
		return foundNotes;
	}
	
	public boolean removeNotes(String title) {
	   // TODO
	   // Given the title of the note, delete it from the folder.
	   // Return true if it is deleted successfully, otherwise return false. 
		for(Note n: notes) {
			if(n.getTitle().equals(title)) {
				notes.remove(n);
				return true;
			}
		}
		return false;
	}

}
