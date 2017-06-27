package party.lemons.anima.content.block.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

/**
 * Created by Sam on 21/06/2017.
 */
public class TileEntityInserter extends TileEntityLinkableWorker
{
	public TileEntityInserter()
	{
		super(10, 10, 10, MachineLevel.LOW);
	}

	@Override
	public boolean canWork()
	{
		return true;
	}

	@Override
	public void work()
	{

	}

	@Override
	public boolean canLinkTo(World world, BlockPos pos, EnumFacing fromFace, boolean checkDuplicates)
	{
		TileEntity te = world.getTileEntity(pos);
		if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
		{
			return true;
		}
		return false;
	}
}
