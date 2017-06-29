package party.lemons.anima.content.block.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.Sys;
import party.lemons.anima.config.AnimaConfig;
import party.lemons.anima.content.block.tileentity.connection.NodeLink;
import party.lemons.anima.effect.ParticleLink;
import party.lemons.anima.energy.CapabilityAnima;
import party.lemons.anima.energy.IAnimaStorage;
import party.lemons.anima.entity.EntityNodeItem;
import party.lemons.anima.util.BlockUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Created by Sam on 20/06/2017.
 */
public abstract class TileEntityLinkableWorker extends TileEntityWorker implements ILinkableTile, IAnalysable
{
	protected ArrayList<NodeLink> links;
	protected ItemStackHandler items = new ItemStackHandler(9);
	protected int suckTime, suckTimeMax, nodeIndex, particleTime, particleTimeMax;
	protected final int MAX_SEND_ENERGY = 20;

	public TileEntityLinkableWorker(int workMax, int workCost, int maxTransfer, MachineLevel level)
	{
		super(workMax, workCost, maxTransfer, level);
		suckTime = 0;
		suckTimeMax = 5;
		nodeIndex = 0;
		particleTime = 0;
		particleTimeMax = AnimaConfig.linkParticleInterval;
		links = new ArrayList<>();
	}

	@Override
	public void update()
	{
		suckTime++;
		particleTime++;
		if(suckTime >= suckTimeMax)
		{
			suckTime = 0;
			suck();
		}

		if(particleTime >= particleTimeMax && AnimaConfig.showLinkParticles)
		{
			particleTime = 0;
			if(world.isRemote)
			{
				linkParticles();
			}
		}
		super.update();
	}

	@Override
	public int getMaxLinks()
	{
		return 1;
	}

	@Override
	public boolean addLink(BlockPos pos, EnumFacing inputSide, EnumFacing outputSide)
	{
		if(links.size() < getMaxLinks())
		{
			links.add(new NodeLink(pos, inputSide, outputSide));
			return true;
		}

		return false;
	}

	@Override
	public boolean isLinkedTo(World world, BlockPos pos)
	{
		for(NodeLink link: links)
		{
			if(BlockUtil.isSamePosition(pos, link.getLinkPos()))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasMaxLinks()
	{
		return links.size() >= getMaxLinks();
	}

	@Override
	public boolean allowDuplicateFaceLinks()
	{
		return true;
	}

	@Override
	public void clearLinks()
	{
		links.clear();
	}

	@Override
	public ItemStack addItem(ItemStack stack)
	{
		ItemStack insert = stack;

		for(int i = 0; i < items.getSlots(); i++)
		{
			insert = ItemHandlerHelper.insertItem(items, insert, false);
		}
		return insert;
	}

	@Override
	public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		SPacketUpdateTileEntity packet = new SPacketUpdateTileEntity(pos, -1, writeToNBT(new NBTTagCompound()));
		return packet;
	}


	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}

		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T) items;
		}

		return super.getCapability(capability, facing);
	}


	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		if(compound.hasKey("links"))
		{
			NBTTagList linktags = compound.getTagList("links", 10);
			for(int i = 0; i < linktags.tagCount(); i++)
			{
				NBTTagCompound com = linktags.getCompoundTagAt(i);
				links.add(NodeLink.fromNBT(com));
			}
		}
		items.deserializeNBT(compound.getCompoundTag("items"));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{

		NBTTagList list = new NBTTagList();
		for(NodeLink link : links)
		{
			list.appendTag(link.writeToNBT());
		}
		compound.setTag("links", list);
		compound.setTag("items", items.serializeNBT());
		return super.writeToNBT(compound);
	}

	public NodeLink nextLink()
	{
		if(nodeIndex >= links.size())
		{
			nodeIndex = 0;
		}
		NodeLink returnNode = links.get(nodeIndex);
		nodeIndex++;
		return returnNode;
	}

	public boolean isEmpty()
	{
		for(int i = 0; i < items.getSlots(); i++)
		{
			if(!items.getStackInSlot(i).isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@SideOnly(Side.CLIENT)
	public Particle createLinkParticle(double xpos, double ypos, double zpos, double velocityX, double velocityY, double velocityZ, NodeLink link)
	{
		return new ParticleLink(world, xpos, ypos, zpos, velocityX, velocityY, velocityZ, link.getLinkPos());
	}

	@SideOnly(Side.CLIENT)
	public void linkParticles()
	{
		if(world.isRemote)
		{
			for(int i = 0; i < links.size(); i++)
			{
				NodeLink link = links.get(i);
				if(canLinkTo(world, link.getLinkPos(), link.getOutputSide(), false))
				{
					BlockPos linkPos = link.getLinkPos();
					double xpos = pos.getX() + 0.5;
					double ypos = pos.getY() + 0.5;
					double zpos = pos.getZ() + 0.5;
					double velocityX = 0;
					double velocityY = 0;
					double velocityZ = 0;
					Vec3d direction;
					direction = new Vec3d(linkPos.getX(), linkPos.getY(), linkPos.getZ()).subtract(getPos().getX(), getPos().getY(), getPos().getZ());
					direction.normalize();

					final double SPEED_IN_BLOCKS_PER_SECOND = 2.0;
					final double TICKS_PER_SECOND = 20;
					final double SPEED_IN_BLOCKS_PER_TICK = SPEED_IN_BLOCKS_PER_SECOND / TICKS_PER_SECOND;

					velocityX = SPEED_IN_BLOCKS_PER_TICK * direction.x;
					velocityY = SPEED_IN_BLOCKS_PER_TICK * direction.y;
					velocityZ = SPEED_IN_BLOCKS_PER_TICK * direction.z;

					Minecraft.getMinecraft().effectRenderer.addEffect(createLinkParticle(xpos, ypos, zpos, velocityX, velocityY, velocityZ, link));
				}else
				{
					links.remove(link);
				}
			}
		}
	}

	protected void checkAndRemoveInvalidLinks()
	{
		for(int i = 0; i < links.size(); i++)
		{
			NodeLink link = links.get(i);
			if(!canLinkTo(world, link.getLinkPos(), link.getInputSide(), false))
			{
				removeLink(link);
			}
		}
	}

	protected void createNodeItemEntity(ItemStack stack, NodeLink link)
	{
		EntityNodeItem eI = new EntityNodeItem(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5F, stack, link.getLinkPos(), link.getInputSide());
		world.spawnEntity(eI);
	}

	public void suck()
	{
		checkAndRemoveInvalidLinks();
		sendEnergy();

		if(links.size() > 0 && !isEmpty() && canSendItems())
		{
			NodeLink link = nextLink();

			if(!world.isRemote && canLinkTo(world, link.getLinkPos(), link.getOutputSide(), false))
			{
				//Send item
				ItemStack sendStack = ItemStack.EMPTY;
				for(int i = 0; i < items.getSlots(); i++)
				{
					if(!items.getStackInSlot(i).isEmpty())
					{
						sendStack = items.getStackInSlot(i);
						items.extractItem(i, sendStack.getCount(), false);
						break;
					}
				}
				if(!sendStack.isEmpty())
				{
					createNodeItemEntity(sendStack, link);
				}
			}
			else
			{
				removeLink(link);
			}
		}
	}

	@Override
	public boolean canLinkTo(World world, BlockPos pos, EnumFacing fromFace, boolean checkDuplicates)
	{
		if(isLinkedTo(world, pos) && checkDuplicates)
		{
			return  false;
		}

		TileEntity te = world.getTileEntity(pos);
		if(te != null && te instanceof ILinkableTile)
		{
			return true;
		}

		return false;
	}

	public void removeLink(NodeLink link)
	{
		links.remove(link);
	}


	@Override
	public boolean hasLink(EnumFacing facing)
	{
		for(NodeLink links : links)
		{
			if(links.getOutputSide() == facing)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean canSendEnergy()
	{
		return true;
	}

	public void sendEnergy()
	{
		if(!canSendEnergy() || links.size() <= 0)
		{
			return;
		}

		int maxToSend = animaStorage.extractEnergy(MAX_SEND_ENERGY, true);
		int amountOfLinks = links.size();
		int sendToEach = maxToSend / amountOfLinks;

		for(NodeLink link : links)
		{
			sendEnergyToNode(link, sendToEach);
		}
	}

	private void sendEnergyToNode(NodeLink link, int amount)
	{
		TileEntity te = world.getTileEntity(link.getLinkPos());
		if(te != null && te.hasCapability(CapabilityAnima.ANIMA, link.getInputSide()))
		{
			IAnimaStorage otherStorage = te.getCapability(CapabilityAnima.ANIMA, link.getInputSide());
			int amountLeft  = otherStorage.receiveEnergy(amount, false);
			animaStorage.extractEnergy(amountLeft, false);
		}
	}

	public ItemStackHandler getItems()
	{
		return items;
	}

	public int getLinkAmount()
	{
		return links.size();
	}

	@Override
	public boolean canSendItems()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawInformation(int startX, int startY)
	{
		Minecraft mc = Minecraft.getMinecraft();
		ItemStack drawStack = new ItemStack(getBlockType());

		int dX = startX + 12;
		int dY = startY - 12;

		mc.getRenderItem().renderItemAndEffectIntoGUI(drawStack, dX, dY);
		dY += 5;
		mc.fontRenderer.drawString(getBlockType().getLocalizedName(), dX + 20, dY, 0xFFFFFF);
		dY += 16;

		boolean canSendPower = canSendEnergy();
		boolean canSendItems = canSendItems();

		String canPower = canSendPower ? "anima.info.cansendpower" : "anima.info.cannotsendpower";
		String canItems = canSendItems ? "anima.info.cansenditems" : "anima.info.cannotsenditems";
		int canPowerColour = canSendPower ? 0x00FF00 : 0xFF0000;
		int canItemColour = canSendItems ? 0x00FF00 : 0xFF0000;
		mc.fontRenderer.drawString(I18n.translateToLocal(canPower), dX, dY, canPowerColour);
		dY += 12;
		mc.fontRenderer.drawString(I18n.translateToLocal(canItems), dX, dY, canItemColour);
		dY += 12;
		mc.fontRenderer.drawString(I18n.translateToLocal("anima.info.storedpower") + " " + animaStorage.getEnergyStored(), dX, dY, 0x77af6e);
		dY += 12;
		mc.fontRenderer.drawString(I18n.translateToLocal("anima.info.maxpower") + " " + animaStorage.getMaxEnergyStored(), dX, dY, 0x77af6e);
		dY += 12;

		mc.fontRenderer.drawString(getLinkAmount() + "/" + getMaxLinks() + " " + I18n.translateToLocal("anima.info.links"), dX, dY, 0x4286f4);
	}
}
