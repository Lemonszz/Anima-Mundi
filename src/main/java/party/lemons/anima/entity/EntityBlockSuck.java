package party.lemons.anima.entity;

import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.anima.content.block.tileentity.ILinkableTile;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Sam on 21/06/2017.
 */
public class EntityBlockSuck extends Entity
{
	private static final DataParameter<Optional<IBlockState>> STATE = EntityDataManager.createKey(EntityBlockSuck.class, DataSerializers.OPTIONAL_BLOCK_STATE);

	private final int MAX_TIME = 48;
	private int current_time = 0;

	private BlockPos targetOutput;

	public EntityBlockSuck(World world)
	{
		super(world);
		this.setSize(1F, 1F);
	}

	public EntityBlockSuck(World worldIn, BlockPos output)
	{
		super(worldIn);
		this.setSize(1F, 1F);
		this.targetOutput = output;
	}

	public void setState(IBlockState state)
	{
		this.dataManager.set(STATE, Optional.fromNullable(state));
	}
	protected boolean canTriggerWalking()
	{
		return false;
	}
	public boolean canBePushed()
	{
		return false;
	}
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	@Nullable
	public AxisAlignedBB getCollisionBox(Entity entityIn)
	{
		return entityIn.getEntityBoundingBox();
	}

	public void onUpdate()
	{

	/*	if(getTime() % 40 == 0)
		{
			this.playSound(EtherealSounds.BLOCK_CRACK, 0.3F + (rand.nextInt(8)/ 10), rand.nextFloat());
		}
	*/

		current_time++;
		if(current_time > MAX_TIME)
		{
			this.setDead();
		}
		if(getTime()/ 2 % 2 == 0)
		{
			if(!world.isRemote && getState() != null)
				((WorldServer)this.world).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY + 0.5, this.posZ, getTime() / 2, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] {Block.getStateId(getState())});

		}


		super.onUpdate();
	}

	public int getTime()
	{
		return current_time;
	}

	public IBlockState getState()
	{
		return (IBlockState) ((Optional) this.dataManager.get(STATE)).orNull();
	}

	@Override
	public void setDead()
	{
		for(int i = 0; i < 20; i++)
		{
			double pX = (posX - 0.5F) + rand.nextFloat();
			double pY = (posY) + rand.nextFloat();
			double pZ = (posZ - 0.5F) + rand.nextFloat();

			//	world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pX, pY, pZ, 0, -0.025, 0);

			if(rand.nextInt(6) == 0)
			{
				//	world.spawnParticle(EnumParticleTypes.FLAME, pX, pY, pZ, 0, -0.025, 0);
			}

			if(rand.nextInt(10) == 0)
			{
				//world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pX, pY, pZ, 0, 0.025, 0);
			}

			int[] par = new int[1];//
			//this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, pX, pY, pZ, 0,0,0, par);
			if(!world.isRemote && getState() != null)
			{
				par[0] =  Block.getStateId(getState());
				((WorldServer)this.world).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY + 0.5, this.posZ, 20, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] {Block.getStateId(getState())});
			}

		}

		boolean flag = true;

		if(getState() != null)
		{
			List<ItemStack> stacks = getState().getBlock().getDrops(world, getPosition(), getState(), 0);

			//Output
			if(!world.isRemote)
			{
				if(targetOutput != null)
				{
					TileEntity te = world.getTileEntity(targetOutput);
					if(te != null && te instanceof ILinkableTile)
					{
						flag = false;
						for(ItemStack s : stacks)
						{
							((ILinkableTile) te).addItem(s);
						}
					}
				}

				if(flag)
				{
					for(ItemStack s : stacks)
					{
						EntityItem ei = new EntityItem(world, posX, posY, posZ, s);
						world.spawnEntity(ei);
					}
				}
			}
		}
		super.setDead();
	}


	@Override
	protected void entityInit()
	{
		this.dataManager.register(STATE, Optional.<IBlockState>absent());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		current_time = compound.getInteger("current");
		int xt = compound.getInteger("x_t");
		int yt = compound.getInteger("y_t");
		int zt = compound.getInteger("z_t");
		targetOutput = new BlockPos(xt, yt, zt);
		setState(Block.getStateById(compound.getInteger("st")));

		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setInteger("current", current_time);
		compound.setInteger("x_t", targetOutput.getX());
		compound.setInteger("y_t", targetOutput.getY());
		compound.setInteger("z_t", targetOutput.getZ());

		compound.setInteger("st", Block.getStateId(getState()));

		return super.writeToNBT(compound);
	}

	protected void writeEntityToNBT(NBTTagCompound compound)
	{

	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	protected void readEntityFromNBT(NBTTagCompound compound){
	}



	@Override
	public void onKillCommand()
	{
		//Don't die
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender()
	{
		return super.getBrightnessForRender();
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness()
	{
		return super.getBrightness();
	}
}