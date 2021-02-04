/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arst.concprg.prodcons;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author hcadavid
 */
public class Producer extends Thread {
    private Queue<Integer> queue = null;
    private int dataSeed = 0;
    private Random rand=null;
    private final long stockLimit;
    public Producer(Queue<Integer> queue,long stockLimit) {
        this.queue = queue;
        rand = new Random(System.currentTimeMillis());
        this.stockLimit=stockLimit;
    }
    public long getStockLimit() {
		return stockLimit;
	}
    @Override
    public void run() {
        fastProduction(); 
    }
    public void originalProduction() {
		while (true) {
			dataSeed = dataSeed + rand.nextInt(100);
			System.out.println("Producer added " + dataSeed);
			queue.add(dataSeed);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
    public void optimalProduction() {
		synchronized (queue) {
			while (true) {
				if (queue.size() == 0) {
					dataSeed = dataSeed + rand.nextInt(100);
					System.out.println("Producer added " + dataSeed);
					queue.add(dataSeed);
					queue.notify();
				} else {
					try {
						queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}
    public void fastProduction() {
		while (true) {
			if (queue.size() < 10) {
				dataSeed = dataSeed + rand.nextInt(100);
				System.out.println("Producer added " + dataSeed);
				queue.add(dataSeed);				
			}
		}
	}
}