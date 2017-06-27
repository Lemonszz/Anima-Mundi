package party.lemons.anima.content.block.tileentity;

/**
 * Created by Sam on 26/06/2017.
 */
public enum MachineLevel
{
	LOW(500),
	MEDIUM(1000),
	HIGH(5000),
	BATTERY(50000),
	BATTERY_SMALL(13000);

	private int maxPower;
	MachineLevel(int maxPower)
	{
		this.maxPower = maxPower;
	}

	public int getMaxPower()
	{
		return maxPower;
	}
}
