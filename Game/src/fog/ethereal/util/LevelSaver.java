package fog.ethereal.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import fog.ethereal.world.Level;
import fog.ethereal.world.SaveableLevel;

public class LevelSaver {
	public static void save(SaveableLevel l) {
		try {
			JAXBContext context = JAXBContext.newInstance(SaveableLevel.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			File file = new File("resources/worlds/" +l.getName() + "/");
			if(!file.exists()) {
				if(!file.mkdir())
					throw new Exception("Unable to create file.");
			}
			file = new File(file.getPath() + "/level.xml");
			m.marshal(l, file);
		} catch (Exception e) {e.printStackTrace(System.err);}
	}
	
	public static SaveableLevel load(String name) {
		return load(new File("resources/worlds/" + name));
	}
	
	public static SaveableLevel load(File path) {
		SaveableLevel temp = null;
		try {
			JAXBContext context = JAXBContext.newInstance(SaveableLevel.class);
			Unmarshaller um = context.createUnmarshaller();
			File file = new File(path.getPath() + "/level.xml");
			if(!file.exists()) {
				throw new IllegalArgumentException("Invalid World File (Selected folder does not contain necessary level.xml file)");
			}
			temp = (SaveableLevel)um.unmarshal(file);
			
		} catch (Exception e) {e.printStackTrace(System.err);}
		return temp;
	}
	
	public static ArrayList<Level> getLevels() {
		File[] unsrt = (new File("resources/worlds/")).listFiles();
		ArrayList<Level> levels = new ArrayList<>();
		for(File f: unsrt) {
			File t = new File(f.getPath() + "/level.xml");
			if(f.isDirectory() && t.exists()) {
				levels.add(new Level(load(f)));
			}
		}
		return levels;
	}
	
	public static void rename(String prev, String newname) {
		Path old = new File("resources/worlds/" + prev).toPath();
		Path next = old.resolveSibling(newname);
		try {
			Files.move(old, next, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SaveableLevel temp = load(next.toFile());
		temp.setName(newname);
		save(temp);
	}
	
	public static void rename(Level prev, String newname) {
		rename(prev.getName(), newname);
	}
}
