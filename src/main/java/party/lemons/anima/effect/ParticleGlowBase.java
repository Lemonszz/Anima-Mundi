package party.lemons.anima.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import party.lemons.anima.config.ModConstants;

/**
 * Created by Sam on 27/06/2017.
 */
public abstract class ParticleGlowBase extends Particle
{
	private final ResourceLocation location = new ResourceLocation(ModConstants.MODID + ":particle/glow_par");
	private int particleRainbow;

	protected ParticleGlowBase(World world, double x, double y, double z,
							   double velocityX, double velocityY, double velocityZ)
	{
		super(world, x, y, z, velocityX, velocityY, velocityZ);

		this.particleAlpha = 0.99F;
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;

		particleRainbow = rand.nextInt(32);
		this.particleMaxAge = 20;
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
		setParticleTexture(sprite);
	}

	@Override
	public int getFXLayer()
	{
		return 1;
	}

	@Override
	public int getBrightnessForRender(float pTicks){
		int i = super.getBrightnessForRender(pTicks);
		int k = i >> 16 & 255;
		return 240 | k << 16;
	}

	@Override
	public boolean shouldDisableDepth()
	{
		return false;
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
	{
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
	}

	protected void updateColour()
	{
		double frequency = .3;
		this.particleRed = (float)Math.sin(frequency*particleRainbow + 0) * 127 + 128;
		this.particleGreen = (float)Math.sin(frequency*particleRainbow + 2) * 127 + 128;
		this.particleBlue = (float)Math.sin(frequency*particleRainbow + 4) * 127 + 128;

		if(world.getTotalWorldTime() % 4 == 0)
		{
			particleRainbow++;
		}
		if(particleRainbow > 32)
		{
			particleRainbow = 0;
		}
	}

	protected void updateMovement()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		move(motionX, motionY, motionZ);

		if (onGround)
		{
			this.setExpired();
		}

		if (prevPosY == posY && motionY > 0)
		{
			this.setExpired();
		}

		if(rand.nextInt(2) == 0)
		{
			this.particleAlpha -= 0.025F;
			if(this.particleMaxAge-- <= 0)
			{
				this.setExpired();
			}
		}
	}

	@Override
	public void onUpdate()
	{
		updateColour();
		updateMovement();
	}
}
