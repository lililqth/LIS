import java.util.HashSet;

public class LIS {
	public int[] input;
	private int[] end;
	private int[] prev;
	private int[] ans;
	private HashSet<Integer> index;
	public int len;
	public int find(int s, int e, int key){
		if (input[end[e]] < key){
			return e + 1;
		}
		while (s <= e){
			int mid = (s + e) >> 1;
			if (input[end[mid]] < key){
				s = mid + 1;
			}else{
				e = mid-1;
			}
		}
		return s;
	}
	public LIS(int[] a){
		input = a;
		if (a.length == 0){
			return;
		}
		end = new int[input.length+1];
		end[1] = 0;
		prev = new int[input.length];
	}
	public int getLISLength(){
		if (input.length == 0){
			return 0;
		}
		int pos = 0;
		len = 1;
		for (int i=1; i<input.length; i++){
			pos = find(1, len, input[i]);
			end[pos] = i;
			if (pos > 1){
				prev[i] = end[pos-1];
			}else{
				prev[i] = -1;
			}
			if (len < pos){
				len = pos;
			}
		}
		return len;
	}

	private int[] reverse(int[] arr){
		int s = 0, e = arr.length-1;
		while (s < e){
			int temp = arr[s];
			arr[s] = arr[e];
			arr[e] = temp;
			s++;
			e--;
		}
		return arr;
	}

	public int[] getLISArray(){
		if (input.length==0){
			return input;
		}
		ans = new int[len];
		index = new HashSet<Integer>();
		for (int i=end[len], j=0; j<len; ){
			index.add(i);
			ans[j++] = input[i];
			i = prev[i];
		}
		
		return reverse(ans);
	}
	public HashSet<Integer> getIndex(){
		return index;
	}
}
