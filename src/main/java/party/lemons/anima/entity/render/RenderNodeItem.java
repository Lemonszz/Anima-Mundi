package party.lemons.anima.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import party.lemons.anima.entity.EntityNodeItem;

/**
 * Created by Sam on 20/06/2017.
 */
public class RenderNodeItem extends RenderEntityItem
{
	public static final Factory FACTORY = new Factory();

	public RenderNodeItem(RenderManager renderManagerIn, RenderItem p_i46167_2_)
	{
		super(renderManagerIn, p_i46167_2_);
	}

	public static class Factory implements IRenderFactory<EntityNodeItem>
	{

		@Override
		public Render<? super EntityNodeItem> createRenderFor(RenderManager manager)
		{
			return new RenderNodeItem(manager, Minecraft.getMinecraft().getRenderItem());
		}

	}
}
