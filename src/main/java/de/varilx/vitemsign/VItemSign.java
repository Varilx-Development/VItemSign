package de.varilx.vitemsign;

import de.tr7zw.changeme.nbtapi.NBT;
import de.varilx.BaseAPI;
import de.varilx.command.registry.VaxCommandRegistry;
import de.varilx.utils.language.LanguageUtils;
import de.varilx.vitemsign.command.ItemSignCommand;
import de.varilx.vitemsign.controller.ItemSignController;
import de.varilx.vitemsign.controller.WorldGuardController;
import de.varilx.vitemsign.hook.LuckPermsHook;
import de.varilx.vitemsign.hook.WorldGuardHook;
import de.varilx.vitemsign.listener.ItemSignListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class VItemSign extends JavaPlugin {

    LuckPermsHook luckPermsHook;
    WorldGuardHook worldGuardHook;

    ItemSignController itemSignController;
    WorldGuardController worldGuardController;

    @Override
    public void onEnable() {
        checkNBT();
        checkHooks();
        new BaseAPI(this, 24312).enable();
        initializeController();
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ItemSignListener(this), this);
    }

    private void registerCommands() {
        VaxCommandRegistry registry = new VaxCommandRegistry();
        registry.registerCommand(new ItemSignCommand(LanguageUtils.getMessageString("Command.Name"), this));
    }

    private void initializeController() {
        itemSignController = new ItemSignController(this);
        if(!worldGuardHook.isEnabled()) return;
        worldGuardController = new WorldGuardController(this);
    }

    private void checkNBT() {
        if(NBT.preloadApi()) return;
        getLogger().warning("NBT-API wasn't initialized properly, disabling the plugin");
        getServer().getPluginManager().disablePlugin(this);
    }

    private void checkHooks() {
        luckPermsHook = new LuckPermsHook(this, "LuckPerms");
        luckPermsHook.check();

        worldGuardHook = new WorldGuardHook(this, "WorldGuard");
        worldGuardHook.check();

    }

}
