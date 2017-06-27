package party.lemons.anima.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.anima.config.ModConstants;

/**
 * Created by Sam on 20/06/2017.
 */
public class ParticleLinkColoured extends Particle
{
	private final ResourceLocation location = new ResourceLocation(ModConstants.MODID + ":particle/glow_par");
	int particleRainbow;
	private BlockPos target;


	public ParticleLinkColoured(World world, double x, double y, double z,
						double velocityX, double velocityY, double velocityZ, BlockPos target, float r, float g, float b)
	{
		super(world, x, y, z, velocityX, velocityY, velocityZ);

		this.particleAlpha = 0.99F;
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;

		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;

		this.target = target;
		this.canCollide = false;
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
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
		//	GlStateManager.disableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

		GlStateManager.popMatrix();
	}

	@Override
	public void onUpdate()
	{

		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		move(motionX, motionY, motionZ);  // simple linear motion.  You can change speed by changing motionX, motionY,
		// motionZ every tick.  For example - you can make the particle accelerate downwards due to gravity by
		// final double GRAVITY_ACCELERATION_PER_TICK = -0.02;
		// motionY += GRAVITY_ACCELERATION_PER_TICK;

		// collision with a block makes the ball disappear.  But does not collide with entities
		BlockPos currentPos = new BlockPos(this.posX, this.posY, this.posZ);

		//TODO: clean
		if (currentPos.getY() == target.getY() && currentPos.getX() == target.getX() && currentPos.getZ() == target.getZ()) {  // isCollided is only true if the particle collides while it is moving downwards...
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
