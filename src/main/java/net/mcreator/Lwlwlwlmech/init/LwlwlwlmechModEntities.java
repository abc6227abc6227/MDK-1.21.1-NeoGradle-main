package net.mcreator.Lwlwlwlmech.init;

import net.mcreator.Lwlwlwlmech.LwlwlwlmechMod;
import net.mcreator.Lwlwlwlmech.entity.NautilusSubEntity;  // ← 确保这个导入存在
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LwlwlwlmechModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, LwlwlwlmechMod.MODID);

    public static final Supplier<EntityType<NautilusSubEntity>> NAUTILUS_SUB =
            ENTITIES.register("nautilus_sub", () -> EntityType.Builder
                    .<NautilusSubEntity>of(NautilusSubEntity::new, MobCategory.MISC)
                    .sized(2.0f, 1.5f)
                    .build("nautilus_sub"));
}