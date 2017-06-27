package party.lemons.anima.content.block.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.anima.effect.ParticleGlow;
import party.lemons.anima.energy.AnimaStorage;
import party.lemons.anima.energy.CapabilityAnima;
import party.lemons.anima.entity.EntityBlockSuck;
import party.lemons.anima.util.BlockUtil;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Sam on 19/06/2017.
 */
public class TileEntityExtractor extends TileEntityLinkableWorker
{

	private static final int BREAK_COST = 25;
	private static final int WORK_TIME_MAX = 40;

	public TileEntityExtractor()
	{
		super(WORK_TIME_MAX, BREAK_COST, 25, MachineLevel.MEDIUM);
	}

	private boolean isFree()
	{
		AxisAlignedBB bb = new AxisAlignedBB(getPos().up());
		List<EntityBlockSuck> en = world.getEntitiesWithinAABB(EntityBlockSuck.class, bb);
		boolean flag3 = en.isEmpty();


		return world.isAirBlock(getPos().up()) && pos.getY() != 255 && flag3;
	}

	@Override
	public boolean canWork()
	{
		return isFree();
	}

	@SideOnly(Side.CLIENT)
	public void createParticles()
	{
		if(world.isRemote)
		{
			for(int i = 0; i < 5; i++)
			{
				double x = pos.getX() + 1 - world.rand.nextFloat();
				double z = pos.getZ() + 1 - world.rand.nextFloat();
				double y = (pos.getY() - 1) + 1 - world.rand.nextFloat()/2;

				ParticleGlow newEffect = new ParticleGlow(world, x, y, z,0, -0.25F, 0);
				Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);

			}
		}
	}


	@Override
	public void work()
	{
		if(canExtractEnergy())
		{
			if(world.isRemote)
			{
				createParticles();
			}

			extractEnergy();

			BlockPos nextTop = getPos().down();

			while(nextTop.getY() >= 0 && world.isAirBlock(nextTop) || BlockUtil.isFluid(world.getBlockState(nextTop)))
			{
				nextTop = nextTop.down();
			}

			IBlockState breakState = world.getBlockState(nextTop);
			if(breakState.getBlock().getBlockHardness(breakState, world, nextTop) > -1 && !world.isRemote)
			{

				world.setBlockState(getPos().up(), breakState);
				world.destroyBlock(nextTop, false);
			}
		}
	}

	@Override
	public boolean canSendEnergy()
	{
		return false;
	}

}
