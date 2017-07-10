package party.lemons.anima.content.item.shield;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import party.lemons.anima.util.EntityUtil;

import java.util.List;

/**
 * Created by Sam on 9/07/2017.
 */
@Mod.EventBusSubscriber
public class ShieldEvents
{

	@SubscribeEvent
	public static void playerHurt(LivingHurtEvent event)
	{
		if(!(event.getEntityLiving() instanceof EntityPlayer))
			return;

		List<ItemStack> stackList = EntityUtil.getFullPlayerInventory((EntityPlayer) event.getEntityLiving());

		for(ItemStack stack : stackList)
		{
			if(!stack.isEmpty())
			{
				if(stack.getItem() instanceof ItemAnimaShield)
				{
					ItemAnimaShield item = (ItemAnimaShield) stack.getItem();

					if(!item.isOn(stack))
						continue;

					if(item.getCurrentCharge(stack) > 0 && item.getShieldCharge(stack) > 0)
					{
						float amountTaken = event.getAmount();
						float shieldScaled = item.getShieldCharge(stack) / 10;

						float remain = Math.max(0, amountTaken - shieldScaled);

						event.setAmount(remain);
						item.depleteShield(stack, amountTaken * 10);

						if(item.getShieldCharge(stack) == 0)
						{
							item.getShield(stack).onDeplete(stack, event.getEntityLiving());
						}
					}

					return;
				}
			}
		}
	}
}
