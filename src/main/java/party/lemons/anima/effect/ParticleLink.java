package party.lemons.anima.effect;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.anima.util.BlockUtil;

/**
 * Created by Sam on 20/06/2017.
 */
public class ParticleLink extends ParticleGlowBase
{
	private BlockPos target;

	public ParticleLink(World world, double x, double y, double z,
						double velocityX, double velocityY, double velocityZ, BlockPos target)
	{
		super(world, x, y, z, velocityX, velocityY, velocityZ);
		this.target = target;
		this.canCollide = false;
	}

	@Override
	public void onUpdate()
	{
		updateColour();
		updateMovement();

		BlockPos currentPos = new BlockPos(this.posX, this.posY, this.posZ);
		if(currentPos.equals(target))
		{
			this.setExpired();
		}
	}

}
