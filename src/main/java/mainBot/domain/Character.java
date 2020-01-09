package mainBot.domain;

public class Character {
	private String name;
	private int hp;
	private int mana;
	
	public Character(String name, Integer hp, Integer mana){
		this.name = name;
		this.hp = hp;
		this.mana = mana;
	}
	
	public String getName(){
		return name;
	}
	
	public int getHP(){
		return hp;
	}
	
	public int getMana(){
		return mana;
	}
	
	public int dammage(int d){
		hp -= d;
		return hp;
	}
	
	public int recover(int h){
		hp += h;
		return hp;
	}
	
	public int spell(int c){
		mana -= c;
		return mana;
	}
	
	public int magicRecover(int v){
		mana += v;
		return mana;
	}
}
