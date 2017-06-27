package party.lemons.anima.proxy;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import party.lemons.anima.entity.EntityBlockSuck;
import party.lemons.anima.entity.EntityNodeItem;
import party.lemons.anima.entity.render.RenderBlockSuck;
import party.lemons.anima.entity.render.RenderNodeItem;

/**
 * Created by Sam on 19/06/2017.
 */
public class ClientProxy extends CommonProxy
{
	@Override
	public void OnPreInit(FMLPreInitializationEvent e)
	{
		super.OnPreInit(e);

		//TODO: Move this
		RenderingRegistry.registerEntityRenderingHandler(EntityNodeItem.class, RenderNodeItem.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBlockSuck.class, RenderBlockSuck.FACTORY);
	}

	@Override
	public void OnInit(FMLInitializationEvent e)
	{
		super.OnInit(e);
	}

	@Override
	public void OnPostInit(FMLPostInitializationEvent e)
	{
		super.OnPostInit(e);
	}
}
