package fog.ethereal.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import fog.ethereal.world.Level;

public class LevelTableCell extends TableCell<Level, Level> {
	private VBox vb;
	private Label name;
	private ImageView thumbnail;
	
	public LevelTableCell() {
		vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		name = new Label();
		name.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		thumbnail = new ImageView();
		thumbnail.setFitWidth(180);
		thumbnail.setFitHeight(100);
		vb.getChildren().addAll(name, thumbnail);
		setGraphic(vb);
	}
	
	@Override
	public void updateItem(Level l, boolean empty) {
		if(l != null) {
			name.setText(l.getName());
			thumbnail.setImage(l.getImage());
		}
	}
}
