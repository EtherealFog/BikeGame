package fog.ethereal.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import fog.ethereal.world.*;

public class LevelSaver {
	public static void save(SaveableLevel l) {
		try {
			JAXBContext context = JAXBContext.newInstance(SaveableLevel.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			File file = new File("resources/worlds/" +l.getName() + ".xml");
			
			m.marshal(l, file);
		} catch (Exception e) {e.printStackTrace(System.out);};
	}
	
	public static void load(SaveableLevel l) {
		
	}
}
