package net.mcreator.Lwlwlwlmech.init;

import net.mcreator.Lwlwlwlmech.LwlwlwlmechMod;
import net.mcreator.Lwlwlwlmech.block.MechCreationStationBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class LwlwlwlmechModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, LwlwlwlmechMod.MODID);

    public static final Supplier<Block> MECH_CREATION_STATION = BLOCKS.register("mech_creation_station", MechCreationStationBlock::new);
}