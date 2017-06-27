package party.lemons.anima.util;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Sam on 22/06/2017.
 */
public class BlockUtil
{
	public static boolean isFluid(IBlockState state)
	{
		boolean flag1 =  state.getBlock() instanceof BlockLiquid;
		boolean flag2 = state.getBlock().getMaterial(state).isLiquid();
		boolean flag3 = state.getBlock() instanceof BlockLiquid;

		return flag1 || flag2 || flag3;
	}

	public static boolean isSamePosition(BlockPos pos1, BlockPos pos2)
	{
		return pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ();
	}
}
