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
		extractWorkEnergy();
		ItemStack stack = items.getStackInSlot(currentItem);
		while(stack.isEmpty() || !(stack.getItem() instanceof ItemAnimaCharged))
		{
			increaseItem();
			stack = items.getStackInSlot(currentItem);
		}
		increaseItem();
		int chargeAmount = extractEnergy(100);
		int amountLeft = stack.getCapability(CapabilityAnima.ANIMA, null).receiveEnergy(chargeAmount, false);

		addEnergy(amountLeft);
	}
}
