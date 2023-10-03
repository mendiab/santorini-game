package santorini.gui;

import javafx.fxml.FXML;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import santorini.service.IGameBuildingBlocksView;
import santorini.service.IGameService;
import santorini.service.impl.GameServiceImpl;

public class GameBuildingBlocksView implements IGameBuildingBlocksView {
	
	@FXML
	private ImageView levelOneBuidingBlock;
	
	@FXML
	private Text levelOneRemainingBlocksCount;
	
	@FXML
	private ImageView levelTwoBuidingBlock;
	
	@FXML
	private Text levelTwoRemainingBlocksCount;
	
	@FXML
	private ImageView levelThreeBuidingBlock;
	
	@FXML
	private Text levelThreeRemainingBlocksCount;
	
	@FXML
	private ImageView domBuidingBlock;
	
	@FXML
	private Text domRemainingBlocksCount;
	
	private IGameService gameService;
	
	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		gameService = GameServiceImpl.getInstance();
		levelOneRemainingBlocksCount.setText(String.valueOf(gameService.getLevelOneRemainingBlocksCount()));
		levelTwoRemainingBlocksCount.setText(String.valueOf(gameService.getLevelTwoRemainingBlocksCount()));
		levelThreeRemainingBlocksCount.setText(String.valueOf(gameService.getLevelThreeRemainingBlocksCount()));
		domRemainingBlocksCount.setText(String.valueOf(gameService.getDomRemainingBlocksCount()));
	}
	
	public void updateRemainingBlocksCountOfSelectedLevel(int level) {
		switch(level) {
		case 1: 
			updateLevelOneRemainingBlocksCount();
			break;
		case 2: 
			updateLevelTwoRemainingBlocksCount();
			break;
		case 3: 
			updateLevelThreeRemainingBlocksCount();
			break;
		case 4: 
			updateDomRemainingBlocksCount();
			break;
		}
	}
	
	@Override
	public void updateLevelOneRemainingBlocksCount() {
		int count = this.gameService.getLevelOneRemainingBlocksCount();
		levelOneRemainingBlocksCount.setText(String.valueOf(count));
	}
	
	@Override
	public void updateLevelTwoRemainingBlocksCount() {
		int count = this.gameService.getLevelTwoRemainingBlocksCount();
		levelTwoRemainingBlocksCount.setText(String.valueOf(count));
	}
	
	@Override
	public void updateLevelThreeRemainingBlocksCount() {
		int count = this.gameService.getLevelThreeRemainingBlocksCount();
		levelThreeRemainingBlocksCount.setText(String.valueOf(count));
	}
	
	@Override
	public void updateDomRemainingBlocksCount() {
		int count = this.gameService.getDomRemainingBlocksCount();
		domRemainingBlocksCount.setText(String.valueOf(count));
	}
	
	@FXML
	public void onLevelOneBuidingBlockClick() {
		gameService.handleLevelOneBuildingBlockSelection();
	}
	
	@FXML
	public void onLevelTwoBuidingBlockClick() {
		gameService.handleLevelTwoBuildingBlockSelection();
	}
	
	@FXML
	public void onLevelThreeBuidingBlockClick() {
		gameService.handleLevelThreeBuildingBlockSelection();
	}
	
	@FXML
	public void onDomBuildingBlockClick() {
		gameService.handleDomBuildingBlockSelection();
	}

	@Override
	public void markSelectedBuidingBlocAsSelected(int level) {
		InnerShadow ds = new InnerShadow(20, Color.RED);
		switch(level) {
		case 1:
			levelOneBuidingBlock.setEffect(ds);
			break;
		case 2:
			levelTwoBuidingBlock.setEffect(ds);
			break;
		case 3:
			levelThreeBuidingBlock.setEffect(ds);
			break;
		case 4:
			domBuidingBlock.setEffect(ds);
			break;
		}
		
	}
	
	public void markSelectedBuidingBlocAsDisabled(int level) {
		switch(level) {
		case 1:
			levelOneBuidingBlock.setEffect(null);
			break;
		case 2:
			levelTwoBuidingBlock.setEffect(null);
			break;
		case 3:
			levelThreeBuidingBlock.setEffect(null);
			break;
		case 4:
			domBuidingBlock.setEffect(null);
			break;
		}
	}

	public ImageView getSelectedBuildingBloc(int level) {
		
		Image image = null;
		
		switch(level) {
		case 1:
			image = levelOneBuidingBlock.getImage();
			break;
		case 2:
			image = levelTwoBuidingBlock.getImage();
			break;
		case 3:
			image = levelThreeBuidingBlock.getImage();
			break;
		case 4:
			image = domBuidingBlock.getImage();
			break;
		}
		
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(120);
		imageView.setFitHeight(120);
		return imageView;
	}

}
