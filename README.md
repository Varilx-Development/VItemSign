![varilxitemsign](https://cdn.modrinth.com/data/cached_images/53be9abc933500b97eda2771459cf73760ff2cc2_0.webp)

![Discord](https://img.shields.io/discord/1322873747535040512)
![Build Status](https://img.shields.io/github/actions/workflow/status/Varilx-Development/VItemSign/build.yml?branch=main)
![Release](https://img.shields.io/github/v/release/Varilx-Development/VItemSign)

<p align="center">
    <a href="https://modrinth.com/plugin/vitemsign">
        <img src="https://raw.githubusercontent.com/vLuckyyy/badges/main/avaiable-on-modrinth.svg" alt="Available on Modrinth" />
    </a>
</p>

# VItemSign Configuration

Vitemsign is a signing system for items, allowing you to sign and protect them.

# Preview

### Commands
![Commands](https://cdn.varilx.de/u/NVIDIA_Overlay_AqL8tHS7OH.png)

## Configuration Overview

### **Custom Messages**
Customize messages for server startup, player join/quit, and Discord chat using the MiniMessage format.
We currently support: `de` and `en`

### **Commands**
`/itemsign <Message>` - Signs the item with the specified text

`/itemsign lock` - Locks the signed item so that it cannot be changed.

`/itemsign unlock` - Unlocks the signed item so that it can be changed again.

`/itemsign delete` - Removes the sign from the item.


### 4. **Permissions** 
`vitemsign.use` | Fully configurable in the Config.yml.
- Permission to use the `/itemsign` Command.

---

## Setup Instructions 

1. Download and install the plugin on your Minecraft server.
2. Configure the `config.yml` file with your preferred settings:
    - Set the database type and connection details.
    - Define custom messages using MiniMessage.
3. Restart the server to apply the changes.

---

## Example config

```yaml
language: en

# These items should remain in the list to avoid duplication bugs
BlockedItems:
  - "SHULKER_BOX"
  - "WHITE_SHULKER_BOX"
  - "GRAY_SHULKER_BOX"
  - "BLACK_SHULKER_BOX"
  - "BROWN_SHULKER_BOX"
  - "RED_SHULKER_BOX"
  - "ORANGE_SHULKER_BOX"
  - "YELLOW_SHULKER_BOX"
  - "LIME_SHULKER_BOX"
  - "GREEN_SHULKER_BOX"
  - "CYAN_SHULKER_BOX"
  - "LIGHT_BLUE_SHULKER_BOX"
  - "BLUE_SHULKER_BOX"
  - "PURPLE_SHULKER_BOX"
  - "MAGENTA_SHULKER_BOX"
  - "PINK_SHULKER_BOX"
  - "CHEST"
  - "TRAPPED_CHEST"
  - "HOPPER"
  - "ENDER_CHEST"
  - "BARREL"
  - "FURNANCE"
```

---

## Example Message Configuration

```yaml
# General Messages
prefix: "<bold><dark_gray>[<gradient:#5FE2C5:#4498DB>VItem</gradient><gradient:#4498DB:#89B974>Si</gradient><gradient:#89B974:#89B974>gn</gradient><dark_gray>]<reset><gray> "
date_format: "MM/dd/yyyy - hh:mm a"
user_not_found: "<prefix><red>That user does not exist."
no_permission: "<prefix><red>You don't have permission to do that."
cannot_sign_blocked_item: "<prefix><red>You are not allowed to sign that!"
cannot_lock_blocked_item: "<prefix><red>You are not allowed to lock this item!"
item_not_signed: "<prefix><red>This item is not signed yet!"
not_your_item: "<prefix><red>You are not the owner of that item!"
item_sign_deleted_successfully: "<prefix>The sign has been <red>deleted <green>succesfully <gray>!"
item_already_locked: "<prefix><red>This item has already been locked!"
item_locked_successfully: "<prefix>This item has <green>succesfully <gray>been <red>locked<dark_gray>!"
item_not_locked: "<prefix><red>This item is not locked!"
item_unlocked_successfully: "<prefix>This item has <green>succesfully <gray>been unlocked<dark_gray>!"
item_already_signed: "<prefix><red>This item has already been signed<dark_gray>!"
item_signed_successfully: "<prefix>This item has <green>succesfully <gray>been signed<dark_gray>."
item_in_hand_is_air: "<prefix><red>You have to hold the item in you main hand!"
item_sign_broken_successfully: "<prefix>You have broken a signed item block!"
lore_prefix_separator: " <dark_gray>| <gray>"

signed_lore: "<!i><dark_gray>» <gray>Signed by <luckperms_prefix><reset><separator><username> <gray>am <yellow><date>"

Command:
  Name: "itemsign"
  Permission: "vitemsign.use"
  Arguments:
    Delete: "delete"
    Lock: "lock"
    Unlock: "unlock"
  Usage:
    - "<prefix>VItemSign Help<dark_gray>:"
    - "<prefix><yellow>/itemsign <dark_gray><<green>Text<dark_gray>> - <gray>Signs an <yellow>Item<gray>."
    - "<prefix><yellow>/itemsign <red>delete <dark_gray>- <red>Delete <gray>the item sign."
    - "<prefix><yellow>/itemsign <red>lock <dark_gray>- <gray>The sign will get <red>locked."
    - "<prefix><yellow>/itemsign <green>unlock <dark_gray>- <gray>The sign will get <yellow>unlocked."
```

---

## Example Database Configuration

```yaml
type: Sqlite # Avaiable types: mongo, mysql, sqlite


# MONGO
Mongo:
  connection-string: "mongodb://<username>:<password>@<host>:<port>/"
  database: "db"


# SQL
SQL:
  connection-string: "jdbc:sqlite:plugins/VItemSign/database.db"
  username: "username" # Not required for sqlite
  password: "password" # Not required for sqlite
```


---

---
## Soft Requirements
* WorldGuard
---



## Notes

- The MiniMessage format is highly flexible for styling and formatting messages. Refer to the [MiniMessage documentation](https://docs.advntr.dev/minimessage/format.html) for more details.
- SQLite is the simplest database option as it doesn’t require additional setup.


Join our Discord for support, updates, or to report bugs:
<p align="center"> <a href="https://discord.gg/ZPyb9g6Gs4"> <img src="https://cdn.varilx.de/raw/Zm9inS.png" alt="Join our Discord" width="400"> </a> </p>
