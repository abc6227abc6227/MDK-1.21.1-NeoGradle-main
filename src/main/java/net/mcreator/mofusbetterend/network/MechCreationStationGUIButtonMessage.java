package net.mcreator.mofusbetterend.network;

import net.mcreator.mofusbetterend.MofusBetterEndMod;
import net.mcreator.mofusbetterend.procedures.MechCreationStationOnBlockRightClickedProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public record MechCreationStationGUIButtonMessage(int buttonID, BlockPos pos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MechCreationStationGUIButtonMessage> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "mech_station_button")
    );
    public static final StreamCodec<FriendlyByteBuf, MechCreationStationGUIButtonMessage> STREAM_CODEC =
            StreamCodec.ofMember(
                    (msg, buf) -> {
                        buf.writeInt(msg.buttonID);
                        buf.writeBlockPos(msg.pos);
                    },
                    buf -> {
                        int id = buf.readInt();
                        BlockPos p = buf.readBlockPos();
                        System.out.println("[网络消息] 从缓冲区读取，坐标: " + p.getX() + ", " + p.getY() + ", " + p.getZ());
                        return new MechCreationStationGUIButtonMessage(id, p);
                    }
            );

    public static void register(PayloadRegistrar registrar) {
        registrar.playToServer(TYPE, STREAM_CODEC, MechCreationStationGUIButtonMessage::handle);
    }

    public static void send(int buttonID, BlockPos pos) {
        PacketDistributor.sendToServer(new MechCreationStationGUIButtonMessage(buttonID, pos));
    }

    private void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            System.out.println("[网络] 收到按钮点击，坐标: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
            System.out.println("[网络] 该位置方块: " + player.level().getBlockState(pos).getBlock());

            if (buttonID == 0) {
                MechCreationStationOnBlockRightClickedProcedure.execute(player.level(), pos.getX(), pos.getY(), pos.getZ(), player);
            }
        });
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}