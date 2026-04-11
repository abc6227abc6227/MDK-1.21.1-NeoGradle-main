package net.mcreator.Lwlwlwlmech.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.mcreator.Lwlwlwlmech.network.NautilusDownMessage;
import net.mcreator.Lwlwlwlmech.network.NautilusForwardMessage;
import net.mcreator.Lwlwlwlmech.network.NautilusThrustMessage;
import net.mcreator.Lwlwlwlmech.network.NautilusUpMessage;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = "lwlwlwl_mech_", value = Dist.CLIENT)
public class LwlwlwlmechModKeyMappings {
    public static final String KEY_CATEGORY = "key.categories.lwlwlwl_mech_";

    public static KeyMapping nautilusUp = new KeyMapping("key.lwlwlwl_mech_.nautilus_up", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_SPACE, KEY_CATEGORY);
    public static KeyMapping nautilusDown = new KeyMapping("key.lwlwlwl_mech_.nautilus_down", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY);
    public static KeyMapping nautilusForward = new KeyMapping("key.lwlwlwl_mech_.nautilus_forward", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_W, KEY_CATEGORY);
    public static KeyMapping nautilusThrust = new KeyMapping("key.lwlwlwl_mech_.nautilus_thrust", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_CONTROL, KEY_CATEGORY);

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(nautilusUp);
        event.register(nautilusDown);
        event.register(nautilusForward);
        event.register(nautilusThrust);
    }

    private static boolean wasUpPressed = false, wasDownPressed = false, wasForwardPressed = false, wasThrustPressed = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
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