package net.mcreator.mofusbetterend.init;

import net.mcreator.mofusbetterend.MofusBetterEndMod;
import net.mcreator.mofusbetterend.entity.NautilusSubEntity;  // ← 确保这个导入存在
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MofusBetterEndModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, MofusBetterEndMod.MODID);

    public static final Supplier<EntityType<NautilusSubEntity>> NAUTILUS_SUB =
            ENTITIES.register("nautilus_sub", () -> EntityType.Builder
                    .<NautilusSubEntity>of(NautilusSubEntity::new, MobCategory.MISC)
                    .sized(2.0f, 1.5f)
                    .build("nautilus_sub"));
}