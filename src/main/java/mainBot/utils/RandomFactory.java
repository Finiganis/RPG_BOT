package mainBot.utils;

import java.util.Random;

public class RandomFactory {
	private static Random gen = new Random();
	
	public static int getARoll(){
		return gen.nextInt();
	}
}
