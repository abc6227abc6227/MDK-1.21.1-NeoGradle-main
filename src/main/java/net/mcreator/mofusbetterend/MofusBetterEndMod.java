package net.mcreator.mofusbetterend;

import com.mojang.logging.LogUtils;
import net.mcreator.mofusbetterend.entity.NautilusSubEntity;
import net.mcreator.mofusbetterend.init.*;
import net.mcreator.mofusbetterend.network.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

@Mod(MofusBetterEndMod.MODID)
public class MofusBetterEndMod {
    public static final String MODID = "mofus_better_end_";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MofusBetterEndMod(IEventBus modEventBus) {
        MofusBetterEndModBlocks.BLOCKS.register(modEventBus);
        MofusBetterEndModItems.ITEMS.register(modEventBus);
        MofusBetterEndModEntities.ENTITIES.register(modEventBus);
        MofusBetterEndModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        MofusBetterEndModMenus.MENUS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerPackets);
        modEventBus.addListener(this::registerAttributes);// ✅ 添加到 mod 总线

        //MofusBetterEndModKeyMappings.register();
        // ✅ 删除这一行！不要在 NeoForge 总线上注册
        // NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {});
    }

    private void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0");
        MechCreationStationGUIButtonMessage.register(registrar);
        MechCreationStationGUISlotMessage.register(registrar);
        NautilusUpMessage.register(registrar);
        NautilusDownMessage.register(registrar);
        NautilusForwardMessage.register(registrar);
        NautilusThrustMessage.register(registrar);
    }

    @SubscribeEvent
    public void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(MofusBetterEndModEntities.NAUTILUS_SUB.get(), NautilusSubEntity.createAttributes().build());
    }

    // 延迟执行任务
    public static void queueServerWork(int ticks, Runnable task) {
        new Thread(() -> {
            try {
                Thread.sleep(ticks * 50L);
                task.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}