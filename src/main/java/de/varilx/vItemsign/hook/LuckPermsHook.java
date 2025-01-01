package de.varilx.vItemsign.hook;


import de.varilx.hook.AbstractPluginHook;
import de.varilx.vItemsign.VItemSign;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

/**
 * Project: VItemSign
 * Package: de.varilx.vItemsign.hook.hooks
 * <p>
 * Author: ShadowDev1929
 * Created on: 01.01.2025
 */
public class LuckPermsHook extends AbstractPluginHook<LuckPerms> {

    public LuckPermsHook(VItemSign plugin, String hookPluginName) {
        super(plugin, hookPluginName);
    }

    @Override
    public void check() {
        if(plugin.getServer().getPluginManager().getPlugin(hookPluginName) == null) {
            plugin.getLogger().warning("Plugin " + hookPluginName + " could not be found! Skipping hooking...");
            return;
        }
        hookedPlugin = LuckPermsProvider.get();
    }
}
