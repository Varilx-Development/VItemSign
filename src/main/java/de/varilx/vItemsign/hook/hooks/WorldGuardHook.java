package de.varilx.vItemsign.hook.hooks;


import de.varilx.vItemsign.VItemSign;
import de.varilx.vItemsign.hook.AbstractPluginHook;
import com.sk89q.worldguard.WorldGuard;

/**
 * Project: VItemSign
 * Package: de.varilx.vItemsign.hook.hooks
 * <p>
 * Author: ShadowDev1929
 * Created on: 01.01.2025
 */
public class WorldGuardHook extends AbstractPluginHook<WorldGuard> {

    public WorldGuardHook(VItemSign plugin, String hookPluginName) {
        super(plugin, hookPluginName);
    }

    @Override
    public void check() {
        if(plugin.getServer().getPluginManager().getPlugin(hookPluginName) == null) {
            plugin.getLogger().info(hookPluginName + "could not be found! Please install it to use this plugin!");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        hookInstance = WorldGuard.getInstance();
    }
}
