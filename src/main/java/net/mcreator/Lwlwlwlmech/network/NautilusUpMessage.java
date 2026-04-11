package net.mcreator.Lwlwlwlmech.network;

import net.mcreator.Lwlwlwlmech.LwlwlwlmechMod;
import net.mcreator.Lwlwlwlmech.procedures.NautilusUpOnKeyPressedProcedure;
import net.mcreator.Lwlwlwlmech.procedures.NautilusUpOnKeyReleasedProcedure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.mcreator.Lwlwlwlmech.entity.NautilusSubEntity;

public record NautilusUpMessage(int action) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<NautilusUpMessage> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "nautilus_up")
    );
    public static final StreamCodec<FriendlyByteBuf, NautilusUpMessage> STREAM_CODEC =
            StreamCodec.ofMember(
                    (msg, buf) -> buf.writeInt(msg.action),
                    buf -> new NautilusUpMessage(buf.readInt())
            );

    public static void register(PayloadRegistrar registrar) {
        registrar.playToServer(TYPE, STREAM_CODEC, NautilusUpMessage::handle);
    }

    public static void send(int action) {
        PacketDistributor.sendToServer(new NautilusUpMessage(action));
    }

    private void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            if (player.getVehicle() instanceof NautilusSubEntity sub) {
                sub.setUpPressed(action == 0);
                System.out.println("[网络] 服务端设置 upPressed = " + (action == 0));
            }
        });
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}