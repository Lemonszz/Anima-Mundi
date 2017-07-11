package party.lemons.anima.energy;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

/**
 * Created by Sam on 19/06/2017.
 *
 * Anima Energy is basically forge energy, but doesn't accept other energy types
 */
public class CapabilityAnima
{
	@CapabilityInject(IAnimaStorage.class)
	public static Capability<IAnimaStorage> ANIMA = null;

	public static void register()
	{
		CapabilityManager.INSTANCE.register(IAnimaStorage.class, new AnimaNBTStorage(), new AnimaFactory());
	}

	private static class AnimaNBTStorage implements Capability.IStorage<IAnimaStorage>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<IAnimaStorage> capability, IAnimaStorage instance, EnumFacing side)
		{
			return new NBTTagInt(instance.getEnergyStored());
		}

		@Override
		public void readNBT(Capability<IAnimaStorage> capability, IAnimaStorage instance, EnumFacing side, NBTBase nbt)
		{
			if(!(instance instanceof AnimaStorage))
				throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
			((AnimaStorage) instance).energy = ((NBTTagInt) nbt).getInt();
		}
	}

	private static class AnimaFactory implements Callable<IAnimaStorage>
	{
		@Override
		public IAnimaStorage call() throws Exception
		{
			return new AnimaStorage(1000);
		}
	}
}
