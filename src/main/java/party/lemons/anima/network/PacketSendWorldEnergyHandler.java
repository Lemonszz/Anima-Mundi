package party.lemons.anima.network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.anima.energy.WorldEnergy;

/**
 * Created by Sam on 28/06/2017.
 */
public class PacketSendWorldEnergyHandler implements IMessageHandler<PacketSendWorldEnergy, IMessage>
{
	@Override
	public IMessage onMessage(PacketSendWorldEnergy message, MessageContext ctx)
	{
		Minecraft.getMinecraft().addScheduledTask(() ->
		{
			WorldEnergy energy = WorldEnergy.get(Minecraft.getMinecraft().world);
			energy.setAnima(message.amt, Minecraft.getMinecraft().world);
		});

		return null;
	}
}