package net.mcreator.Lwlwlwlmech.network;

import net.mcreator.Lwlwlwlmech.LwlwlwlmechMod;
import net.mcreator.Lwlwlwlmech.entity.NautilusSubEntity;
import net.mcreator.Lwlwlwlmech.procedures.NautilusForwardOnKeyPressedProcedure;
import net.mcreator.Lwlwlwlmech.procedures.NautilusForwardOnKeyReleasedProcedure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;



public record NautilusForwardMessage(int action) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<NautilusForwardMessage> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "nautilus_forward")
    );
    public static final StreamCodec<FriendlyByteBuf, NautilusForwardMessage> STREAM_CODEC =
            StreamCodec.ofMember(
                    (msg, buf) -> buf.writeInt(msg.action),
                    buf -> new NautilusForwardMessage(buf.readInt())
            );

    public static void register(PayloadRegistrar registrar) {
        registrar.playToServer(TYPE, STREAM_CODEC, NautilusForwardMessage::handle);
    }

    public static void send(int action) {
        System.out.println("[网络] 发送前进消息: " + action);
        PacketDistributor.sendToServer(new NautilusForwardMessage(action));
    }

    private void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            if (player.getVehicle() instanceof NautilusSubEntity sub) {
                // 直接让服务端移动
                double rad = Math.toRadians(player.getYRot());
                double moveX = -Math.sin(rad) * 0.3;
                double moveZ = Math.cos(rad) * 0.3;
                sub.setPos(sub.getX() + moveX, sub.getY(), sub.getZ() + moveZ);
                System.out.println("[网络] 移动鹦鹉螺号到: " + sub.getX());
            }
        });
    }

/*
    private void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            if (action == 0) {
                NautilusForwardOnKeyPressedProcedure.execute(player);
            } else {
                NautilusForwardOnKeyReleasedProcedure.execute(player);
            }
        });
    }
*/
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}