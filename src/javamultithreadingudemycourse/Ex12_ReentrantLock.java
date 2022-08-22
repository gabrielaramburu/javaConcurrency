	package javamultithreadingudemycourse;

import java.util.*;
import java.util.concurrent.*;	
import java.util.concurrent.locks.*;

public class Ex12_ReentrantLock {
	public static void main(String[] args) throws InterruptedException {

		PriceContainer container = new PriceContainer();

		Thread tPriceFetcher = new Thread (
			()-> {
				new PriceUpdater(container).fetchPrices();
			});

		Thread tPriceShower = new Thread (
			() -> {
				new PriceShower(container).showPrices();
			});

		tPriceFetcher.start();
		tPriceShower.start();

		tPriceFetcher.join();
		tPriceShower.join();
	}
}

class PriceContainer {
		Lock lock;

		private double priceProd1;
		private double priceProd2;
		private double priceProd3;

		PriceContainer() {
			this.lock = new ReentrantLock();
		}

		Lock getLock() {
			return lock;
		}

		double getPriceProd1() {
			return this.priceProd1;
		}

		double getPriceProd2() {
			return this.priceProd2;	
		}

		double getPriceProd3() {
			return this.priceProd3;	
		}

		void setPriceProd1(double price) {
			this.priceProd1 = price;
		}

		void setPriceProd2(double price) {
			this.priceProd2 = price;
		}

		void setPriceProd3(double price) {
			this.priceProd3 = price;
		}
	}

	class PriceUpdater {
		PriceContainer priceContainer;
		Random random;

		PriceUpdater(PriceContainer priceContainer) {
			this.random = new Random();
			this.priceContainer = priceContainer;
		}

		void fetchPrices() {
			while(true) {
				//It adquier the lock on the PriceContainer to ensure that no one else can
				//see the prices in an intermidiate state.
				//This is a very important feature of mutual exclution that I never think before.
				//This ensure atomicity.
				this.priceContainer.getLock().lock();
				try {
					System.out.println(Thread.currentThread().getName() 
						+ " Fetching prices...");
					this.priceContainer.setPriceProd1(random.nextInt(1000));
					this.priceContainer.setPriceProd2(random.nextInt(1000));
					this.priceContainer.setPriceProd3(random.nextInt(1000));
					pause(2000); //simulate some nextwork latency
				} finally {
					this.priceContainer.getLock().unlock();
					System.out.println(Thread.currentThread().getName() 
						+ " Fetching prices ends.");
				}
			}
		}

		void pause(long inter) {
			try {
				Thread.sleep(inter);	
			} catch (InterruptedException e) {
				System.out.println("error");
			}
		}
	}

	class PriceShower {
		PriceContainer priceContainer;

		PriceShower(PriceContainer container) {
			this.priceContainer = container;
		}

		void showPrices() {
			while(true) {
				System.out.println(
					System.currentTimeMillis() + " Trying to show most updated prices.");
				//this.priceContainer.getLock().lock();
				if (this.priceContainer.getLock().tryLock()) {
					try  {	
						System.out.format("Prices -> prod1: %s prod2: %s prod3: %s \n", 
						this.priceContainer.getPriceProd1(),
						this.priceContainer.getPriceProd2(),
						this.priceContainer.getPriceProd3());
					} finally {
						this.priceContainer.getLock().unlock();
					}	
				}
				
			}
		}
	}
