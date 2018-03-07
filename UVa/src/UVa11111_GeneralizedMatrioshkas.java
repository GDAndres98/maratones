import java.util.*;
import java.io.*;

public class xUVa11111_GeneralizedMatrioshkas {

	public static void main(String[] args) throws IOException {
		try(BufferedReader kb= new BufferedReader(new InputStreamReader(System.in))){
			StringTokenizer st;
			for (String in=kb.readLine(); in!=null; in=kb.readLine() ) {
				st = new StringTokenizer(in.trim());
				ArrayList<Integer> num = new ArrayList<Integer>(); 
				int[] arr = new int[st.countTokens()];
				for (int i = 0; i < arr.length; i++) {
					arr[i] = Integer.parseInt(st.nextToken());
				}
						
//				while (st.hasMoreElements()){
//					char a = st.nextToken();
//				
//				}
				if(isValid(arr)) {
					System.out.println(":-) Matrioshka!");
				}
				else {
					System.out.println(":-( Try again.");
				}
			}
		}
		
	}

	private static boolean isValid(int[] arr) {
		return isValid2(0,arr.length-1,arr,arr[arr.length-1]);
	}

	private static boolean isValid2(int i, int j, int[] arr, int complete) {
		if(arr[i]+arr[j]!=0)
			return false;
		
		
		
		
		return false;
	}

}
