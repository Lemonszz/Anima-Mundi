package party.lemons.anima.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import party.lemons.anima.energy.WorldEnergy;

/**
 * Created by Sam on 28/06/2017.
 */
@Mod.EventBusSubscriber
public class SyncHandler
{
	@SubscribeEvent
	public static void syncValues(PlayerEvent.PlayerLoggedInEvent event)
	{
		if(event.player.world != null)
		{
			WorldEnergy.syncWith(event.player);
		}
	}
}
