package net.mcreator.mofusbetterend.init;

import net.mcreator.mofusbetterend.MofusBetterEndMod;
import net.mcreator.mofusbetterend.block.entity.MechCreationStationTileEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MofusBetterEndModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MofusBetterEndMod.MODID);

    public static final Supplier<BlockEntityType<MechCreationStationTileEntity>> MECH_CREATION_STATION =
            BLOCK_ENTITIES.register("mech_creation_station", () -> BlockEntityType.Builder.of(
                    MechCreationStationTileEntity::new,
                    MofusBetterEndModBlocks.MECH_CREATION_STATION.get()
            ).build(null));
}