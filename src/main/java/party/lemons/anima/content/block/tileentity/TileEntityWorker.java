package party.lemons.anima.content.block.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import party.lemons.anima.energy.AnimaStorage;
import party.lemons.anima.energy.CapabilityAnima;

import javax.annotation.Nullable;

/**
 * Created by Sam on 19/06/2017.
 */
public abstract class TileEntityWorker extends TileEntity implements ITickable
{
	protected int workTime, workMax, workCost;
	protected AnimaStorage animaStorage;
	protected MachineLevel machineLevel;

	public TileEntityWorker(int workMax, int workCost, int maxTransfer, MachineLevel machineLevel)
	{
		workTime = 0;
		this.workCost = workCost;
		this.workMax = workMax;
		this.machineLevel = machineLevel;
		animaStorage = new AnimaStorage(machineLevel.getMaxPower(), machineLevel.getMaxPower(), maxTransfer);
	}

	public boolean canExtractEnergy()
	{
		return animaStorage.extractEnergy(workCost, true) == workCost;
	}

	public int extractEnergy()
	{
		return animaStorage.extractEnergy(workCost, false);
	}

	@Override
	public void update()
	{
		if(canWork())
		{
			workTime++;

			if(workTime >= workMax)
			{
				work();
				workTime = 0;
			}
		}
		this.markDirty();
	}


	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		workTime = compound.getInteger("worktime");
		animaStorage.setEnergyStored(compound.getInteger("power"));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setInteger("worktime", workTime);
		compound.setInteger("power", animaStorage.getEnergyStored());
		return super.writeToNBT(compound);
	}

	@Override
	public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, @Nullable net.minecraft.util.EnumFacing facing)
	{
		if(capability == CapabilityAnima.ANIMA)
		{
			return true;
		}

		return super.hasCapability(capability, facing);
	}

	@Override
	@Nullable
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing)
	{
		if(capability == CapabilityAnima.ANIMA)
		{
			return (T)animaStorage;
		}

		return super.getCapability(capability, facing);
	}
	public abstract boolean canWork();
	public abstract void work();
}
