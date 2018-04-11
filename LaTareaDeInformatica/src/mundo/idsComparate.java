package mundo;

import java.util.Comparator;

public class idsComparate implements Comparator<Integer> {

	@Override
	public int compare(Integer o1, Integer o2) {
		// TODO Auto-generated method stub
		if(o1>o2) {
			return 0;
		}else {
			return 1;
		}
	
	}

	

}
