package party.lemons.anima.crafting;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import party.lemons.anima.content.item.AnimaItems;

/**
 * Created by Sam on 19/06/2017.
 */
public class AnimaTab extends CreativeTabs
{
	public static AnimaTab Tab = new AnimaTab();

	public AnimaTab()
	{
		super("anima");
	}


	@Override
	public ItemStack getTabIconItem()
	{
		NBTTagCompound tags =  new NBTTagCompound();
		tags.setInteger("pos1_x", 1);
		ItemStack stack = new ItemStack(AnimaItems.LINKER);
		stack.setTagCompound(tags);
		return stack;
	}
}
