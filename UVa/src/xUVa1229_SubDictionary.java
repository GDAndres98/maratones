import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class xUVa1229_SubDictionary {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		int n;
		String[] dic;
		Set<String> def[];
		StringTokenizer st;
		while ((n = Integer.parseInt(in.readLine())) != 0) {
			dic = new String[n];
			def = new HashSet[n];
			for (int i = 0; i < n; i++) {
				st = new StringTokenizer(in.readLine());
				def[i] = new HashSet();
				dic[i] = st.nextToken();
				while (st.hasMoreTokens())
					def[i].add(st.nextToken());
			}
			
			
			
			
			

		}
		out.close();

	}
}
