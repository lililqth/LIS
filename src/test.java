import java.util.Scanner;

public class test {
	public static void main(String[] argc){
		//int[] input = {5,6,7,1,2,8};
		int[] input = {2,1,3,0,4,1,5,2,7};
		//int[] input = {0,2,1,5,3,6,4,8,9,7};
		LIS lis = new LIS(input);
		int length = lis.getLISLength();
		System.out.println(length);
		int[] ans = lis.getLISArray();
		for (int i=0; i<length; i++){
			System.out.print(ans[i]);
		}
	}
}
