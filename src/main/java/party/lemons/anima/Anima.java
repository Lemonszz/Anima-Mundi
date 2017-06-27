package party.lemons.anima;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import party.lemons.anima.config.ModConstants;
import party.lemons.anima.proxy.CommonProxy;

/**
 * Created by Sam on 19/06/2017.
 */
@Mod(modid = ModConstants.MODID, name = ModConstants.MODNAME, version = ModConstants.MODVERSION, updateJSON = ModConstants.UPDATEURL)
public class Anima
{
	@Mod.Instance(ModConstants.MODID)
	public static Anima Instance;

	@SidedProxy(clientSide = "party.lemons.anima.proxy.ClientProxy", serverSide = "party.lemons.anima.proxy.ServerProxy")
	public static CommonProxy Proxy;

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModConstants.MODID);

	@Mod.EventHandler
	public void OnPreInit(FMLPreInitializationEvent event)
	{
		Proxy.OnPreInit(event);
	}

	@Mod.EventHandler
	public void OnInit(FMLInitializationEvent event)
	{
		Proxy.OnInit(event);
	}

	@Mod.EventHandler
	public void OnPostInit(FMLPostInitializationEvent event)
	{
		Proxy.OnPostInit(event);
	}
}
