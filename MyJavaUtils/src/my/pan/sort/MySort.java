package my.pan.sort;

public class MySort {

	public static void main(String[] args) {
		int [] list = {1,2,7,9,3,8,4};
		do_sort(list);
		for(int k=0;k<list.length;k++){
			System.out.print(list[k]+",");
		}
	}
	/**
	 * 冒泡排序
	 * @param list
	 */
	public static void do_sort(int [] list){
		boolean flag = true;
		for(int i=0;i<list.length&&flag;i++){
			flag = false;
			for(int j=list.length-1;j>i;j--){
				if(list[j]<list[j-1]){
					int temp = list[j];
					list[j] = list[j-1];
					list[j-1] = temp;
					flag = true;
				}
			} 			
			if(!flag) return;
		}
		
	}
	
	
	
}
