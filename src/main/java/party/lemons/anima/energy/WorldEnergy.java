package party.lemons.anima.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import party.lemons.anima.config.ModConstants;

/**
 * Created by Sam on 27/06/2017.
 */
public class WorldEnergy extends WorldSavedData
{
	private static final String NAME = ModConstants.MODID + ":anima";
	private static final int ANIMA_START = 5000000; //5 million anima

	private int anima = ANIMA_START;


	public WorldEnergy()
	{
		super(NAME);
	}

	public WorldEnergy(String name)
	{
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		anima = nbt.getInteger("globalanima");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags.setInteger("globalanima", anima);

		return tags;
	}

	public static WorldEnergy get(World world)
	{
		MapStorage storage = world.getMapStorage();
		WorldEnergy we = (WorldEnergy) storage.getOrLoadData(WorldEnergy.class, NAME);

		if(we == null)
		{
			we = new WorldEnergy();
			storage.setData(NAME, we);
		}

		return we;
	}
}
