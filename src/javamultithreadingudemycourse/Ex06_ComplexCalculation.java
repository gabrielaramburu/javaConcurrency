package javamultithreadingudemycourse;

import java.math.BigInteger;

public class Ex06_ComplexCalculation {

	public static void main(String[] args) throws InterruptedException {
		BigInteger result = calculateResult(new BigInteger("10"), new BigInteger("2"), new BigInteger("10"), new BigInteger("2"));
		System.out.println(result.toString());
	}

	public static BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2)
			throws InterruptedException {
		BigInteger result = BigInteger.ZERO;
		/*
		 * Calculate result = ( base1 ^ power1 ) + (base2 ^ power2). Where each
		 * calculation in (..) is calculated on a different thread
		 */
		PowerCalculatingThread t1 = new PowerCalculatingThread(base1, power1);
		PowerCalculatingThread t2 = new PowerCalculatingThread(base2, power2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		result = result.add(t1.getResult()).add(t2.getResult());
		return result;
	}

	private static class PowerCalculatingThread extends Thread {
		private BigInteger result = BigInteger.ONE;
		private BigInteger base;
		private BigInteger power;

		public PowerCalculatingThread(BigInteger base, BigInteger power) {
			this.base = base;
			this.power = power;
		}

		@Override
		public void run() {
			/*
			 * Implement the calculation of result = base ^ power
			 */
			this.result = this.base;
			for (BigInteger i = BigInteger.ONE; i.compareTo(this.power) < 0; i = i.add(BigInteger.ONE)) {
				result = result.multiply(this.base);
			}
			System.out.println(result.toString());
		}

		public BigInteger getResult() {
			return result;
		}
	}
}
