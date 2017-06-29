package party.lemons.anima.energy;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.anima.config.ModConstants;
import party.lemons.anima.content.item.ItemAnimaCharged;

/**
 * Created by Sam on 29/06/2017.
 */
@Mod.EventBusSubscriber
public class AnimaEvents
{
	@SubscribeEvent
	public static void attachItemStacks(AttachCapabilitiesEvent<ItemStack> event)
	{/*
		if(!event.getObject().isEmpty())
		{
			if(event.getObject().getItem() instanceof ItemAnimaCharged)
			{
				int max = ((ItemAnimaCharged)event.getObject().getItem()).getMaxCharge(event.getObject());
				event.addCapability(new ResourceLocation(ModConstants.MODID, "anima"), new EnergyStorage(max));
			}
		}*/
	}

}
