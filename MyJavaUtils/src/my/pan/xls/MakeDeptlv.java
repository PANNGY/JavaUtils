package my.pan.xls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class MakeDeptlv {

	public static Map deptlv(List<Branch> branches,Map<String,Branch> branchMap,String topbranch) throws InterruptedException{
		LinkedBlockingQueue queue = new LinkedBlockingQueue<Branch>();
		queue.addAll(branches);
		Map<String,Branch> temp = new HashMap<String,Branch>();
		while(!queue.isEmpty()){
			System.out.println(queue.size());
			Branch branvo = (Branch) queue.poll();
			if(branvo.getUpbrno().equals(topbranch)){
				branvo.setDeptlv(branvo.getBrno());
				temp.put(branvo.getBrno(), branvo);
				continue;
			}else if(!temp.containsKey(branvo.getUpbrno())){
				queue.put(branvo);
				continue;
			}else{
		        String deptlv = temp.get(branvo.getUpbrno()).getDeptlv()+","+branvo.getBrno();
			    branvo.setDeptlv(deptlv);
			    System.out.println(branvo);
			    temp.put(branvo.getBrno(), branvo);
			}
					
		}	
		return temp;
		
	}
}
