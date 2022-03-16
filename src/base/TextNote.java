package base;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextNote extends Note{
	private static final long serialVersionUID = 1L;
	private String content;
	
	public TextNote(String title) {
		super(title);
	}
	
	public TextNote(String title, String content) {
		super(title);
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	private String getTextFromFile(String absolutePath) {
		String result = "";
		try {
            result = new String (Files.readAllBytes(Paths.get(absolutePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
		return result;
	}

	public void exportTextToFile(String pathFolder) {
        if(pathFolder == ""){
        	pathFolder = ".";
        }
		File file = new File(pathFolder + File.separator + this.getTitle().replaceAll(" ", "_") + ".txt");
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(this.content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
