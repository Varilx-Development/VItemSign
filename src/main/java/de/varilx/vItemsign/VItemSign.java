package de.varilx.vItemsign;

import de.varilx.BaseAPI;
import de.varilx.command.registry.VaxCommandRegistry;
import de.varilx.utils.language.LanguageUtils;
import de.varilx.vItemsign.command.ItemSignCommand;
import de.varilx.vItemsign.controller.ItemSignController;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class VItemSign extends JavaPlugin {

    ItemSignController itemSignController;

    @Override
    public void onEnable() {
        new BaseAPI(this, 24312).enable();
        initializeController();
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
    }

    private void registerListeners() {
    }

    private void registerCommands() {
        VaxCommandRegistry registry = new VaxCommandRegistry();
        registry.registerCommand(new ItemSignCommand(LanguageUtils.getMessageString("Command.Name"), this));
    }

    private void initializeController() {
        itemSignController = new ItemSignController();
    }

}
