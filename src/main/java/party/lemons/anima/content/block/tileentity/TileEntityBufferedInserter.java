package party.lemons.anima.content.block.tileentity;

import net.minecraft.item.ItemStack;
import party.lemons.anima.content.block.tileentity.connection.NodeLink;
import party.lemons.anima.entity.EntityNodeItem;

/**
 * Created by Sam on 24/06/2017.
 */
public class TileEntityBufferedInserter extends TileEntityInserter
{
	public TileEntityBufferedInserter()
	{
		super();
		this.suckTimeMax = 50;
	}

	@Override
	protected void createNodeItemEntity(ItemStack stack, NodeLink link)
	{
		EntityNodeItem eI = new EntityNodeItem(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5F, stack, link.getLinkPos(), link.getInputSide()).setReturnable(this.pos, link.getOutputSide());
		world.spawnEntity(eI);
	}
}
