package de.varilx.vitemsign.listener;


import de.tr7zw.changeme.nbtapi.NBTBlock;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.varilx.utils.language.LanguageUtils;
import de.varilx.vitemsign.VItemSign;
import de.varilx.vitemsign.utils.ItemStackConverter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project: VItemSign
 * Package: de.varilx.vItemsign.listener
 * <p>
 * Author: ShadowDev1929
 * Created on: 01.01.2025
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemSignListener implements Listener {

    VItemSign plugin;
    ItemStackConverter itemStackConverter;

    public ItemSignListener(VItemSign plugin) {
        this.plugin = plugin;
        this.itemStackConverter = new ItemStackConverter();
    }

    @EventHandler
    public void onSpawn(HangingPlaceEvent event) {
        Player player = event.getPlayer();
        if(plugin.getWorldGuardController() != null) {
            if (plugin.getWorldGuardController().isWorldGuardRegion(event.getBlock().getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
        if(plugin.getItemSignController().isSigned(player.getEquipment().getItemInMainHand())) {
            ItemStack inHand = player.getEquipment().getItemInMainHand();
            inHand = inHand.clone();
            inHand.setAmount(1);
            String signData = itemStackConverter.encode(inHand);

            NBTEntity nbtEntity = new NBTEntity(event.getEntity());
            nbtEntity.getPersistentDataContainer().setString("signData", signData);
        }
    }

    @EventHandler
    public void onBreak(HangingBreakByEntityEvent event) {
        if(plugin.getWorldGuardController() != null) {
            if (plugin.getWorldGuardController().isWorldGuardRegion(event.getEntity().getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
        if(event.getRemover() instanceof Player player) {
            NBTEntity entity = new NBTEntity(event.getEntity());
            if(entity.getPersistentDataContainer().hasKey("signData")) {
                event.setCancelled(true);
                event.getEntity().remove();
                ItemStack data = itemStackConverter.decode(entity.getPersistentDataContainer().getString("signData"));
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItem(player.getLocation(), data);
                } else {
                    player.getInventory().addItem(data);
                }
                player.sendMessage(LanguageUtils.getMessage("item_sign_broken_successfully"));
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(plugin.getWorldGuardController() != null) {
            if (plugin.getWorldGuardController().isWorldGuardRegion(event.getBlock().getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
        if(plugin.getItemSignController().isSigned(event.getItemInHand())) {
            ItemStack inHand = event.getItemInHand();
            if (!inHand.getType().isBlock())
                return;
            inHand = inHand.clone();
            inHand.setAmount(1);
            String signData = itemStackConverter.encode(inHand);
            NBTBlock nbtBlock = new NBTBlock(event.getBlockPlaced());
            nbtBlock.getData().setString("signData", signData);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(plugin.getWorldGuardController() != null) {
            if (plugin.getWorldGuardController().isWorldGuardRegion(event.getBlock().getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
        Player player = event.getPlayer();
        NBTBlock nbtBlock = new NBTBlock(event.getBlock());
        if(nbtBlock.getData().hasTag("signData")) {
            ItemStack data = itemStackConverter.decode(nbtBlock.getData().getString("signData"));
            nbtBlock.getData().removeKey("signData");
            event.setDropItems(false);
            if (player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItem(player.getLocation(), data);
            } else {
                player.getInventory().addItem(data);
            }
            player.sendMessage(LanguageUtils.getMessage("item_sign_broken_successfully"));
        }
    }

}
