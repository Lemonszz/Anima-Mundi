package party.lemons.anima.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;

/**
 * Created by Sam on 26/06/2017.
 */
public class EntityUtil
{
	public static boolean isHolding(EntityLivingBase living, Item item)
	{
		return living.getHeldItem(EnumHand.MAIN_HAND).getItem() == item || living.getHeldItem(EnumHand.OFF_HAND).getItem() == item;
	}
}
