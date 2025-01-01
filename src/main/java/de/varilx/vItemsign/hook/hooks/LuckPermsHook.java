package de.varilx.vItemsign.hook.hooks;


import de.varilx.vItemsign.VItemSign;
import de.varilx.vItemsign.hook.AbstractPluginHook;
import net.luckperms.api.LuckPermsProvider;

/**
 * Project: VItemSign
 * Package: de.varilx.vItemsign.hook.hooks
 * <p>
 * Author: ShadowDev1929
 * Created on: 01.01.2025
 */
public class LuckPermsHook extends AbstractPluginHook {

    public LuckPermsHook(VItemSign plugin, String hookPluginName) {
        super(plugin, hookPluginName);
    }

    @Override
    public void check() {
        if(plugin.getServer().getPluginManager().getPlugin(hookPluginName) == null) {
            plugin.getLogger().warning("Plugin " + hookPluginName + " could not be found! Skipping hooking...");
            return;
        }
        hookInstance = LuckPermsProvider.get();
    }
}
