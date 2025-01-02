package de.varilx.vitemsign.controller;


import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.varilx.utils.itembuilder.ItemBuilder;
import de.varilx.utils.language.LanguageUtils;
import de.varilx.vitemsign.VItemSign;
import de.varilx.vitemsign.hook.LuckPermsHook;
import de.varilx.vitemsign.item.SignedItem;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Project: VItemSign
 * Package: de.varilx.vItemsign.controller
 * <p>
 * Author: ShadowDev1929
 * Created on: 01.01.2025
 */

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemSignController {

    VItemSign plugin;
    LuckPermsHook luckPermsHook;
    DateFormat dateFormat;
    MiniMessage miniMessage;

    public ItemSignController(VItemSign plugin) {
        this.plugin = plugin;
        luckPermsHook = plugin.getLuckPermsHook();
        this.dateFormat = new SimpleDateFormat(LanguageUtils.getMessageString("date_format"));
        this.miniMessage = MiniMessage.miniMessage();
    }

    public SignedItem getSignedItem(ItemStack itemStack) {
        NBTItem item = new NBTItem(itemStack);
        if(!item.hasTag("signed")) {
            return new SignedItem(false, false, null, null, -1);
        }
        return new SignedItem(true, item.getBoolean("locked"), item.getUUID("owner"), item.getString("text"), item.getLong("signDate"));
    }

    public ItemStack signItem(Player player, ItemStack itemStack, String text) {
        long signDate = System.currentTimeMillis();

        String prefix = null;

        if(luckPermsHook.isEnabled()) {
            User user = luckPermsHook.getHookedPlugin().getUserManager().getUser(player.getUniqueId());
            ContextSet contexts = luckPermsHook.getHookedPlugin().getContextManager().getContext(user).orElse(null);
            prefix = user.getCachedData().getMetaData(QueryOptions.contextual(contexts)).getPrefix();
        }

        System.out.println(PlainTextComponentSerializer.plainText().serialize(LanguageUtils.getMessage("signed_lore",
                Placeholder.parsed("luckperms_prefix", (prefix == null ? "" : prefix)),
                Placeholder.parsed("username", player.getName()),
                Placeholder.parsed("date", dateFormat.format(signDate)),
                Placeholder.parsed("separator", (prefix == null ? " " : LanguageUtils.getMessageString("lore_prefix_separator")))
        )));

        ItemBuilder itemBuilder = new ItemBuilder(itemStack);
        itemBuilder.addLastLore(miniMessage.deserialize(text))
                .addLastLore(Component.empty())
                .addLastLore(LanguageUtils.getMessage("signed_lore",
                        Placeholder.parsed("luckperms_prefix", (prefix == null ? "" : prefix)),
                        Placeholder.parsed("username", player.getName()),
                        Placeholder.parsed("date", dateFormat.format(signDate)),
                        Placeholder.parsed("separator", (prefix == null ? "" : LanguageUtils.getMessageString("lore_prefix_separator")))
                ));
        ItemStack stack = itemBuilder.build();

        NBT.modify(stack, nbt -> {
            nbt.setBoolean("signed", true);
            nbt.setBoolean("locked", false);
            nbt.setUUID("owner", player.getUniqueId());
            nbt.setString("text", text);
            nbt.setLong("signDate", signDate);
        });

        return stack;
    }

    public ItemStack lockItem(Player player, ItemStack itemStack) {
        NBT.modify(itemStack, nbt -> {
            nbt.setBoolean("locked", true);
            nbt.setUUID("owner", player.getUniqueId());
        });
        return itemStack;
    }

    public ItemStack unlockItem(ItemStack itemStack) {
        NBT.modify(itemStack, nbt -> {
            nbt.setBoolean("locked", false);
        });
        return itemStack;
    }

    public ItemStack deleteSignature(ItemStack itemStack) {
        ItemBuilder itemBuilder = new ItemBuilder(itemStack);
        for (int i = 0; i < 3; i++) {
            itemBuilder.removeLastLore();
        }
        NBT.modify(itemStack, nbt -> {
            nbt.removeKey("signed");
            nbt.removeKey("locked");
            nbt.removeKey("owner");
            nbt.removeKey("text");
            nbt.removeKey("signDate");
        });
        return itemBuilder.build();
    }

    public UUID getItemOwner(ItemStack itemStack) {
        NBTItem item = new NBTItem(itemStack);
        return item.getUUID("owner");
    }

    public boolean isSigned(ItemStack itemStack) {
        NBTItem item = new NBTItem(itemStack);
        return item.hasTag("signed");
    }

    public boolean isLocked(ItemStack itemStack) {
        NBTItem item = new NBTItem(itemStack);
        return item.getBoolean("locked");
    }

}
