package party.lemons.anima.content.block.tileentity;

import net.minecraft.item.ItemStack;
import party.lemons.anima.crafting.AnimaGeneratorRecipes;

/**
 * Created by Sam on 27/06/2017.
 */
public class TileEntityAnimaGenerator extends TileEntityLinkableWorker
{
	public TileEntityAnimaGenerator()
	{
		super(50, 0, 25, MachineLevel.BATTERY);
	}

	@Override
	public boolean canWork()
	{
		return !isEmpty() && animaStorage.receiveEnergy(1, true) >= 1;
	}

	@Override
	public void work()
	{
		ItemStack burnStack = ItemStack.EMPTY;
		int amount = 0;
		for(int i = 0; i < items.getSlots(); i++)
		{
			if(!items.getStackInSlot(i).isEmpty())
			{
				ItemStack stack = items.getStackInSlot(i);
				if(AnimaGeneratorRecipes.getValue(stack) > 0)
				{
					burnItem(items.getStackInSlot(i));
					amount = AnimaGeneratorRecipes.getValue(stack);
					stack.shrink(1);
					break;
				}
			}
		}
		workTime = workMax - (amount / 12);
	}

	public void burnItem(ItemStack stack)
	{
		animaStorage.receiveEnergy(AnimaGeneratorRecipes.getValue(stack.getItem()), false);
	}
}
