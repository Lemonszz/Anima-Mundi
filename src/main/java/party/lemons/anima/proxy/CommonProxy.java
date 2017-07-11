package party.lemons.anima.proxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.anima.Anima;
import party.lemons.anima.config.ModConstants;
import party.lemons.anima.content.item.AnimaItems;
import party.lemons.anima.content.worldgen.AnimaWorldGenerator;
import party.lemons.anima.energy.CapabilityAnima;
import party.lemons.anima.entity.EntityBlockSuck;
import party.lemons.anima.entity.EntityNodeItem;

/**
 * Created by Sam on 19/06/2017.
 */
public class CommonProxy implements IProxy
	{
		@Override
		public void OnPreInit(FMLPreInitializationEvent e)
		{
			//TODO: move these
			int id = 1;
			EntityRegistry.registerModEntity(new ResourceLocation(ModConstants.MODID + ":nodeitem"), EntityNodeItem.class, "nodeItem", id++, Anima.Instance, 64, 1, true);
			EntityRegistry.registerModEntity(new ResourceLocation(ModConstants.MODID + ":blockSuck"), EntityBlockSuck.class, "blockSuck", id++, Anima.Instance, 64, 3, true);
			GameRegistry.registerWorldGenerator(new AnimaWorldGenerator(), 0);

			CapabilityAnima.register();
		}

		@Override
		public void OnInit(FMLInitializationEvent e)
		{
			NetworkRegistry.INSTANCE.registerGuiHandler(Anima.Instance, new GuiProxy());
		}

		@Override
		public void OnPostInit(FMLPostInitializationEvent e)
		{
			AnimaItems.postInit();
		}
	}
