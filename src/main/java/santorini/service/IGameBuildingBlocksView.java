package santorini.service;

public interface IGameBuildingBlocksView {

	void updateLevelOneRemainingBlocksCount();

	void updateLevelTwoRemainingBlocksCount();

	void updateLevelThreeRemainingBlocksCount();

	void updateDomRemainingBlocksCount();
	
	void markSelectedBuidingBlocAsSelected(int level);

}
