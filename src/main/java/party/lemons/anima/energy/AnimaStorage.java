package party.lemons.anima.energy;


/**
 * Created by Sam on 19/06/2017.
 *
 * Anima Energy is basically forge energy, but doesn't accept other energy types
 */
public class AnimaStorage implements IAnimaStorage
{
	protected int energy;
	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;

	public AnimaStorage(int capacity)
	{
		this(capacity, capacity, capacity, 0);
	}

	public AnimaStorage(int capacity, int maxTransfer)
	{
		this(capacity, maxTransfer, maxTransfer, 0);
	}

	public AnimaStorage(int capacity, int maxReceive, int maxExtract)
	{
		this(capacity, maxReceive, maxExtract, 0);
	}

	public AnimaStorage(int capacity, int maxReceive, int maxExtract, int energy)
	{
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		this.energy = Math.max(0 , Math.min(capacity, energy));
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		if (!canReceive())
			return 0;

		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
		if (!simulate)
			energy += energyReceived;
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		if (!canExtract())
			return 0;

		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
		if (!simulate)
			energy -= energyExtracted;
		return energyExtracted;
	}

	public void setMaxExtract(int maxExtract)
	{
		this.maxExtract = maxExtract;
	}

	public int getMaxExtract()
	{
		return maxExtract;
	}

	@Override
	public int getEnergyStored()
	{
		return energy;
	}

	@Override
	public int getMaxEnergyStored()
	{
		return capacity;
	}

	@Override
	public boolean canExtract()
	{
		return this.maxExtract > 0;
	}

	@Override
	public boolean canReceive()
	{
		return this.maxReceive > 0;
	}

	public void setEnergyStored(int energyStored)
	{
		this.energy = energyStored;
	}
}
