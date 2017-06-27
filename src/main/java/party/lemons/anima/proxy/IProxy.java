package party.lemons.anima.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Sam on 19/06/2017.
 */
public interface IProxy
{
	void OnPreInit(FMLPreInitializationEvent e);
	void OnInit(FMLInitializationEvent e);
	void OnPostInit(FMLPostInitializationEvent e);
}
