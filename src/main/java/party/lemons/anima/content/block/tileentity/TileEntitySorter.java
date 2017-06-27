package party.lemons.anima.content.block.tileentity;

import net.minecraft.client.particle.Particle;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import party.lemons.anima.content.block.tileentity.connection.NodeLink;
import party.lemons.anima.effect.ParticleLinkColoured;
import party.lemons.anima.entity.EntityNodeItem;
import party.lemons.anima.util.BlockUtil;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Sam on 23/06/2017.
 */
public class TileEntitySorter extends TileEntityLinkableWorker
{
	private ArrayList<ItemStackHandler> stacks;
	private int currentOutput;
	private int defaultSort;

	public TileEntitySorter()
	{
		super(5, 10, 10, MachineLevel.MEDIUM);
		stacks = new ArrayList<>();
		for(int i = 0; i < EnumFacing.values().length; i++)
		{
			stacks.add(new ItemStackHandler(9));
		}

		currentOutput = 0;
		defaultSort = -1;
	}

	@Override
	public boolean canWork()
	{
		return false;
	}

	@Override
	public void work()
	{

	}

	public ArrayList<ItemStackHandler> getSortStacks()
	{
		return stacks;
	}

	public ItemStackHandler getItemHandlerForSide(EnumFacing side)
	{
		return stacks.get(side.ordinal());
	}

	@Override
	public int getMaxLinks()
	{
		return 6;
	}

	public boolean isLinkedTo(World world, BlockPos pos, EnumFacing facing)
	{
		for(NodeLink link: links)
		{
			if(BlockUtil.isSamePosition(pos, link.getLinkPos()) && link.getOutputSide() == facing)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean canLinkTo(World world, BlockPos pos, EnumFacing output, boolean checkDuplicates)
	{
		if(isLinkedTo(world, pos, output) && checkDuplicates)
		{
			return false;
		}

		TileEntity te = world.getTileEntity(pos);
		if(te != null && te instanceof ILinkableTile)
		{
			return true;
		}

		for(int i = 0; i < links.size(); i++)
		{
			NodeLink link = links.get(i);
			if(BlockUtil.isSamePosition(link.getLinkPos(), pos))
			{
				links.remove(link);
			}
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		stacks.clear();
		NBTTagList sortStacks = compound.getTagList("sortstacks", 10);
		for(int i = 0; i < sortStacks.tagCount(); i++)
		{
			ItemStackHandler handler = new ItemStackHandler(9);
			handler.deserializeNBT(sortStacks.getCompoundTagAt(i));
			stacks.add(handler);
		}

		if(compound.hasKey("defaultsort"))
		{
			defaultSort = compound.getInteger("defaultsort");
		}
		super.readFromNBT(compound);
	}


	@Override
	public boolean allowDuplicateFaceLinks()
	{
		return false;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		NBTTagList sortStacks = new NBTTagList();
		for(ItemStackHandler itemHandler : stacks)
		{
			sortStacks.appendTag(itemHandler.serializeNBT());
		}
		compound.setTag("sortstacks", sortStacks);
		compound.setInteger("defaultsort", defaultSort);
		return super.writeToNBT(compound);
	}

	public boolean sideHasItem(ItemStack stack, EnumFacing side)
	{
		ItemStackHandler handler = this.getItemHandlerForSide(side);
		if(!stack.isEmpty())
		{
			for(int i = 0; i < items.getSlots(); i++)
			{
				ItemStack sortStack = handler.getStackInSlot(i);
				if(!sortStack.isEmpty() && ItemStack.areItemsEqual(stack, sortStack))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void suck()
	{
		checkAndRemoveInvalidLinks();

		if(links.size() > 0 && !isEmpty() & !world.isRemote)
		{
			boolean isDefault = nodeIndex == defaultSort;
			NodeLink link = nextLink();
			if(canLinkTo(world, link.getLinkPos(), link.getOutputSide(), false))
			{
				ItemStack sendStack = ItemStack.EMPTY;

				for(int i = 0; i < items.getSlots(); i++)
				{
					if(!items.getStackInSlot(i).isEmpty())
					{
						if(sideHasItem(items.getStackInSlot(i), link.getOutputSide()) || isDefault)
						{
							sendStack = items.getStackInSlot(i);
							items.extractItem(i, sendStack.getCount(), false);

							EntityNodeItem eI = new EntityNodeItem(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5F, sendStack, link.getLinkPos(), link.getInputSide());
							world.spawnEntity(eI);
							break;
						}
					}
				}
			}
			else
			{
				removeLink(link);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Particle createLinkParticle(double xpos, double ypos, double zpos, double velocityX, double velocityY, double velocityZ, NodeLink link)
	{
		Color col = getColourForSide(link.getOutputSide());
		float red = (float)col.getRed() / 255F;
		float green =  (float)col.getGreen() / 255F;
		float blue =  (float)col.getBlue() / 255F;

		return  new ParticleLinkColoured(world, xpos, ypos, zpos, velocityX, velocityY, velocityZ, link.getLinkPos(), red, green, blue);
	}

	public Color getColourForSide(EnumFacing outputSide)
	{
		switch(outputSide)
		{
			case DOWN:
				return new Color(134, 66, 244);
			case UP:
				return new Color(93, 255, 61);
			case NORTH:
				return new Color(206, 47, 39);
			case SOUTH:
				return new Color(66, 133, 206);
			case WEST:
				return new Color(250, 255, 0);
			case EAST:
				return new Color(255, 255, 255);
		}

		return Color.white;
	}

	public int getDefaultSort()
	{
		return defaultSort;
	}

	public void incrementDefaultSort()
	{
		defaultSort++;
		if(defaultSort > 5)
		{
			defaultSort = -1;
		}
	}

	public void decrementDefaultSort()
	{
		defaultSort--;
		if(defaultSort < -1)
		{
			defaultSort = 5;
		}
	}

}
