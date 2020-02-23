package mainBot.utils;

import java.util.Random;

public class RandomFactory {
	private static Random gen = new Random();
	
	public static int getARoll(){
		int res = gen.nextInt();
		return res > 0 ? res : -res;
	}

	public static int getARoll(int max){
		int res = gen.nextInt();
		return res >= 0 ? (res % max) + 1 : (-res % max) + 1;
	}
}
