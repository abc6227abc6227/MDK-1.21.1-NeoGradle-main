package net.mcreator.mofusbetterend.network;

import net.mcreator.mofusbetterend.MofusBetterEndMod;
import net.mcreator.mofusbetterend.procedures.NautilusUpOnKeyPressedProcedure;
import net.mcreator.mofusbetterend.procedures.NautilusUpOnKeyReleasedProcedure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public record NautilusUpMessage(int action) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<NautilusUpMessage> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "nautilus_up")
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
            if (action == 0) {
                NautilusUpOnKeyPressedProcedure.execute(player);
            } else {
                NautilusUpOnKeyReleasedProcedure.execute(player);
            }
        });
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}