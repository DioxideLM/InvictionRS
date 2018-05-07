package org.invictus.model.players.packets;

import org.invictus.model.players.Client;
import org.invictus.model.players.packets.in.ActionButtonPacketHandler;
import org.invictus.model.players.packets.in.AttackPlayerPacketHandler;
import org.invictus.model.players.packets.in.Bank10PacketHandler;
import org.invictus.model.players.packets.in.Bank5PacketHandler;
import org.invictus.model.players.packets.in.BankAllPacketHandler;
import org.invictus.model.players.packets.in.BankX1PacketHandler;
import org.invictus.model.players.packets.in.BankX2PacketHandler;
import org.invictus.model.players.packets.in.ChallengePlayerPacketHandler;
import org.invictus.model.players.packets.in.ChangeAppearancePacketHandler;
import org.invictus.model.players.packets.in.ChangeRegionsPacketHandler;
import org.invictus.model.players.packets.in.ChatPacketHandler;
import org.invictus.model.players.packets.in.ClanChatPacketHandler;
import org.invictus.model.players.packets.in.ClickItemPacketHandler;
import org.invictus.model.players.packets.in.ClickNPCPacketHandler;
import org.invictus.model.players.packets.in.ClickObjectPacketHandler;
import org.invictus.model.players.packets.in.ClickingInGamePacketHandler;
import org.invictus.model.players.packets.in.ClickingStuffPacketHandler;
import org.invictus.model.players.packets.in.CommandPacketHandler;
import org.invictus.model.players.packets.in.DialoguePacketHandler;
import org.invictus.model.players.packets.in.DropItemPacketHandler;
import org.invictus.model.players.packets.in.FollowPlayerPacketHandler;
import org.invictus.model.players.packets.in.IdlePacketHandler;
import org.invictus.model.players.packets.in.ItemClick2PacketHandler;
import org.invictus.model.players.packets.in.ItemClick3PacketHandler;
import org.invictus.model.players.packets.in.ItemOnGroundItemPacketHandler;
import org.invictus.model.players.packets.in.ItemOnItemPacketHandler;
import org.invictus.model.players.packets.in.ItemOnNpcPacketHandler;
import org.invictus.model.players.packets.in.ItemOnObjectPacketHandler;
import org.invictus.model.players.packets.in.MagicOnFloorItemsPacketHandler;
import org.invictus.model.players.packets.in.MagicOnItemsPacketHandler;
import org.invictus.model.players.packets.in.MoveItemsPacketHandler;
import org.invictus.model.players.packets.in.PickupItemPacketHandler;
import org.invictus.model.players.packets.in.PrivateMessagingPacketHandler;
import org.invictus.model.players.packets.in.RemoveItemPacketHandler;
import org.invictus.model.players.packets.in.TradingPacketHandler;
import org.invictus.model.players.packets.in.WalkingPacketHandler;
import org.invictus.model.players.packets.in.WearItemPacketHandler;
import org.invictus.model.players.packets.out.SendConfig;

public class PacketHandler {

	private static PacketType packetId[] = new PacketType[256];

	static {

		QuietPacket u = new QuietPacket();
		packetId[3] = u;
		packetId[202] = u;
		packetId[77] = u;
		packetId[86] = u;
		packetId[78] = u;
		packetId[36] = new SendConfig();
		packetId[226] = u;
		packetId[246] = u;
		packetId[148] = u;
		packetId[183] = u;
		packetId[230] = u;
		packetId[136] = u;
		packetId[189] = u;
		packetId[152] = u;
		packetId[200] = u;
		packetId[85] = u;
		packetId[165] = u;
		packetId[238] = u;
		packetId[150] = u;
		packetId[40] = new DialoguePacketHandler();
		ClickObjectPacketHandler co = new ClickObjectPacketHandler();
		packetId[132] = co;
		packetId[252] = co;
		packetId[70] = co;
		packetId[57] = new ItemOnNpcPacketHandler();
		ClickNPCPacketHandler cn = new ClickNPCPacketHandler();
		packetId[72] = cn;
		packetId[131] = cn;
		packetId[155] = cn;
		packetId[17] = cn;
		packetId[21] = cn;
		packetId[16] = new ItemClick2PacketHandler();
		packetId[75] = new ItemClick3PacketHandler();
		packetId[122] = new ClickItemPacketHandler();
		packetId[241] = new ClickingInGamePacketHandler();
		packetId[4] = new ChatPacketHandler();
		packetId[236] = new PickupItemPacketHandler();
		packetId[87] = new DropItemPacketHandler();
		packetId[185] = new ActionButtonPacketHandler();
		packetId[130] = new ClickingStuffPacketHandler();
		packetId[103] = new CommandPacketHandler();
		packetId[214] = new MoveItemsPacketHandler();
		packetId[237] = new MagicOnItemsPacketHandler();
		packetId[181] = new MagicOnFloorItemsPacketHandler();
		packetId[202] = new IdlePacketHandler();
		AttackPlayerPacketHandler ap = new AttackPlayerPacketHandler();
		packetId[73] = ap;
		packetId[249] = ap;
		packetId[128] = new ChallengePlayerPacketHandler();
		packetId[139] = new TradingPacketHandler();
		packetId[39] = new FollowPlayerPacketHandler();
		packetId[41] = new WearItemPacketHandler();
		packetId[145] = new RemoveItemPacketHandler();
		packetId[117] = new Bank5PacketHandler();
		packetId[43] = new Bank10PacketHandler();
		packetId[129] = new BankAllPacketHandler();
		packetId[101] = new ChangeAppearancePacketHandler();
		PrivateMessagingPacketHandler pm = new PrivateMessagingPacketHandler();
		packetId[188] = pm;
		packetId[126] = pm;
		packetId[215] = pm;
		packetId[59] = pm;
		packetId[95] = pm;
		packetId[133] = pm;
		packetId[135] = new BankX1PacketHandler();
		packetId[208] = new BankX2PacketHandler();
		WalkingPacketHandler w = new WalkingPacketHandler();
		packetId[98] = w;
		packetId[164] = w;
		packetId[248] = w;
		packetId[53] = new ItemOnItemPacketHandler();
		packetId[192] = new ItemOnObjectPacketHandler();
		packetId[25] = new ItemOnGroundItemPacketHandler();
		ChangeRegionsPacketHandler cr = new ChangeRegionsPacketHandler();
		packetId[121] = cr;
		packetId[210] = cr;
		packetId[60] = new ClanChatPacketHandler();
	}

	public static void processPacket(Client c, int packetType, int packetSize) {
		if (packetType == -1) {
			return;
		}
		PacketType p = packetId[packetType];
		if (p != null) {
			try {
				// System.out.println("packet: " + packetType);
				p.processPacket(c, packetType, packetSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Unhandled packet type: " + packetType
					+ " - size: " + packetSize);
		}
	}

}
