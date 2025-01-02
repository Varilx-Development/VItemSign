package de.varilx.vitemsign.utils;


import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Project: VItemSign
 * Package: de.varilx.vItemsign.utils
 * <p>
 * Author: ShadowDev1929
 * Created on: 01.01.2025
 */
public class ItemStackConverter {

    public String encode(ItemStack itemStack) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
            dataOutput.writeObject(itemStack);
            return new String(Base64Coder.encode(outputStream.toByteArray()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public ItemStack decode(String base64) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decode(base64));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
            return (ItemStack) dataInput.readObject();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
