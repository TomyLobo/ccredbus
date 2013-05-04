package eu.tomylobo.ccredbus;

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
	version = "0.0.4",
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
		/*OCSLog.init();

		OCSLog.info( "CCRedbus version %s starting", FMLCommonHandler.instance().findContainerFor(instance).getVersion() );*/

		creativeTab = ComputerCraftAPI.getCreativeTab();

		Blocks.northBridgeBlock = new BlockNorthBridge(Config.northBridgeBlockID);

		GameRegistry.registerBlock(Blocks.northBridgeBlock, "ccredbus.northbridge");

		LanguageRegistry.addName(Blocks.northBridgeBlock, "North Bridge");

		GameRegistry.registerTileEntity(TileEntityNorthBridge.class, "ccredbus.tile.northbridge");

		GameRegistry.addRecipe(new ItemStack(Blocks.northBridgeBlock), 
				new String[] { "   ", "WWW", "S S" }, 
				'W', Block.planks,
				'S', Item.stick
		);

		proxy.init();
	}

	@Mod.PreInit
	public void preInit( FMLPreInitializationEvent evt ) {
		Configuration configFile = new Configuration(evt.getSuggestedConfigurationFile());

		Config.northBridgeBlockID = configFile.getBlock("northBridgeBlockID", Config.northBridgeBlockID, "The block ID for the North Bridge block").getInt();

		configFile.save();
	}
}