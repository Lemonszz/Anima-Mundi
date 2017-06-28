package party.lemons.anima.energy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import party.lemons.anima.Anima;
import party.lemons.anima.config.ModConstants;
import party.lemons.anima.network.PacketSendWorldEnergy;

/**
 * Created by Sam on 27/06/2017.
 */
public class WorldEnergy extends WorldSavedData
{
	private static final String NAME = ModConstants.MODID + ":anima";
	private static final int ANIMA_START = 	5000000; //5 million anima
	private static final int ANIMA_MAX = 	5000000; //5 million anima

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

	public int getAnima()
	{
		return 5000000;//anima;
	}

	public void setAnima(int anima, World world)
	{
		this.anima = anima;
		this.markDirty();

		if(!world.isRemote)
		{
			syncWithAll(world);
		}
	}

	public static int putAnima(World world, int amount)
	{
		WorldEnergy energy = get(world);
		int animaGiven = Math.min(ANIMA_MAX - energy.getAnima(), amount);
		energy.setAnima(energy.getAnima() + animaGiven, world);

		return amount - animaGiven;
	}

	public static int drainEnergy(World world, int amount)
	{
		WorldEnergy energy = get(world);
		int animaTaken = Math.min(energy.getAnima(), amount);
		energy.setAnima(energy.getAnima() - animaTaken, world);

		return animaTaken;
	}

	public static boolean canHasAnyEnergy(World world)
	{
		WorldEnergy energy = get(world);
		return energy.getAnima() > 0;
	}

	public static void syncWith(EntityPlayer player)
	{
		WorldEnergy energy = get(player.world);
		if(!player.world.isRemote)
		{
			Anima.NETWORK.sendTo(new PacketSendWorldEnergy(energy.getAnima()), (EntityPlayerMP)player);
		}
	}

	public static void syncWithAll(World world)
	{
		WorldEnergy energy = get(world);
		if(!world.isRemote)
		{
			Anima.NETWORK.sendToAll(new PacketSendWorldEnergy(energy.getAnima()));
		}
	}


}
