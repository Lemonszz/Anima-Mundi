package party.lemons.anima.content.block.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import party.lemons.anima.content.block.tileentity.TileEntitySorter;

/**
 * Created by Sam on 23/06/2017.
 */
public class ContainerSorter extends Container
{
	private TileEntitySorter te;

	public ContainerSorter(IInventory playerInventory, TileEntitySorter te)
	{
		this.te = te;

		addSortSlots();
		addStorageSlots();
		addPlayerSlots(playerInventory);
		this.addSlotToContainer(new SlotDefaultSort(new InventoryEmpty(), 0, 182, 131));
	}

	private void addSortSlots()
	{
		int y = 6;
		int x = 8;

		for(ItemStackHandler handler : te.getSortStacks())
		{
			for(int i = 0; i < handler.getSlots(); i++)
			{
				this.addSlotToContainer(new SlotSorter(handler, i, x + (i * 18), y));
			}
			y += 18;
		}
	}

	private void addStorageSlots()
	{
		IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		int xPos = 8;
		int yPos = 118;

		int slotIndex = 0;
		for(int i = 0; i < itemHandler.getSlots(); i++)
		{
			this.addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, xPos, yPos));
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
				int yPos = 149 + (r * 18);
				this.addSlotToContainer(new Slot(playerInventory, c + r * 9 + 10, xPos, yPos));
			}
		}

		for(int r = 0; r < 9; ++r)
		{
			int xPos = 8 + r * 18;
			int yPos = 207;
			this.addSlotToContainer(new Slot(playerInventory, r, xPos, yPos));
		}
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
	{
		try
		{
			if(this.getSlot(slotId) instanceof SlotDefaultSort)
			{

				if(!player.isSneaking())
				{

					te.incrementDefaultSort();

					System.out.println(te.getDefaultSort());

				}
				else
				{
					te.decrementDefaultSort();
				}
			}
			if(!(this.getSlot(slotId) instanceof SlotSorter))
			{
				return super.slotClick(slotId, dragType, clickTypeIn, player);
			}
			else
			{
				this.getSlot(slotId).isItemValid(player.inventory.getItemStack());
			}


		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("FAILED");
			return super.slotClick(slotId, dragType, clickTypeIn, player);

		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		Slot slot = this.inventorySlots.get(index);

		if(index >= 0 && index <= 53)
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack1.setCount(0);
		}
		ItemStack itemstack = ItemStack.EMPTY;

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index <= 62 && index >= 54)
			{
				if (!this.mergeItemStack(itemstack1, 71, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 54, 63, false))
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

	public TileEntitySorter getTile()
	{
		return this.te;
	}

	private class SlotDefaultSort extends Slot
	{

		public SlotDefaultSort(IInventory inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return false;
		}

		@Override
		public void putStack(ItemStack stack)
		{
		}


	}

}
