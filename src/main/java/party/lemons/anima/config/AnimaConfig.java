package party.lemons.anima.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Sam on 26/06/2017.
 */
@Config(modid = ModConstants.MODID)
@Config.LangKey("anima.config.title")
public class AnimaConfig
{
	//Particle Config
	@Config.Comment("Show particles between links")
	public static boolean showLinkParticles = true;

	@Config.Comment("Time between link particles")
	public static int linkParticleInterval = 5;

	//Node Item Config
	@Config.Comment("Allow the player to pickup node items while in transport")
	public static boolean allowPlayerPickup = true;

	@Config.Comment("[UNUSED] Allow entities to pickup node items while in transport")
	public static boolean allowEntityPickup = true;

	//Gui Config
	@Config.Comment("The corner to draw the analyser GUI in, 0 = Top Left, 1 = Top Right, 2 = Bottom Left, 3 = Bottom Right")
	public static int guiCorner = 0;

	public static void loadConfig(Configuration config)
	{
	/*	config.load();

		showLinkParticles = config.getBoolean(
		"","particle", true, ""
		);

		linkParticleInterval = config.getInt(
				"Link Particle Interval", "particle", 5, 1, 10000, "Show the particles between links"
		);

		allowPlayerPickup = config.getBoolean(
			"Player can pick up node items", "item", true, "Allow the player to pickup items while they are in transit"
		);

		allowEntityPickup = config.getBoolean(
				"Entities can pick up node items", "item", true, "[Unused] Allow entities to pickup items while they are in transit"
		);

		guiCorner = config.getInt(
				"Analyser GUI corner", "gui", 0, 0, 4, ""
		);

		config.save();*/
	}

	@EventBusSubscriber(modid = ModConstants.MODID)
	private static class EventHandler
	{
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
		{
			if (event.getModID().equals(ModConstants.MODID)) {
				ConfigManager.sync(ModConstants.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
