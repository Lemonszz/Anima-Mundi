package party.lemons.anima.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.anima.energy.WorldEnergy;

/**
 * Created by Sam on 28/06/2017.
 */
public class PacketSendWorldEnergy implements IMessage
{
	public int amt;

	public PacketSendWorldEnergy(){
	}

	public PacketSendWorldEnergy(int amt)
	{
		this.amt = amt;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		amt = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(amt);
	}

}
