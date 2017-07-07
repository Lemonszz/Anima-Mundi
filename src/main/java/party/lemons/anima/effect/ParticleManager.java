package party.lemons.anima.effect;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.anima.config.ModConstants;
import net.minecraftforge.fml.relauncher.Side;


/**
 * Created by Sam on 20/06/2017.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ParticleManager
{
	@SubscribeEvent
	public static void registerParticles(TextureStitchEvent.Pre event)
	{
		stitchParticle("particle/glow_par", event.getMap());
		stitchParticle("items/sword_glow_top", event.getMap());
		stitchParticle("items/sword_glow_bottom", event.getMap());
	}

	private static void stitchParticle(ResourceLocation loc, TextureMap map)
	{
		map.registerSprite(loc);
	}

	private static void stitchParticle(String location, TextureMap map)
	{
		ResourceLocation loc = new ResourceLocation(ModConstants.MODID + ":" + location);
		stitchParticle(loc, map);
	}
}
