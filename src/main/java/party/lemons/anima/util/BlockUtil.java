package party.lemons.anima.util;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.BlockFluidBase;

/**
 * Created by Sam on 22/06/2017.
 */
public class BlockUtil
{
	public static boolean isFluid(IBlockState state)
	{
		boolean flag1 =  state.getBlock() instanceof BlockLiquid;
		boolean flag2 = state.getBlock().getMaterial(state).isLiquid();
		boolean flag3 = state.getBlock() instanceof BlockFluidBase;

		return flag1 || flag2 || flag3;
	}
}
