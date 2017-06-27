package party.lemons.anima.content.block.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import party.lemons.anima.content.block.BlockDestroyer;
import party.lemons.anima.entity.EntityBlockSuck;

import java.util.List;

/**
 * Created by Sam on 19/06/2017.
 */
public class TileEntityDestroyer extends TileEntityLinkableWorker
{
	private static final int WORK_MAX = 10;
	private static final int WORK_COST = 25;

	public TileEntityDestroyer()
	{
		super(WORK_MAX, WORK_COST, 25, MachineLevel.MEDIUM);
	}

	@Override
	public boolean canWork()
	{

		EnumFacing facing = getFacing();
		IBlockState frontState = getFrontState();
		boolean flag1 = !world.isAirBlock(getFrontPos());
		boolean flag2 = frontState.getBlock().getBlockHardness(frontState, world, pos.offset(facing)) > -1;

		AxisAlignedBB bb = new AxisAlignedBB(getFrontPos());
		List<EntityBlockSuck> en = world.getEntitiesWithinAABB(EntityBlockSuck.class, bb);
		boolean flag3 = en.isEmpty();

		return flag1 && flag2 && flag3;
	}

	public EnumFacing getFacing()
	{
		return world.getBlockState(pos).getValue(BlockDestroyer.FACING);
	}

	public BlockPos getFrontPos()
	{
		return pos.offset(getFacing());
	}

	public IBlockState getFrontState()
	{
		BlockPos offsetPos = getFrontPos();
		IBlockState frontBlock = world.getBlockState(offsetPos);

		return frontBlock;
	}

	@Override
	public void work()
	{
		if(!world.isRemote && canExtractEnergy())
		{
			extractEnergy();
			EntityBlockSuck e = new EntityBlockSuck(world, pos);
			e.setState(getFrontState());

			BlockPos f = getFrontPos();
			e.setPosition(f.getX() + 0.5F, f.getY(), f.getZ() + 0.5F);

			world.destroyBlock(getFrontPos(), false);

			world.spawnEntity(e);
		}

	}

	@Override
	public boolean canSendEnergy()
	{
		return false;
	}
}
