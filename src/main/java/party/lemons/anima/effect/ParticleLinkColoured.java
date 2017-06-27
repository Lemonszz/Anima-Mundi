package party.lemons.anima.effect;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Sam on 20/06/2017.
 */
public class ParticleLinkColoured extends ParticleLink
{

	public ParticleLinkColoured(World world, double x, double y, double z,
						double velocityX, double velocityY, double velocityZ, BlockPos target, float r, float g, float b)
	{
		super(world, x, y, z, velocityX, velocityY, velocityZ, target);

		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;

	}

	@Override
	public void onUpdate()
	{
		updateMovement();
		super.onUpdate();
	}

}
