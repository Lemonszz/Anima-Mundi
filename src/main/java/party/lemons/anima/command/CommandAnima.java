package party.lemons.anima.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

/**
 * Created by Sam on 28/06/2017.
 */
public class CommandAnima extends CommandBase
{
	@Override
	public String getName()
	{
		return "anima";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/anima";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
	}
}
