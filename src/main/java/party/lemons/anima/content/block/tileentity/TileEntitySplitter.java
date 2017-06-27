package party.lemons.anima.content.block.tileentity;

/**
 * Created by Sam on 22/06/2017.
 */
public class TileEntitySplitter extends TileEntityLinkableWorker
{
	public TileEntitySplitter()
	{
		super(2,10, 50,  MachineLevel.BATTERY_SMALL);
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
	public int getMaxLinks()
	{
		return 6;
	}

}
