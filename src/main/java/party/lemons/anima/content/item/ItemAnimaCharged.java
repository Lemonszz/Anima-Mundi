package party.lemons.anima.content.item;

import net.minecraft.item.ItemStack;

/**
 * Created by Sam on 29/06/2017.
 */
public abstract class ItemAnimaCharged extends ItemAnima
{
	public ItemAnimaCharged(String name)
	{
		super(name);
	}

	public abstract int getMaxCharge(ItemStack stack);
}
