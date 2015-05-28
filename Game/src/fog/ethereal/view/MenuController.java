package fog.ethereal.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import fog.ethereal.client.MainClient;
import fog.ethereal.util.LevelSaver;
import fog.ethereal.util.Mode;
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
	private RenameDialogController rdc;
	private Stage rdcStage, ndcStage;
	private NewDialogController ndc;
	
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
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(new URL("file:src/fog/ethereal/view/EditorLayout.fxml"));
			loader.setRoot(new AnchorPane());
			AnchorPane rootPane = (AnchorPane) loader.load();
			
			Scene scene = new Scene(rootPane);
			editStage.setScene(scene);
			editStage.setTitle("Level Editor: " + currentLevel.getName());
			editStage.getIcons().add(new Image("file:resources/assets/editicon.png"));
			
			EditorController ec = loader.getController();
			ec.setLevel(currentLevel);
			ec.setStage(editStage);
			editStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void newLevel() {
		getNDC().show();
	}
	
	public NewDialogController getNDC() {
		if(ndc != null) {
			return ndc;
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(new URL("file:src/fog/ethereal/view/NewDialog.fxml"));
				AnchorPane rootPane = (AnchorPane) loader.load();
				
				Scene scene = new Scene(rootPane);
				ndcStage = new Stage(StageStyle.TRANSPARENT);
				ndcStage.setScene(scene);
				ndcStage.setResizable(false);
				
				ndc = loader.getController();
				ndc.setup(this, ndcStage);
			} catch (Exception e) { e.printStackTrace(); }
			return ndc;
		}
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
		if(!levels.containsAll(levelTable.getItems()) || !levelTable.getItems().containsAll(levels))
			levelTable.getItems().setAll(levels);
	}
	
	@FXML
	public void showRenameDialog() {
		if(rdc == null) {
			setupRenameDialog();
		}
		rdc.show();
	}
	
	public void renameCurrent(String name) {
		LevelSaver.rename(currentLevel, name);
		updateLevels();
	}
	
	public void showLevelDetails(Level l) {
		setCurrentLevel(l);
		if(l != null) {
			currentLevelNameLabel.setText(l.getName());
			currentLevelBestTimeLabel.setText("Best Time: " + millisToString(l.getBestTime()));
			currentLevelImage.setImage(l.getImage());
			currentLevelImage.setFitWidth(380);
			currentLevelImage.setFitHeight(200);
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
	
	public void setCurrentLevel(Level l) {
		currentLevel = l;
	}
	
	public void setupRenameDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(new URL("file:src/fog/ethereal/view/RenameDialog.fxml"));
			BorderPane rootPane = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootPane);
			rdcStage = new Stage(StageStyle.TRANSPARENT);
			rdcStage.setScene(scene);
			rdcStage.setResizable(false);
			
			rdc = loader.getController();
			rdc.setup(this, rdcStage);
		} catch (IOException e) {
			e.printStackTrace();
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
	
	public static List<Image> getIcons(String name) {
		ArrayList<Image> icons = new ArrayList<>();
		try {
			for(int i = 1; i <= 4; i++) {
				icons.add(new Image("file:resources/assets" + name + i + ".png"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return icons;
	}
	
	
}
