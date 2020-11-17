package cf.moegirl.helloworld;

public class Math {
	/**
	 * 阶乘
	 *
	 * @param n
	 * @return
	 */
	public int factorial(int n) throws Exception {
		if (n < 0) {
			throw new Exception("负数没有阶乘");
		} else if (n <= 1) {
			return 1;
		} else {
			return n * factorial(n - 1);
		}
	}

	/**mvn
	 * 斐波那契数列
	 *
	 * @param n
	 * @return
	 */
	public int fibonacci(int n) throws Exception {
		if (n <= 0) {
			throw new Exception("斐波那契数列从第1位开始");
		} else if (n == 1) {
			return 0;
		} else if (n == 2) {
			return 1;
		} else {
			return fibonacci(n - 1) + fibonacci(n - 2);
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello, world.");
	}
}