package mainBot.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Settings {
    public static Map<String, Mode> modeMap = new HashMap<>();

    public static String listeModes() {
        return Mode.AVENTURE.toString() + "\n" +
               Mode.STARVENTURE.toString();
    }

    public enum Mode {
        AVENTURE,
        STARVENTURE,
        NONE
    }
}
