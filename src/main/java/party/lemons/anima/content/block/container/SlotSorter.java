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
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		return ItemStack.EMPTY;
	}

	@Override
	public int getSlotStackLimit() {
		return 0;
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return true;
	}

	@Override
	public void putStack(ItemStack stack)
	{
		if (!stack.isEmpty())
		{
			stack.setCount(1);
		}
		super.putStack(stack);
	}

}
