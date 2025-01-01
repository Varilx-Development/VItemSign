package de.varilx.vItemsign;

import de.varilx.BaseAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class VItemSign extends JavaPlugin {

    @Override
    public void onEnable() {
        new BaseAPI(this, 24312).enable();
    }

    @Override
    public void onDisable() {
    }
}
