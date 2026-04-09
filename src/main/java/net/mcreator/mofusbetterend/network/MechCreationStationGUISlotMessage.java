package net.mcreator.mofusbetterend.network;

import net.mcreator.mofusbetterend.MofusBetterEndMod;
import net.mcreator.mofusbetterend.procedures.NautilusBlueprintDisplayGUIProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public record MechCreationStationGUISlotMessage(int slotID, BlockPos pos, int changeType, int meta) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MechCreationStationGUISlotMessage> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "mech_station_slot")
    );
    public static final StreamCodec<FriendlyByteBuf, MechCreationStationGUISlotMessage> STREAM_CODEC =
            StreamCodec.ofMember(
                    (msg, buf) -> {
                        buf.writeInt(msg.slotID);
                        buf.writeBlockPos(msg.pos);
                        buf.writeInt(msg.changeType);
                        buf.writeInt(msg.meta);
                    },
                    buf -> new MechCreationStationGUISlotMessage(buf.readInt(), buf.readBlockPos(), buf.readInt(), buf.readInt())
            );

    public static void register(PayloadRegistrar registrar) {
        registrar.playToServer(TYPE, STREAM_CODEC, MechCreationStationGUISlotMessage::handle);
    }

    public static void send(int slotID, BlockPos pos, int changeType, int meta) {
        PacketDistributor.sendToServer(new MechCreationStationGUISlotMessage(slotID, pos, changeType, meta));
    }

    private void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            if (slotID == 0 && changeType == 0) {
                NautilusBlueprintDisplayGUIProcedure.execute(player);  // 只传 player
            }
        });
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}