package party.lemons.anima.content.block.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

/**
 * Created by Sam on 21/06/2017.
 */
public class TileEntitySucker extends TileEntityLinkableWorker
{
	public TileEntitySucker()
	{
		super(20, 10, 10,  MachineLevel.LOW);
	}


	@Override
	public boolean canWork()
	{
		return true;
	}

	private EnumFacing[] checkFaces = {
		EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST, EnumFacing.UP, EnumFacing.DOWN
	};

	@Override
	public void work()
	{

		for(EnumFacing facing : checkFaces)
		{
			TileEntity tile = world.getTileEntity(pos.offset(facing));
			if(tile != null)
			{
				if(tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()))
				{
					IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
					ItemStack outStack = ItemStack.EMPTY;
					for(int i = 0; i < handler.getSlots(); i++)
					{
						outStack = handler.extractItem(i, handler.getStackInSlot(i).getCount(), false);
						if(!outStack.isEmpty())
						{
							break;
						}
					}
					ItemStack remain = addItem(outStack);
					ItemHandlerHelper.insertItem(handler, remain ,false);
				}
			}
		}
	}
}
