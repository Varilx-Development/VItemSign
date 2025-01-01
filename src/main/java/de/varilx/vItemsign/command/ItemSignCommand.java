package de.varilx.vItemsign.command;


import de.varilx.BaseAPI;
import de.varilx.command.VaxCommand;
import de.varilx.utils.language.LanguageUtils;
import de.varilx.vItemsign.VItemSign;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Project: VItemSign
 * Package: de.varilx.vItemsign.command
 * <p>
 * Author: ShadowDev1929
 * Created on: 01.01.2025
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemSignCommand extends VaxCommand {

    VItemSign plugin;

    public ItemSignCommand(@NotNull String name, VItemSign plugin) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(!player.hasPermission(LanguageUtils.getMessageString("Command.Permission"))) {
                player.sendMessage(LanguageUtils.getMessage("no_permission"));
                return false;
            }

            if(args.length == 0) {
                LanguageUtils.getMessageList("Command.Usage").forEach(player::sendMessage);
            } else if(args.length == 1) {
                ItemStack inHand = player.getEquipment().getItemInMainHand();

                if(inHand.getType().isAir()) {
                    player.sendMessage(LanguageUtils.getMessage("item_in_hand_is_air"));
                    return false;
                }

                if(isBlocked(inHand)) {
                    player.sendMessage(LanguageUtils.getMessage("cannot_sign_blocked_item"));
                    return false;
                }

                if(args[0].equalsIgnoreCase(LanguageUtils.getMessageString("Command.Arguments.Delete"))) {
                    if(!plugin.getItemSignController().isSigned(inHand)) {
                        player.sendMessage(LanguageUtils.getMessage("item_not_signed"));
                        return false;
                    }

                    if(plugin.getItemSignController().isLocked(inHand)) {
                        UUID owner = plugin.getItemSignController().getItemOwner(inHand);
                        if(!owner.equals(player.getUniqueId())) {
                            player.sendMessage(LanguageUtils.getMessage("not_your_item"));
                            return false;
                        }
                    }

                    ItemStack normalItem = plugin.getItemSignController().deleteSignature(inHand);
                    player.getInventory().removeItem(inHand);
                    player.getInventory().addItem(normalItem);
                    player.sendMessage(LanguageUtils.getMessage("item_sign_deleted_successfully"));
                } else if(args[0].equalsIgnoreCase(LanguageUtils.getMessageString("Command.Arguments.Lock"))) {
                    if(!plugin.getItemSignController().isSigned(inHand)) {
                        player.sendMessage(LanguageUtils.getMessage("item_not_signed"));
                        return false;
                    }
                    if(isBlocked(inHand)) {
                        player.sendMessage(LanguageUtils.getMessage("cannot_lock_blocked_item"));
                        return false;
                    }

                    if(plugin.getItemSignController().isLocked(inHand)) {
                        player.sendMessage(LanguageUtils.getMessage("item_already_locked"));
                        return false;
                    }
                    ItemStack locked = plugin.getItemSignController().lockItem(player, inHand);
                    player.getInventory().removeItem(inHand);
                    player.getInventory().addItem(locked);
                    player.sendMessage(LanguageUtils.getMessage("item_locked_successfully"));
                } else if(args[0].equalsIgnoreCase(LanguageUtils.getMessageString("Command.Arguments.Unlock"))) {
                    if(!plugin.getItemSignController().isSigned(inHand)) {
                        player.sendMessage(LanguageUtils.getMessage("item_not_signed"));
                        return false;
                    }
                    if(!plugin.getItemSignController().isLocked(inHand)) {
                        player.sendMessage(LanguageUtils.getMessage("item_not_locked"));
                        return false;
                    }
                    UUID owner = plugin.getItemSignController().getItemOwner(inHand);
                    if(!owner.equals(player.getUniqueId())) {
                        player.sendMessage(LanguageUtils.getMessage("not_your_item"));
                        return false;
                    }

                    ItemStack unlocked = plugin.getItemSignController().unlockItem(inHand);
                    player.getInventory().removeItem(inHand);
                    player.getInventory().addItem(unlocked);
                    player.sendMessage(LanguageUtils.getMessage("item_unlocked_successfully"));
                } else {
                    if(plugin.getItemSignController().isSigned(inHand)) {
                        player.sendMessage(LanguageUtils.getMessage("item_already_signed"));
                        return false;
                    }
                    if(plugin.getItemSignController().isLocked(inHand)) {
                        player.sendMessage(LanguageUtils.getMessage("item_already_locked"));
                        return false;
                    }
                    String text = args[0];
                    ItemStack signed = plugin.getItemSignController().signItem(player, inHand, text);
                    player.getInventory().removeItem(inHand);
                    player.getInventory().addItem(signed);
                    player.sendMessage(LanguageUtils.getMessage("item_signed_successfully"));
                }
                return false;
            }
            if(args.length > 1) {
                ItemStack inHand = player.getEquipment().getItemInMainHand();

                if(inHand.getType().isAir()) {
                    player.sendMessage(LanguageUtils.getMessage("item_in_hand_is_air"));
                    return false;
                }
                if(plugin.getItemSignController().isSigned(inHand)) {
                    player.sendMessage(LanguageUtils.getMessage("item_already_signed"));
                    return false;
                }
                if(plugin.getItemSignController().isLocked(inHand)) {
                    player.sendMessage(LanguageUtils.getMessage("item_already_locked"));
                    return false;
                }
                StringBuilder textBuilder = new StringBuilder();
                for (String arg : args) {
                    textBuilder.append(arg).append(" ");
                }
                ItemStack signed = plugin.getItemSignController().signItem(player, inHand, textBuilder.toString());
                player.getInventory().removeItem(inHand);
                player.getInventory().addItem(signed);
                player.sendMessage(LanguageUtils.getMessage("item_signed_successfully"));
                return false;
            }
        }
        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return List.of("delete", "lock", "unlock");
    }

    private boolean isBlocked(ItemStack itemStack) {
        return BaseAPI.getBaseAPI().getConfiguration().getConfig().getStringList("BlockedItems").contains(itemStack.getType().name());
    }

}
