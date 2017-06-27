package party.lemons.anima.entity.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;
import party.lemons.anima.entity.EntityBlockSuck;

/**
 * Created by Sam on 21/06/2017.
 */
public class RenderBlockSuck extends RenderEntity
{
	public static final RenderBlockSuckFactory FACTORY = new RenderBlockSuckFactory();

	public RenderBlockSuck(RenderManager renderManagerIn)
	{
		super(renderManagerIn);
	}

	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		EntityBlockSuck sucker = (EntityBlockSuck) entity;
		IBlockState renderState = sucker.getState();

		BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
		GlStateManager.pushMatrix();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);

		GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.5F, -0.5F, 0.5F);

		GL11.glTranslatef(0.5F, 0.5F, -0.5F);

		float scale = (float) (1F - (sucker.getTime() * 0.0205F) + ((Math.sin(sucker.getTime())))/ 20);

		GlStateManager.scale(scale,scale,scale);
		GlStateManager.rotate((float) ((sucker.getTime() * 3) + (Math.sin(sucker.getTime()) / 20)), 0, -1, 0);
		GlStateManager.rotate((float) (sucker.getTime() * 6 + (Math.sin(sucker.getTime()) / 2)), 1, 0, 0);
		GlStateManager.rotate((float) (sucker.getTime() + (Math.sin(sucker.getTime()))), 0, 0, 1);

		GL11.glTranslatef(-0.5F, -0.5F, +0.5F);

		if(renderState != null)
		{
			this.bindEntityTexture(entity);
			blockrendererdispatcher.renderBlockBrightness(renderState, 1);
		}
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GL11.glDisable(GL11.GL_BLEND);

		if (((EntityBlockSuck) entity).getTime() / 2 % 2 == 0)
		{
			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
			GlStateManager.color(1.0F, 1.0F, 1.0F, partialTicks);
			GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);

			if(renderState != null)
				blockrendererdispatcher.renderBlockBrightness(renderState, 1.0F);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
		}
		GlStateManager.popMatrix();
	}

	public static class RenderBlockSuckFactory implements IRenderFactory<Entity>
	{
		@Override
		public Render<? super Entity> createRenderFor(RenderManager manager) {
			return new RenderBlockSuck(manager);
		}

	}

	protected ResourceLocation getEntityTexture(EntityTNTPrimed entity)
	{
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}