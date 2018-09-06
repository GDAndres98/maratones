import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class xUVa13254_Fish {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		StringTokenizer st;
		ArrayList<double[]> A = new ArrayList();
		ArrayList<double[]> B = new ArrayList();
		double[][] convexA, convexB; 
		double[][] convexFin;
		int n;
		int r;
		
		
//		ArrayList<double[]> rec = new ArrayList<>();
//		rec.add(new double[] {0,9});
//		rec.add(new double[] {3,2});
//		rec.add(new double[] {2,4});
//		rec.add(new double[] {5,0});
//		
//		double[][] c = convexHullGS(rec);
//		
//		System.out.println(c.length);
		
		
		
		while (true) {
			
			st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			r = Integer.parseInt(st.nextToken()) + 1;
			
			if(n == 0) break;
			A.clear();
			B.clear();
			for (int i = 0; i < n; i++) {
				st = new StringTokenizer(in.readLine());
				double x = Integer.parseInt(st.nextToken()) + r;
				double y = Integer.parseInt(st.nextToken()) + r;
				if (st.nextToken().charAt(0)=='A')
					A.add(new double[] { x, y });
				else
					B.add(new double[] { -y, -x });
			}
			if(A.size()+B.size()==2) {
				out.println("FEED");
				continue;
			}
			convexA = convexHullGS(A);
			convexB = convexHullGS(B);
//			System.out.println(convexA.length);
//			System.out.println(convexB.length);
			
			if(B.size()==1) {
				out.println(dentroPoligono(convexA, new double[] {-B.get(0)[1],-B.get(0)[0]}, true) ? "NOT YET" : "FEED");
				continue;			
			}
			if(A.size()==1) {
				out.println(dentroPoligono(convexB, new double[] {-A.get(0)[1],-A.get(0)[0]}, true) ? "NOT YET" : "FEED");
				continue;
			}

//			for (int i = 0; i < convexA.length; i++) {
//				System.out.println(Arrays.toString(convexA[i]));
//			}
//			System.out.println();
//			for (int i = 0; i < convexB.length; i++) {
//				System.out.println(Arrays.toString(convexB[i]));
//			}
			
			if(convexA.length == 3 && convexB.length == 3) {
				out.println(intSegmentos(convexA[0][0], convexA[0][1], convexA[1][0], convexA[1][1], -convexB[0][1], -convexB[0][0], -convexB[1][1], -convexB[1][0]) == null? "FEED": "NOT YET");
			}
			else if(convexA.length == 3) {
				boolean o = true;
				for(int i = 1; i < convexB.length && o; i++) {
					if(intSegmentos(convexA[0][0], convexA[0][1], convexA[1][0], convexA[1][1], -convexB[i][1],
							-convexB[i][0], -convexB[i-1][1], -convexB[i-1][0]) != null)
						o = false;
				}
				if(o) out.println("FEED");
				else out.println("NOT YET");
			}
			else if(convexB.length == 3) {
				boolean o = true;
				for(int i = 1; i < convexA.length && o; i++) {
					if(intSegmentos(-convexB[0][1], -convexB[0][0], -convexB[1][1], -convexB[1][0], convexA[i][0],
							convexA[i][1], convexA[i-1][0], convexA[i-1][1]) != null)
						o = false;
				}
				if(o) out.println("FEED");
				else out.println("NOT YET");
			}
			else {
				convexFin = minkowsky(convexA, convexB);

				out.println(dentroPoligono(convexFin, new double[] { 0, 0 }, true) ? "NOT YET" : "FEED");
			}
			
		}
		out.close();
	}

	
	public static double ds(double[] a, double[] b) {
		return (b[0]-a[0])*(b[0]-a[0])+(b[1]-a[1])*(b[1]-a[1]);
	}
	
	public static double distPL(double[] p1, double[] p2, double[] p) {
		return Math.abs((p2[0]-p1[0]) * (p1[1]-p[1])-(p2[1]-p1[1])*(p1[0]-p[0]))/Math.sqrt(ds(p1, p2));
	}
	public static double distPS(double[] p1, double[] p2, double[] p) {
		double dP = ds(p1, p2), d1 = ds(p1, p), d2 = ds(p2,  p);
		return (d2 + dP < d1 || d1 + dP < d2)? Math.sqrt(Math.min(d1, d2)): distPL(p1, p2, p);
	}
	public static double[] intLineas(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double xa = x2-x1, xb = x4-x3, xc = x1-x3, ya=y2-y1, yb = y4-y3, yc = y1-y3, d = yb*xa-xb*ya, n = xb*yc - yb*xc;
		return Math.abs(d)<1e-11? null: new double[] {x1+xa*n/d, y1 + ya*n/d};
	}
	
	public static double[] intSegmentos(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double[] p1 = {x1, y1}, p2 = {x2, y2}, p3 = {x3, y3}, p4 = {x4, y4}, p12[] = {p1,p2}, p34[] = {p3, p4};
		for(double[] p: p12) if(distPS(p3, p4, p) < 1e-11) return p;
		for(double[] p: p34) if(distPS(p1, p2, p) < 1e-11) return p;
		double[] p = intLineas(x1, y1, x2, y2, x3, y3, x4, y4);
		return p != null && distPS(p1, p2, p) < 1e-11 &&distPS(p3, p4, p) < 1e-11? p: null;
		
	}
	static int sgn(double x) {
		return Math.abs(x) > 9.9e-12 ? (x < 0 ? -1 : 1) : 0;
	}

	static double cruz(double[] b2, double[] b) {
		return b2[0] * b[1] - b2[1] * b[0];
	}

	static double cruz(double[] a, double[] b, double[] c) {
		return cruz(a, b) + cruz(b, c) + cruz(c, a);
	}

	static double[][] convexHullGS(ArrayList<double[]> pt) {
		int h = pt.size(), t = 0;
		double v[] = null, r[][] = new double[h][];
		for (double[] p : pt)
			if (v == null || p[1] < v[1] || (p[1] == v[1] && p[0] > v[0]))
				v = p;
		v = v.clone();
		for (double[] p : pt) {
			p[0] -= v[0];
			p[1] -= v[1];
		}
		Collections.sort(pt, new Comparator<double[]>() {
			public int compare(double[] a, double[] b) {
				double cz = cruz(b, a);
				return sgn(sgn(cz) != 0 ? cz : a[0] * a[0] + a[1] * a[1] - b[0] * b[0] - b[1] * b[1]);
			}
		});

		for (double[] p : pt) {
			while (t >= 2 && sgn(cruz(r[t - 1], p, r[t - 2])) < 1)
				t--;
			r[t++] = p;
		}
		for (double[] p : pt) {
			p[0] += v[0];
			p[1] += v[1];
		}

		int k = 0;

		for (int j = 1; j < t; j++)
			if (r[k][1] > r[j][1]) {
				k = j;
			} else if (r[k][1] == r[j][1] && r[k][0] > r[j][0]) {
				k = j;
			}
		double[][] r2 = new double[t + 1][2];
		for (int j = 0, indexR = k; j < t; j++, indexR++) {
			r2[j] = r[indexR];

			if (!(indexR + 1 < t))
				indexR = -1;
		}
		r2[t] = r2[0];

		return r2;
	}


	static boolean dentroPoligono(double[][] pt, double[] p, boolean bd) { // bd: con borde?
		boolean b = false;
		for (int i = 0, j = 1, t = pt.length; i < t; i++, j = j + 1 == t ? 0 : j + 1) {
			if (distPS(pt[i], pt[j], p) < 1e-11)
				return bd;
			if (((pt[j][1] <= p[1] && p[1] < pt[i][1]) || (pt[i][1] <= p[1] && p[1] < pt[j][1]))
					&& (p[0] - pt[j][0] < (p[1] - pt[j][1]) * (pt[i][0] - pt[j][0]) / (pt[i][1] - pt[j][1])))
				b = !b;
		}
		return b;
	}

	public static double[][] minkowsky(double[][] a, double[][] b) {
		double[][] mink = new double[a.length + b.length - 2][2];

		double ang1 = ang(a[0], a[1]);
		double ang2 = ang(b[0], b[1]);
		
		Set<double[]> set = new HashSet<>();
		for(int i = 0; i < a.length; i++)
			for(int j = 0; j < b.length; j++)
				set.add(new double[] {a[i][0] + b[j][0], a[i][1] + b[j][1]});	
		

		for (int k = 0, i = 0, j = 0; k < mink.length; k++) {
			mink[k][0] = a[i][0] + b[j][0];
			mink[k][1] = a[i][1] + b[j][1];

			if (ang1 < ang2) {
				if (i < a.length - 1) {
					i++;
					if (i + 1 < a.length)
						ang1 = ang(a[i], a[i + 1]);
				} else {
					ang1 = 1000;
					k--;
				}
			} else {
				if (j < b.length - 1) {
					j++;
					if (j + 1 < b.length)
						ang2 = ang(b[j], b[j + 1]);
				} else {
					ang2 = 1000;
					k--;
				}
			}

		}

		return mink;
	}

	private static double ang(double[] a, double[] b) {
		double x = Math.abs(a[0] - b[0]);
		double y = Math.abs(a[1] - b[1]);
		double hip = Math.hypot(x, y);

		if (b[0] >= a[0]) {
			if (b[1] >= a[1])
				return Math.asin(y / hip);
			return Math.asin(x / hip) + (3 * Math.PI) / 2;
		} else {
			if (b[1] >= a[1])
				return Math.asin(x / hip) + Math.PI / 2;
			return Math.asin(y / hip) + Math.PI;
		}
	}
}