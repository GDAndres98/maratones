import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class xUVa11080_PlaceTheGuards {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		int n = Integer.parseInt(in.readLine());
		StringTokenizer st;
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(in.readLine());
			int v = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			ArrayList<Integer>[] x = new ArrayList[v];
			ArrayList<HashSet<Integer>> set = new ArrayList<>();
			int color[] = new int[v];
			for (int j = 0; j < v; j++) {
				x[j] = new ArrayList<Integer>();
			}
			for (int j = 0; j < e; j++) {
				st = new StringTokenizer(in.readLine());
				int o = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				x[o].add(d);
				x[d].add(o);
				for (int k = 0; k <= set.size(); k++) {
					if (k == set.size()) {
						set.add(new HashSet<Integer>());
						set.get(k).add(o);
						set.get(k).add(d);
						break;
					} else {
						if (set.get(k).contains(o) || set.get(k).contains(d)) {
							set.get(k).add(o);
							set.get(k).add(d);
							break;
						}
					}
				}
			}

			if (esBic(x, color)) {
				// System.out.println(Arrays.toString(color));
				int contTotal = 0;
				int cont1 = 0;
				int cont2 = 0;
				for (int k = 0; k < set.size(); k++) {
					for (int l : set.get(k)) {
						if (color[l] == -1)
							cont2++;
						else if (color[l] == 1)
							cont1++;
					}
					if (cont2 > 0)
						cont1 = Math.min(cont1, cont2);

					contTotal += cont1;
					cont1 = 0;
					cont2 = 0;
				}
				for (int j = 0; j < color.length; j++) {
					if (color[j] == 0)
						contTotal++;
				}
				out.println(contTotal);

				// for (int j = 0; j < color.length; j++) {
				// if (color[j] == -1)
				// cont2++;
				// else if (color[j] == 1)
				// cont1++;
				// else
				// cont0++;
				// }
				//
				// if (cont2 > 0)
				// cont1 = Math.min(cont1, cont2);
				// out.println(cont1 + cont0);

			} else
				out.println(-1);

		}

		out.close();
	}

	// 13 10
	// 0 5
	// 5 4
	// 5 3
	// 5 2
	// 5 1
	// 5 6
	// 7 8
	// 7 9
	// 7 10
	// 7 11

	static boolean esBic(ArrayList<Integer>[] lAdy, int[] color) {
		for (int i = 0, N = lAdy.length; i < N; i++)
			if (color[i] == 0 && !esBic(lAdy, color, i, 1))
				return false;
		return true;
	}

	private static boolean esBic(ArrayList<Integer>[] lAdy, int[] color, int v, int cl) {
		color[v] = cl;
		if (lAdy[v].size() == 0)
			color[v] = 0;
		for (int u : lAdy[v])
			if (color[u] == color[v] || (color[u] == 0 && !esBic(lAdy, color, u, cl * -1)))
				return false;
		return true;
	}

}
