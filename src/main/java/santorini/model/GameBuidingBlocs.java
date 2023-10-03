package santorini.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameBuidingBlocs {
	private static final int LEVEL_ONE = 1;
	private static final int LEVEL_TWO = 2;
	private static final int LEVEL_THREE = 3;
	private static final int LEVEL_FOUR = 4;
	
	private int[] remainingBlocsCountArray;
	
	private int selectedBuildingBlocLevel;
	
	public GameBuidingBlocs(int levelOneRemainingBlocsCount, int levelTwoRemainingBlocsCount,
			int levelThreeRemainingBlocsCount, int domRemainingBlocsCount) {
		super();
		remainingBlocsCountArray = new int[] {levelOneRemainingBlocsCount, levelTwoRemainingBlocsCount, levelThreeRemainingBlocsCount, domRemainingBlocsCount};
	}
	
	private int getRemainingBlocCountByLevel(int level) {
		int arrayIndex = level - 1;
		return remainingBlocsCountArray[arrayIndex];
	}

	public int getLevelOneRemainingBlocsCount() {
		return getRemainingBlocCountByLevel(LEVEL_ONE);
	}

	public int getLevelTwoRemainingBlocsCount() {
		return getRemainingBlocCountByLevel(LEVEL_TWO);
	}

	public int getLevelThreeRemainingBlocsCount() {
		return getRemainingBlocCountByLevel(LEVEL_THREE);
	}
	
	public int getDomRemainingBlocsCount() {
		return getRemainingBlocCountByLevel(LEVEL_FOUR);
	}
	
	public boolean isBuildingBlocWithGivenLevelAvailable(int level) {		
		return getRemainingBlocCountByLevel(level) > 0;
	}
	
	public boolean isBuidingBlocSelected() {
		return selectedBuildingBlocLevel != 0;
	}
	
	public int getSelectedBuildingBlocLevel() {
		return selectedBuildingBlocLevel;
	}
	
	public void setSelectedBuildingBlocLevel(int selectedBuildingBlocLevel) {
		this.selectedBuildingBlocLevel = selectedBuildingBlocLevel;
	}

	public void decrementBuidingBlocsCountForLevel(int selectedBuildingBlocLevel) {
		int arrayIndex = selectedBuildingBlocLevel - 1;
		remainingBlocsCountArray[arrayIndex] = remainingBlocsCountArray[arrayIndex] - 1;
	}

	public List<Integer> getLevelsOfAvailableBuildingBlocs() {
		List<Integer> levels = new ArrayList<>();
		
		IntStream.range(0, remainingBlocsCountArray.length)
	     .filter(index -> remainingBlocsCountArray[index] > 0)
	     .forEach(index -> levels.add(index+1));
		
		return levels;
	}
}
