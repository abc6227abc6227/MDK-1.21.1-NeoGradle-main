package net.mcreator.mofusbetterend.init;

import net.mcreator.mofusbetterend.MofusBetterEndMod;
import net.mcreator.mofusbetterend.block.MechCreationStationBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class MofusBetterEndModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MofusBetterEndMod.MODID);

    public static final Supplier<Block> MECH_CREATION_STATION = BLOCKS.register("mech_creation_station", MechCreationStationBlock::new);
}