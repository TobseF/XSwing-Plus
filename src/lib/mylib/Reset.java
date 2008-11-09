/*
 * @version 0.0 01.06.2008
 * @author 	Tobse F
 */
package lib.mylib;

import java.util.ArrayList;
import java.util.List;


public class Reset implements Resetable{
	List<Resetable> list=new ArrayList<Resetable>();
	
	public void add(Resetable resetableObject){
		list.add(resetableObject);
	}
	
	/** Resets all Elements	 */
	public void reset(){
		for(int i=0;i<list.size();i++){
			list.get(i).reset();
		}
	}
}
