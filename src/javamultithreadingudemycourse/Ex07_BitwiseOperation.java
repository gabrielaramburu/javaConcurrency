package javamultithreadingudemycourse;

public class Ex07_BitwiseOperation {

	public static void main(String[] args) {
		bitWiseOp(4, 11, 8);
	}

	static void bitWiseOp(int a, int b, int c) {

		int d = a ^ a; //XNOR
		System.out.println(d);
		int e = c ^ b;
		System.out.println(e);
		int f = a & b; //AND
		System.out.println(f);
		int g = c | d; //OR
		System.out.println(c);
		e = ~e; //NOT
		System.out.println(e);

	}
}
