package fog.ethereal.world;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class LevelData {
	private StringProperty nameProperty;
	private LongProperty bestTimeProperty;
	private ObjectProperty<Image> imageProperty;
	
	public LevelData(String name, long time, Image image) {
		setName(name);
		setBestTime(time);
		setImage(image);
	}
	
	public StringProperty nameProperty() {
		if(nameProperty == null) 
			nameProperty = new SimpleStringProperty(this, "name");
		return nameProperty;
	}
	
	public LongProperty bestTimeProperty() {
		if(bestTimeProperty == null)
			bestTimeProperty = new SimpleLongProperty(this, "bestTime");
		return bestTimeProperty;
	}
	
	public ObjectProperty<Image> imageProperty() {
		if(imageProperty == null)
			imageProperty = new SimpleObjectProperty<Image>(this, "image");
		return imageProperty;
	}
	
	public void setName(String name) {
		nameProperty().set(name);
	}
	
	public void setBestTime(long time) {
		bestTimeProperty().set(time);
	}
	
	public void setImage(Image image) {
		imageProperty().set(image);
	}
	
	public String getName() {
		return nameProperty().get();
	}
	
	public long getBestTime() {
		return bestTimeProperty().get();
	}
	
	public Image getImage() {
		return imageProperty().get();
	}
	
}
