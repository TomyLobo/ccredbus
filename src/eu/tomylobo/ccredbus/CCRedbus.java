package eu.tomylobo.ccredbus;

import java.util.logging.Logger;

import com.eloraam.redpower.RedPowerBase;

import eu.tomylobo.ccredbus.common.BlockNorthBridge;
import eu.tomylobo.ccredbus.common.CommonProxy;
import eu.tomylobo.ccredbus.common.TileEntityNorthBridge;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dan200.computer.api.ComputerCraftAPI;

@Mod(
	modid = "CCRedbus",
	name = "CCRedbus",
	version = "0.0.6",
	dependencies = "required-after:ComputerCraft;after:CCTurtle;required-after:RedPowerMachine"
)
@NetworkMod(
	clientSideRequired = true,
	serverSideRequired = false
)
public class CCRedbus {
	public static class Blocks {
		public static BlockNorthBridge northBridgeBlock;
	}

	public static class Config {
		public static int northBridgeBlockID = 1320;
	}

	public static final Logger logger = Logger.getLogger("Minecraft");

	@Mod.Instance("CCRedbus")
	public static CCRedbus instance;

	@SidedProxy(
		clientSide = "eu.tomylobo.ccredbus.client.ClientProxy",
		serverSide = "eu.tomylobo.ccredbus.common.CommonProxy"
	)
	public static CommonProxy proxy;

	public static CreativeTabs creativeTab;

	@Mod.Init
	public void init(FMLInitializationEvent evt) {
		creativeTab = ComputerCraftAPI.getCreativeTab();

		Blocks.northBridgeBlock = new BlockNorthBridge(Config.northBridgeBlockID);

		GameRegistry.registerBlock(Blocks.northBridgeBlock, "ccredbus.northbridge");

		LanguageRegistry.addName(Blocks.northBridgeBlock, "North Bridge");

		GameRegistry.registerTileEntity(TileEntityNorthBridge.class, "ccredbus.tile.northbridge");

		GameRegistry.addRecipe(new ItemStack(Blocks.northBridgeBlock), 
				new String[] { "SSS", "SRS", "SCS" }, 
				'S', Block.stone,
				'R', Item.redstone,
				'C', new ItemStack((Block)(Object)RedPowerBase.blockMicro, 1, 12 << 8)
		);

		proxy.init();
	}

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent evt) {
		logger.info("CCRedbus initializing...");
		Configuration configFile = new Configuration(evt.getSuggestedConfigurationFile());

		Config.northBridgeBlockID = configFile.getBlock("northBridgeBlockID", Config.northBridgeBlockID, "The block ID for the North Bridge block").getInt();

		configFile.save();
	}
}
