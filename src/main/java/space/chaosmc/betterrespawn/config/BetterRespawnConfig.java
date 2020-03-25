package space.chaosmc.betterrespawn.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BetterRespawnConfig {
    public boolean addBetterRespawnAnchor;
    public boolean injectFunctionalityIntoCryingObsidian;
    public boolean injectFunctionalityIntoRespawnAnchor;
    public boolean addPortableItem;

    public BetterRespawnConfig() {
        addBetterRespawnAnchor = true;
        injectFunctionalityIntoCryingObsidian = false;
        injectFunctionalityIntoRespawnAnchor = false;
        addPortableItem = true;
    }

    public static BetterRespawnConfig init(BetterRespawnConfig config) {
        File file = new File(FabricLoader.getInstance().getConfigDirectory().getPath(), "betterrespawn.json");

        try {
            // util.logger("loading config!", true);
            System.out.println("loading config!");
            FileReader fr = new FileReader(file);
            config = new Gson().fromJson(fr, BetterRespawnConfig.class);
            FileWriter fw = new FileWriter(file);
            fw.write(new GsonBuilder().setPrettyPrinting().create().toJson(config));
            fw.close();
        } catch (IOException e) {
            System.out.println("failed loading! Creating initial config!");
            // util.logger(, true);
            config = new BetterRespawnConfig();
            try {
                FileWriter fw = new FileWriter(file);
                fw.write(new GsonBuilder().setPrettyPrinting().create().toJson(config));
                fw.close();
            } catch (IOException e1) {
                System.out.println("failed config!");
                // util.logger("failed config!", true);
                e1.printStackTrace();
            }
        }
        return config;
    }

}
