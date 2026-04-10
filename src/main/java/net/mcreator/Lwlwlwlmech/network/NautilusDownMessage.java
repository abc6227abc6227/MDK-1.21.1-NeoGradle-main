package net.mcreator.Lwlwlwlmech.network;

import net.mcreator.Lwlwlwlmech.LwlwlwlmechMod;
import net.mcreator.Lwlwlwlmech.procedures.NautilusDownOnKeyPressedProcedure;
import net.mcreator.Lwlwlwlmech.procedures.NautilusDownOnKeyReleasedProcedure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public record NautilusDownMessage(int action) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<NautilusDownMessage> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "nautilus_down")
    );
    public static final StreamCodec<FriendlyByteBuf, NautilusDownMessage> STREAM_CODEC =
            StreamCodec.ofMember(
                    (msg, buf) -> buf.writeInt(msg.action),
                    buf -> new NautilusDownMessage(buf.readInt())
            );

    public static void register(PayloadRegistrar registrar) {
        registrar.playToServer(TYPE, STREAM_CODEC, NautilusDownMessage::handle);
    }

    public static void send(int action) {
        PacketDistributor.sendToServer(new NautilusDownMessage(action));
    }

    private void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            if (action == 0) {
                NautilusDownOnKeyPressedProcedure.execute(player);
            } else {
                NautilusDownOnKeyReleasedProcedure.execute(player);
            }
        });
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}