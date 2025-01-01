package de.varilx.vItemsign.item;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Project: VItemSign
 * Package: de.varilx.vItemsign.item
 * <p>
 * Author: ShadowDev1929
 * Created on: 01.01.2025
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SignedItem {

    boolean signed, locked;
    UUID owner;
    String text;
    long signDate;

}
