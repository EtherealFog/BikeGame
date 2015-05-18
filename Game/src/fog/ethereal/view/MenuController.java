package fog.ethereal.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import fog.ethereal.util.LevelSaver;
import fog.ethereal.world.BikeWorld;
import fog.ethereal.world.Level;
import fog.ethereal.world.LevelData;
import fog.ethereal.world.World;

public class MenuController {
	@FXML
	private TableView<Level> levelTable;
	
	@FXML
	private TableColumn<Level, LevelData> levelColumn;
	
	@FXML
	private Label currentLevelNameLabel;
	
	@FXML
	private Label currentLevelBestTimeLabel;
	
	@FXML
	private ImageView currentLevelImage;
	
	private Level currentLevel;
	
	@FXML
	public void startGame() {
		Stage gameStage = new Stage();
		World world = new BikeWorld();
		world.initialize(gameStage);
		world.setupGameLoop();
		world.playGameLoop();
	}
	
	@FXML
	public void editLevel() {
		Stage editStage = new Stage();
		World world = new BikeWorld();
		world.initialize(editStage);
		world.setupEditLoop();
		world.playEditLoop();
	}
	
	@FXML
	public void newLevel() {
		
	}
	
	@FXML
	public void openLevel() {
		
	}
	
	@FXML
	private void initialize() {
		levelColumn.setCellValueFactory(new PropertyValueFactory<Level, LevelData>("data"));
		levelColumn.setCellFactory(new Callback<TableColumn<Level, LevelData>, TableCell<Level, LevelData>>() {
			@Override
			public TableCell<Level, LevelData> call(TableColumn<Level, LevelData> param) {
				TableCell<Level, LevelData> cell = new LevelTableCell();
				return cell;
			}
		});
		
		updateLevels();
		
		showLevelDetails(null);
		
		levelTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showLevelDetails(newValue));
	}
	
	@FXML
	public ImageView getCurrentImage() {
		return new ImageView(currentLevel.getImage());
	}
	
	@FXML
	public void updateLevels() {
		ArrayList<Level> levels = LevelSaver.getLevels();
		if(!levels.equals(levelTable.getItems()))
			levelTable.getItems().setAll(levels);
	}
	
	public void showLevelDetails(Level l) {
		if(l != null) {
			currentLevelNameLabel.setText(l.getName());
			currentLevelBestTimeLabel.setText("Best Time: " + millisToString(l.getBestTime()));
			currentLevelImage.setImage(l.getImage());
		} else {
			currentLevelNameLabel.setText("No Level Selected");
			currentLevelBestTimeLabel.setText("Best Time: --:--.--");
			try {
				currentLevelImage.setImage(new Image(new FileInputStream(new File("resources/assets/notfound.png"))));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String millisToString(long millis) {
		String temp ="";
		temp += millis / 60000 + ":";
		millis -= (millis / 60000) * 60000;
		temp += millis / 1000 + ".";
		millis -= (millis / 1000) * 1000;
		temp += millis / 10;
		return temp;
	}
}
