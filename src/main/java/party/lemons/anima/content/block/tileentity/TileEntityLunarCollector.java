package party.lemons.anima.content.block.tileentity;


import party.lemons.anima.energy.WorldEnergy;

/**
 * Created by Sam on 26/06/2017.
 */
public class TileEntityLunarCollector extends TileEntitySolarCollector
{
	@Override
	public boolean canWork()
	{
		return world.canBlockSeeSky(pos.up()) && world.getWorldTime() % 24000L > 12500 && animaStorage.receiveEnergy(1, true) > 0 && WorldEnergy.canHasAnyEnergy(world);
	}
}
