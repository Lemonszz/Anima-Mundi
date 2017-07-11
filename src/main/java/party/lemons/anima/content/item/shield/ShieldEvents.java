package party.lemons.anima.content.item.shield;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.item.ItemTossEvent;
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
						item.removeCharge(stack, (int) (amountTaken * 40));

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

	@SubscribeEvent
	public static void itemPickup(EntityItemPickupEvent event)
	{
		if(!(event.getEntity() instanceof EntityPlayer) || event.getItem().getItem().isEmpty())
			return;

		if(event.getItem().getItem().getItem() instanceof ItemAnimaShield)
		{
			ItemAnimaShield i  = (ItemAnimaShield) event.getItem().getItem().getItem();
			i.setOff(event.getItem().getItem(), event.getEntityPlayer());
		}
	}

	@SubscribeEvent
	public static  void itemToss(ItemTossEvent event)
	{
		if(event.getEntityItem().getItem().isEmpty())
			return;

		if(event.getEntityItem().getItem().getItem() instanceof ItemAnimaShield)
		{
			ItemAnimaShield i  = (ItemAnimaShield) event.getEntityItem().getItem().getItem();
			i.setOff(event.getEntityItem().getItem(), event.getPlayer());
		}
	}
}
