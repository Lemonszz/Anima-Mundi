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
 * Created by Sam on 20/06/2017.
 */
public class ParticleGlow extends Particle
{
	private final ResourceLocation location = new ResourceLocation(ModConstants.MODID + ":particle/glow_par");
	int particleRainbow;



	public ParticleGlow(World world, double x, double y, double z,
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
	//	GlStateManager.pushMatrix();
	//	GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
		//GlStateManager.disableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);


		//GlStateManager.popMatrix();
	}

	@Override
	public void onUpdate()
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

		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		move(motionX, motionY, motionZ);  // simple linear motion.  You can change speed by changing motionX, motionY,
		// motionZ every tick.  For example - you can make the particle accelerate downwards due to gravity by
		// final double GRAVITY_ACCELERATION_PER_TICK = -0.02;
		// motionY += GRAVITY_ACCELERATION_PER_TICK;

		// collision with a block makes the ball disappear.  But does not collide with entities
		if (onGround) {  // isCollided is only true if the particle collides while it is moving downwards...
			this.setExpired();
		}

		if (prevPosY == posY && motionY > 0) {  // detect a collision while moving upwards (can't move up at all)
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

}
