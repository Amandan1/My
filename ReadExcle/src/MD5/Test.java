package MD5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractSpinnerModel;

public class Test {
	  public static void main(String[] args) {
	       List<Integer> intList = new ArrayList<Integer>();
	       Collections.addAll(intList, 1, 2, 3, 5, 6);
	       // for循环优化写法，只获取一次长度
	       for(int i = 0, size = intList.size(); i < size; i++) {
	           Integer value = intList.get(i);
	           // 符合条件，删除元素
	           if(value == 3 || value == 5) {
	              intList.remove(i);
	           }
	       }
	       System.out.println(intList);
	    }
}
