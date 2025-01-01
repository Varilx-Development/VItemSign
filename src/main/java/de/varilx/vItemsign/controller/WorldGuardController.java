package de.varilx.vItemsign.controller;


import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import de.varilx.vItemsign.VItemSign;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;

import java.util.Objects;

/**
 * Project: VItemSign
 * Package: de.varilx.vItemsign.controller
 * <p>
 * Author: ShadowDev1929
 * Created on: 01.01.2025
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorldGuardController {

    VItemSign plugin;

    public WorldGuardController(VItemSign plugin) {
        this.plugin = plugin;
    }

    public boolean isWorldGuardRegion(Location location) {
        if(!plugin.getWorldGuardHook().isEnabled()) return false;
        ApplicableRegionSet set = Objects.requireNonNull(plugin.getWorldGuardHook().getHookedPlugin().getPlatform().getRegionContainer()
                        .get(new BukkitWorld(location.getWorld())))
                .getApplicableRegions(BlockVector3.at(location.getX(),location.getY(),location.getZ()));
        return set.size() != 0;
    }

}
