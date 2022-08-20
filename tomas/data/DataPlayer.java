package tomas.data;

import org.bukkit.entity.Player;
import tomas.miner.Miner;

public class DataPlayer {

    private DataManager dataManager;
    private Miner miner;

    public Miner getMiner() {
        return miner;
    }

    public static int TaskID;

    public int getTaskID() {
        return TaskID;
    }

    public DataPlayer(Player player) {
        dataManager = new DataManager(player);
        miner = new Miner(player);
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
