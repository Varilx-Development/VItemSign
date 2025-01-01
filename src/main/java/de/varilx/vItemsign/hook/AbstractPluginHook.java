package de.varilx.vItemsign.hook;


import de.varilx.vItemsign.VItemSign;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * Project: VItemSign
 * Package: de.varilx.vItemsign.hook
 * <p>
 * Author: ShadowDev1929
 * Created on: 01.01.2025
 */
@Getter
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractPluginHook<P> {

    VItemSign plugin;
    String hookPluginName;
    P hookInstance;
    boolean enabled;

    public AbstractPluginHook(VItemSign plugin, String hookPluginName) {
        this.plugin = plugin;
        this.hookPluginName = hookPluginName;
        this.enabled = false;
    }

    public abstract void check();

}
