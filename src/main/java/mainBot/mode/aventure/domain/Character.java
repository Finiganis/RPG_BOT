package mainBot.mode.aventure.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Character implements Serializable {
	private String name;
	private int maxHP;
	private int hp;
	private int maxMana;
	private int mana;
}
