package party.lemons.anima.energy;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.anima.config.ModConstants;
import party.lemons.anima.content.item.ItemAnimaCharged;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Sam on 29/06/2017.
 */
@Mod.EventBusSubscriber
public class AnimaEvents
{
	@SubscribeEvent
	public static void attachItemStacks(AttachCapabilitiesEvent<ItemStack> event)
	{
	}
}
