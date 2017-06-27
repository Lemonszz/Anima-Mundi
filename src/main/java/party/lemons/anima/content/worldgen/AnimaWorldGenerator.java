package party.lemons.anima.content.worldgen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import party.lemons.anima.content.block.AnimaBlocks;

import java.util.Random;

/**
 * Created by Sam on 23/06/2017.
 */
public class AnimaWorldGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		switch(world.provider.getDimension())
		{
			case -1:

				break;

			case 0:
				genSurface(world, random, chunkX, chunkZ, chunkGenerator, chunkProvider);
				break;

			case 1:

				break;
		}
	}

	private void genSurface(World world, Random random, int chunkX, int chunkZ, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		addOreSpawn(AnimaBlocks.ANIMA_ORE.getDefaultState(), world, random, chunkX, chunkZ, 16, 16, 6, 40, 20, 100);
	}

	private void addOreSpawn(IBlockState block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chance, int minY, int maxY){
		int diffMinMaxY = maxY - minY;
		for(int x = 0; x < chance; x++){
			int posX = (blockXPos * 16)+ random.nextInt(maxX);
			int posY = minY + random.nextInt(diffMinMaxY);
			int posZ = (blockZPos * 16) + random.nextInt(maxZ);
			(new WorldGenMinable(block, maxVeinSize)).generate(world, random, new BlockPos(posX, posY, posZ));
		}
	}
}
