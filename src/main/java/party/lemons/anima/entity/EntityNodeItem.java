package party.lemons.anima.entity;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import party.lemons.anima.config.AnimaConfig;

/**
 * Created by Sam on 20/06/2017.
 */
public class EntityNodeItem extends EntityItem
{
	private BlockPos targetPos;
	private EnumFacing inputSide;
	private int age = 0;

	private BlockPos returnPos;
	private EnumFacing returnSide;

	public EntityNodeItem(World world)
	{
		super(world);
		//no
	}

	public EntityNodeItem(World worldIn, double x, double y, double z, ItemStack stack, BlockPos targetPos, EnumFacing inputSide)
	{
		super(worldIn, x, y, z, stack);

		this.targetPos = targetPos;
		this.inputSide = inputSide;
	}

	public EntityNodeItem setReturnable(BlockPos returnPos, EnumFacing returnSide)
	{
		this.returnPos = returnPos;
		this.returnSide = returnSide;

		return this;
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn)
	{
		if(!this.world.isRemote && AnimaConfig.allowPlayerPickup)
		{
			if(age < 10)
				return;
			ItemStack itemstack = this.getItem();
			Item item = itemstack.getItem();
			int i = itemstack.getCount();

			int hook = net.minecraftforge.event.ForgeEventFactory.onItemPickup(this, entityIn);
			if(hook < 0)
				return;

			if(entityIn.inventory.addItemStackToInventory(itemstack))
			{
				net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerItemPickupEvent(entityIn, this);
				entityIn.onItemPickup(this, i);

				if(itemstack.isEmpty())
				{
					this.setDead();
					itemstack.setCount(i);
				}

				entityIn.addStat(StatList.getObjectsPickedUpStats(item), i);
			}
		}
	}

	private void onInvalidArrival(ItemStack stack)
	{
		if(!canReturn())
		{
			spawnRegularItem(stack);
		}
		else
		{
			spawnReturnEntity(stack);
		}
		this.setDead();
	}

	private void spawnReturnEntity(ItemStack stack)
	{
		EntityNodeItem ni = new EntityNodeItem(world, posX, posY, posZ, stack, returnPos, returnSide);
		world.spawnEntity(ni);

	}

	private void spawnRegularItem(ItemStack stack)
	{
		EntityItem ei = new EntityItem(world, posX, posY, posZ, stack);
		world.spawnEntity(ei);
		this.setDead();
	}

	private void attemptInsert(TileEntity te, EnumFacing facing)
	{
		IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
		ItemStack returned = ItemHandlerHelper.insertItem(itemHandler, getItem(), false);
		if(!returned.isEmpty())
		{
			onInvalidArrival(returned);
		}
		this.setDead();
	}

	protected boolean canReturn()
	{
		return returnPos != null;
	}

	@Override
	public void onUpdate()
	{
		age++;
		if(this.getItem().isEmpty())
		{
			this.setDead();
		}

		if(!this.world.isRemote)
		{
			this.setFlag(6, this.isGlowing());
		}

		noClip = true;
		if(!this.world.isRemote)
		{
			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
			BlockPos p = new BlockPos(posX, posY, posZ);
			if(targetPos != null)
			{
				if(this.targetPos.getX() == p.getX() && targetPos.getY() == p.getY() && targetPos.getZ() == p.getZ())
				{
					TileEntity te = world.getTileEntity(p);
					if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inputSide))
					{
						attemptInsert(te, inputSide);
					}
					else if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
					{
						attemptInsert(te, null);
					}
					else
					{
						onInvalidArrival(getItem());
					}
				}
				Vec3d direction;

				direction = new Vec3d(targetPos.getX() + 0.5, targetPos.getY() + 0.1, targetPos.getZ() + 0.5).subtract(posX, posY, posZ);
				direction.normalize();

				final double SPEED_IN_BLOCKS_PER_SECOND = 1;
				final double TICKS_PER_SECOND = 20;
				final double SPEED_IN_BLOCKS_PER_TICK = SPEED_IN_BLOCKS_PER_SECOND / TICKS_PER_SECOND;

				motionX = SPEED_IN_BLOCKS_PER_TICK * direction.x; // how much to increase the x position every tick
				motionY = SPEED_IN_BLOCKS_PER_TICK * direction.y; // how much to increase the y position every tick
				motionZ = SPEED_IN_BLOCKS_PER_TICK * direction.z; // how much to increase the z position every tick
			}
		}


		ItemStack item = this.getItem();
		if(item.isEmpty())
		{
			this.setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		if(targetPos != null)
		{
			compound.setInteger("target_x", targetPos.getX());
			compound.setInteger("target_y", targetPos.getY());
			compound.setInteger("target_z", targetPos.getZ());
			compound.setInteger("targetSide", inputSide.ordinal());
		}

		if(canReturn())
		{
			compound.setInteger("return_x", returnPos.getX());
			compound.setInteger("return_y", returnPos.getY());
			compound.setInteger("return_z", returnPos.getZ());
			compound.setInteger("returnSide", returnSide.ordinal());
		}

		super.writeEntityToNBT(compound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		int x = compound.getInteger("target_x");
		int y = compound.getInteger("target_y");
		int z = compound.getInteger("target_z");
		inputSide = EnumFacing.values()[compound.getInteger("targetSide")];
		targetPos = new BlockPos(x, y, z);

		if(compound.hasKey("return_x"))
		{
			int xr = compound.getInteger("return_x");
			int yr = compound.getInteger("return_y");
			int zr = compound.getInteger("return_z");
			returnSide = EnumFacing.values()[compound.getInteger("returnSide")];
			returnPos = new BlockPos(xr, yr, zr);
		}

		super.readEntityFromNBT(compound);
	}
}
