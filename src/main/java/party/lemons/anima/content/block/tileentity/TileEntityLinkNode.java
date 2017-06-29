package party.lemons.anima.content.block.tileentity;


/**
 * Created by Sam on 20/06/2017.
 */
public class TileEntityLinkNode extends TileEntityLinkableWorker
{
	public TileEntityLinkNode()
	{
		super(0, 25, 50, MachineLevel.BATTERY);
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
}
