package party.lemons.anima.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import party.lemons.anima.content.item.shield.ItemAnimaShield;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 26/06/2017.
 */
public class EntityUtil
{
	public static boolean isHolding(EntityLivingBase living, Item item)
	{
		return living.getHeldItem(EnumHand.MAIN_HAND).getItem() == item || living.getHeldItem(EnumHand.OFF_HAND).getItem() == item;
	}

	public static List<ItemStack> getFullPlayerInventory(EntityPlayer player)
	{
		//TODO: Is there a better way of doing this?

		ArrayList<ItemStack> stacks = new ArrayList<>();

		stacks.addAll(player.inventory.armorInventory);
		stacks.addAll(player.inventory.mainInventory);
		stacks.addAll(player.inventory.offHandInventory);

		return stacks;
	}

	public static ItemStack getActivePlayerShield(EntityPlayer player)
	{
		for(ItemStack stack : getFullPlayerInventory(player))
		{
			if(!stack.isEmpty())
			{
				if(stack.getItem() instanceof ItemAnimaShield)
				{
					ItemAnimaShield item = (ItemAnimaShield) stack.getItem();

					if(item.isOn(stack))
					{
						return stack;
					}
				}
			}
		}

		return ItemStack.EMPTY;
	}

}
