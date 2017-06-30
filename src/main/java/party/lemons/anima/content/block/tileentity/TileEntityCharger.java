package party.lemons.anima.content.block.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.Sys;
import party.lemons.anima.content.item.ItemAnimaCharged;
import party.lemons.anima.energy.CapabilityAnima;
import party.lemons.anima.energy.IAnimaStorage;

/**
 * Created by Sam on 29/06/2017.
 */
public class TileEntityCharger extends TileEntityLinkableWorker
{
	private int currentItem;

	public TileEntityCharger()
	{
		super(10, 10, 100, MachineLevel.BATTERY);
		currentItem = 0;
	}

	@Override
	public void update()
	{
		super.update();
	}

	@Override
	public boolean canWork()
	{
		return !isEmpty();
	}

	@Override
	public boolean canSendEnergy()
	{
		return false;
	}

	@Override
	public boolean canSendItem(ItemStack stack)
	{
		if(stack.isEmpty() || !(stack.getItem() instanceof ItemAnimaCharged))
		{
			return true;
		}

		ItemAnimaCharged it = (ItemAnimaCharged) stack.getItem();
		return !(it.getCurrentCharge(stack) < it.getMaxCharge(stack));
	}

	private void increaseItem()
	{
		currentItem++;
		if(currentItem > items.getSlots() - 1)
		{
			currentItem = 0;
		}
	}

	@Override
	public void work()
	{
		if(!world.isRemote)
		{
			extractWorkEnergy();
			ItemStack stack = items.getStackInSlot(currentItem);
			while(!isEmpty() && (stack.isEmpty() || !(stack.getItem() instanceof ItemAnimaCharged)))
			{
				increaseItem();
				stack = items.getStackInSlot(currentItem);
			}
			increaseItem();

			int chargeAmount = extractEnergy(100);

			ItemAnimaCharged ch = (ItemAnimaCharged) stack.getItem();
			int amountLeft = ch.addCharge(stack, chargeAmount);
			addEnergy(amountLeft);
		}
	}
}
