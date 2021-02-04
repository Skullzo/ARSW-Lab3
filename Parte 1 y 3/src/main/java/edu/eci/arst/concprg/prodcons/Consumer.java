/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arst.concprg.prodcons;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author hcadavid
 */
public class Consumer extends Thread{   
    private Queue<Integer> queue;
    private int consumerStockLimit;   
    public Consumer(Queue<Integer> queue, long stockLimit){
        this.queue=queue; 
        consumerStockLimit = (int) stockLimit;
    }
    @Override
    public void run() {
//		optimalConsumption();
		lowConsumption();
//		originalConsumption();
    }
    public void originalConsumption() {
		while (true) {

			if (queue.size() > 0) {
				int elem = queue.poll();
				System.out.println("Consumer consumes " + elem);
			}

		}
	}
    public void optimalConsumption() {
		synchronized (queue) {
			while (true) {
				if (queue.size() > 0) {
					int elem = queue.poll();
					System.out.println("Consumer consumes " + elem);
					queue.notify();
				} else {
					try {
						queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
    public void lowConsumption() {
		while (true) {
			if (queue.size() > 0) {
				int elem = queue.poll();
				System.out.println("Consumer consumes " + elem);				
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}
}