package GUI.Helpers.Searcher;

import java.util.Comparator;

public class OptionListComparator implements Comparator<Integer> {

	@Override
	public int compare(Integer anInteger, Integer anotherInteger) {
		if (anInteger.intValue() > anotherInteger.intValue()) {
			return 1;
		}else if(anInteger.intValue() == anotherInteger.intValue()){
			return 0;
		}else{
			return -1;
		}
	}
	
}
