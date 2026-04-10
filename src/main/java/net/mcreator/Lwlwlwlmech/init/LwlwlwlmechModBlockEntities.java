package net.mcreator.Lwlwlwlmech.init;

import net.mcreator.Lwlwlwlmech.LwlwlwlmechMod;
import net.mcreator.Lwlwlwlmech.block.entity.MechCreationStationTileEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LwlwlwlmechModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, LwlwlwlmechMod.MODID);

    public static final Supplier<BlockEntityType<MechCreationStationTileEntity>> MECH_CREATION_STATION =
            BLOCK_ENTITIES.register("mech_creation_station", () -> BlockEntityType.Builder.of(
                    MechCreationStationTileEntity::new,
                    LwlwlwlmechModBlocks.MECH_CREATION_STATION.get()
            ).build(null));
}