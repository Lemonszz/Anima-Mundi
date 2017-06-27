package party.lemons.anima.content.block.tileentity;

import net.minecraft.client.Minecraft;
import party.lemons.anima.effect.ParticleGlow;

/**
 * Created by Sam on 26/06/2017.
 */
public class TileEntitySolarCollector extends TileEntityLinkableWorker
{
	public TileEntitySolarCollector()
	{
		super(25, 0, 25, MachineLevel.MEDIUM);
	}

	@Override
	public boolean canWork()
	{
		return world.canBlockSeeSky(pos.up()) && world.getWorldTime() % 24000L < 12500 && animaStorage.receiveEnergy(1, true) > 0;
	}

	@Override
	public void work()
	{
		animaStorage.receiveEnergy(5, false);

		if(world.isRemote)
		{
			for(int i = 0; i < 2; i++)
			{
				double x = pos.getX() + 1 - world.rand.nextFloat();
				double z = pos.getZ() + 1 - world.rand.nextFloat();
				double y = (pos.getY() + 1) + world.rand.nextInt(10);

				ParticleGlow newEffect = new ParticleGlow(world, x, y, z,0, -0.25F, 0);
				Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
			}
		}
	}
}
