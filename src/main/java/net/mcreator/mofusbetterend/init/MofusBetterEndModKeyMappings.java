package net.mcreator.mofusbetterend.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.mcreator.mofusbetterend.network.NautilusDownMessage;
import net.mcreator.mofusbetterend.network.NautilusForwardMessage;
import net.mcreator.mofusbetterend.network.NautilusThrustMessage;
import net.mcreator.mofusbetterend.network.NautilusUpMessage;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;



import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = "mofus_better_end_", value = Dist.CLIENT)
public class MofusBetterEndModKeyMappings {
    public static final String KEY_CATEGORY = "key.categories.mofus_better_end_";

    public static KeyMapping nautilusUp = new KeyMapping("key.mofus_better_end_.nautilus_up", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_SPACE, KEY_CATEGORY);
    public static KeyMapping nautilusDown = new KeyMapping("key.mofus_better_end_.nautilus_down", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY);
    public static KeyMapping nautilusForward = new KeyMapping("key.mofus_better_end_.nautilus_forward", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_W, KEY_CATEGORY);
    public static KeyMapping nautilusThrust = new KeyMapping("key.mofus_better_end_.nautilus_thrust", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_CONTROL, KEY_CATEGORY);



    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(nautilusUp);
        event.register(nautilusDown);
        event.register(nautilusForward);
        event.register(nautilusThrust);
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(MofusBetterEndModKeyMappings::onClientTick);
    }

    private static boolean wasUpPressed = false, wasDownPressed = false, wasForwardPressed = false, wasThrustPressed = false;

    public static void onClientTick(LevelTickEvent event) {
        if (event.getLevel().isClientSide) {
            boolean up = nautilusUp.isDown();
            boolean down = nautilusDown.isDown();
            boolean forward = nautilusForward.isDown();
            boolean thrust = nautilusThrust.isDown();

            if (up != wasUpPressed) {
                System.out.println("[按键] 发送上升: " + up);
                NautilusUpMessage.send(up ? 0 : 1);
                wasUpPressed = up;
            }
            if (down != wasDownPressed) {
                System.out.println("[按键] 发送下降: " + down);
                NautilusDownMessage.send(down ? 0 : 1);
                wasDownPressed = down;
            }
            if (forward != wasForwardPressed) {
                System.out.println("[按键] 发送前进: " + forward);
                NautilusForwardMessage.send(forward ? 0 : 1);
                wasForwardPressed = forward;
            }
            if (thrust != wasThrustPressed) {
                System.out.println("[按键] 发送推进: " + thrust);
                NautilusThrustMessage.send(thrust ? 0 : 1);
                wasThrustPressed = thrust;
            }
        }

    }
}