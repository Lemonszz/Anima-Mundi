package party.lemons.anima.content.armourmods;

import net.minecraft.entity.ai.attributes.AttributeModifier;

/**
 * Created by Sam on 1/07/2017.
 */
public class AttributeHolder
{
	private AttributeModifier modifier;
	private double min, max;

	public AttributeHolder(AttributeModifier modifier, double min, double max)
	{
		this.modifier = modifier;
		this.min = min;
		this.max = max;
	}

	public AttributeModifier getModifier()
	{
		return modifier;
	}

	public double getMinValue()
	{
		return min;
	}

	public  double getMaxValue()
	{
		return max;
	}
}
