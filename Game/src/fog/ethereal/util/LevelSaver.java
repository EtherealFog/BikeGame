package fog.ethereal.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
}
