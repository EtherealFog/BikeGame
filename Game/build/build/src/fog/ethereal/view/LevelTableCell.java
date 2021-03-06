package fog.ethereal.view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import fog.ethereal.world.Level;
import fog.ethereal.world.LevelData;

public class LevelTableCell extends TableCell<Level, LevelData> {
	private VBox vb;
	private Label name;
	private ImageView thumbnail;
	
	public LevelTableCell() {
		vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		name = new Label();
		name.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		thumbnail = new ImageView();
		thumbnail.setFitWidth(200);
		thumbnail.setPreserveRatio(true);
		vb.getChildren().addAll(name, new Separator(Orientation.HORIZONTAL), thumbnail);
		setGraphic(vb);
	}
	
	@Override
	public void updateItem(LevelData l, boolean empty) {
		super.updateItem(l, empty);
		if(l != null) {
			name.setText(l.getName());
			thumbnail.setImage(l.getImage());
		}
	}
}
