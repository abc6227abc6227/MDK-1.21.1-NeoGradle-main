package net.mcreator.mofusbetterend.init;

import net.mcreator.mofusbetterend.MofusBetterEndMod;
import net.mcreator.mofusbetterend.block.display.MechCreationStationDisplayItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class MofusBetterEndModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MofusBetterEndMod.MODID);

    public static final Supplier<Item> NAUTILUS_SUB_BLUEPRINT = ITEMS.register("nautilus_sub_blueprint", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> NAUTILUS_THRUSTER = ITEMS.register("nautilus_thruster", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MECH_CREATION_STATION = ITEMS.register("mech_creation_station", () -> new MechCreationStationDisplayItem(MofusBetterEndModBlocks.MECH_CREATION_STATION.get(), new Item.Properties()));
}