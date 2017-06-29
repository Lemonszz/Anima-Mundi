package party.lemons.anima.content.block.tileentity;

import net.minecraft.item.ItemStack;
import party.lemons.anima.content.item.ItemAnimaCharged;
import party.lemons.anima.energy.CapabilityAnima;

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
			int safe = 0;
			extractWorkEnergy();
			ItemStack stack = items.getStackInSlot(currentItem);
			while((stack.isEmpty() || !(stack.getItem() instanceof ItemAnimaCharged)) && safe < 10)
			{
				increaseItem();
				stack = items.getStackInSlot(currentItem);
				safe++;
			}
			increaseItem();
			if(stack.hasCapability(CapabilityAnima.ANIMA, null))
			{
				int chargeAmount = extractEnergy(100);
				int amountLeft = stack.getCapability(CapabilityAnima.ANIMA, null).receiveEnergy(chargeAmount, false);
				addEnergy(amountLeft);
			}
		}
	}
}