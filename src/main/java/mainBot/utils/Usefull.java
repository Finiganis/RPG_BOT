package mainBot.utils;

public class Usefull {
	public static String rolltoeffect(int roll){
		String[] tab = new String[6];
		tab[0] = "+5% réussite critique";
		tab[1] = "+20% réussite";
		tab[2] = "neutre";
		tab[3] = "conséquence";
		tab[4] = "-20% réussite";
		tab[5] = "+5% échec critique";
		if(roll<1 || roll>6) return "";
		return tab[roll-1];
	}
}
