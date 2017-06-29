package party.lemons.anima.content.block.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import party.lemons.anima.content.block.tileentity.TileEntityCharger;

/**
 * Created by Sam on 29/06/2017.
 */
public class ContainerCharger extends Container
{
	private TileEntityCharger te;
	private IItemHandler items;

	public ContainerCharger(IInventory playerInventory, TileEntityCharger te)
	{
		this.te = te;
		items = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		addStorageSlots();
		addPlayerSlots(playerInventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}

	private void addStorageSlots()
	{
		int xPos = 8;
		int yPos = 7;

		int slotIndex = 0;
		for(int i = 0; i < items.getSlots(); i++)
		{
			this.addSlotToContainer(new SlotItemHandler(items, slotIndex, xPos, yPos));
			slotIndex++;
			xPos += 18;
		}
	}

	private void addPlayerSlots(IInventory playerInventory)
	{
		for(int r = 0; r < 3; ++r)
		{
			for(int c = 0; c < 9; c++)
			{
				int xPos = 8 + (c * 18);
				int yPos = 38 + (r * 18);
				this.addSlotToContainer(new Slot(playerInventory, c + r * 9 + 10, xPos, yPos));
			}
		}

		for(int r = 0; r < 9; ++r)
		{
			int xPos = 8 + r * 18;
			int yPos = 96;
			this.addSlotToContainer(new Slot(playerInventory, r, xPos, yPos));
		}
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < items.getSlots())
			{
				if (!this.mergeItemStack(itemstack1, items.getSlots(), this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, items.getSlots(), false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}
}
