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
		/*
		for(int i = 0; i < links.size(); i++)
		{
			if(!canLinkTo(world, links.get(i).getLinkPos(), links.get(i).getOutputSide(), false))
			{
				links.remove(links.get(i));
			}
		}
		return links.size() > 0;*/


		return true;
	}

	@Override
	public void work()
	{
	}
}
