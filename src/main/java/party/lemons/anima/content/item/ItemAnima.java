package party.lemons.anima.content.item;

import net.minecraft.item.Item;

/**
 * Created by Sam on 22/06/2017.
 */
public class ItemAnima extends Item
{
	public ItemAnima(String name)
	{
		AnimaItems.generalRegisterItem(name, this);
	}
}
