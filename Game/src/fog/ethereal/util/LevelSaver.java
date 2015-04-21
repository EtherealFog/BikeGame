package fog.ethereal.util;

import java.io.File;

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
			
			File file = new File("resources/worlds/" +l.getName() + ".xml");
			
			m.marshal(l, file);
		} catch (Exception e) {e.printStackTrace(System.err);}
	}
	
	public static SaveableLevel load(String name) {
		SaveableLevel temp = null;
		try {
			JAXBContext context = JAXBContext.newInstance(SaveableLevel.class);
			Unmarshaller um = context.createUnmarshaller();
			temp = (SaveableLevel)um.unmarshal(new File("resources/worlds/" + name + ".xml"));
			
		} catch (Exception e) {e.printStackTrace(System.err);}
		return temp;
	}
}
