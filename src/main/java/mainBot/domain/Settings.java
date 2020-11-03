package mainBot.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Settings {
    public static Map<String, Mode> modeMap = new HashMap<>();
    public static Path settingsFilePath = new File("src/main/resources/settings.txt").toPath();

    public static String listeModes() {
        return Mode.AVENTURE.toString() + "\n" +
               Mode.STARVENTURE.toString();
    }

    public enum Mode {
        AVENTURE,
        STARVENTURE,
        NONE
    }

    public static String saveSettings() {
        if (!Files.exists(settingsFilePath)) {
            try {
                Files.createFile(settingsFilePath);
            } catch (IOException e) {
                System.out.println(settingsFilePath);
                e.printStackTrace();
            }
        }

        try (BufferedWriter bw = Files.newBufferedWriter(settingsFilePath, StandardCharsets.UTF_8)) {
            for(Map.Entry<String, Mode> entry : modeMap.entrySet()) {
                bw.write(String.format("%s:%s", entry.getKey(), entry.getValue().name()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("A problem occured while saving");
            e.printStackTrace();
            return "Echec de la sauvegarde";
        }
        return "Sauvegarde effectuée avec succès";
    }

    public static void loadSettings() {
        try {
            String content = new String(Files.readAllBytes(settingsFilePath));
            for (String line : content.split(System.lineSeparator())) {
                String[] lineTab = line.split(":");
                modeMap.put(lineTab[0], Mode.valueOf(lineTab[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
