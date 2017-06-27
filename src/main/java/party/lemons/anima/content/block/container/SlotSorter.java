package party.lemons.anima.content.block.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by Sam on 23/06/2017.
 */
public class SlotSorter extends SlotItemHandler
{
	public SlotSorter(ItemStackHandler handler, int i, int i1, int y)
	{
		super(handler, i, i1, y);
	}

	@Override
	public boolean canTakeStack(EntityPlayer playerIn)
	{
		return false;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if(!this.getStack().isEmpty())
		{
			ItemStack setStack = stack.copy();
			setStack.setCount(1);
			this.putStack(ItemStack.EMPTY);
		}
		else
		{
			if(!stack.isEmpty())
			{
				ItemStack setStack = stack.copy();
				setStack.setCount(1);
				this.putStack(setStack);
			}
		}
		return false;
	}


}
