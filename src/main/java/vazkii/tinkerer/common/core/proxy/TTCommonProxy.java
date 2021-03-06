/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [4 Sep 2013, 16:29:41 (GMT)]
 */
package vazkii.tinkerer.common.core.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import li.cil.oc.api.Driver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.block.tile.peripheral.OpenComputers.*;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.core.handler.kami.DimensionalShardDropHandler;
import vazkii.tinkerer.common.core.handler.kami.KamiArmorHandler;
import vazkii.tinkerer.common.core.handler.kami.KamiDimensionHandler;
import vazkii.tinkerer.common.core.handler.kami.SoulHeartHandler;
import vazkii.tinkerer.common.core.helper.NumericAspectHelper;
import vazkii.tinkerer.common.enchantment.ModEnchantments;
import vazkii.tinkerer.common.enchantment.core.EnchantmentManager;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.network.GuiHandler;
import vazkii.tinkerer.common.network.PlayerTracker;
import vazkii.tinkerer.common.network.packet.PacketEnchanterAddEnchant;
import vazkii.tinkerer.common.network.packet.PacketEnchanterStartWorking;
import vazkii.tinkerer.common.network.packet.PacketMobMagnetButton;
import vazkii.tinkerer.common.network.packet.PacketTabletButton;
import vazkii.tinkerer.common.network.packet.kami.PacketSoulHearts;
import vazkii.tinkerer.common.network.packet.kami.PacketToggleArmor;
import vazkii.tinkerer.common.network.packet.kami.PacketWarpGateButton;
import vazkii.tinkerer.common.network.packet.kami.PacketWarpGateTeleport;
import vazkii.tinkerer.common.potion.ModPotions;
import vazkii.tinkerer.common.research.ModRecipes;
import vazkii.tinkerer.common.research.ModResearch;

public class TTCommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());

		ModBlocks.initBlocks();
		ModItems.initItems();
		NumericAspectHelper.init();
		initCCPeripherals();
	}

	public void init(FMLInitializationEvent event) {
		ModEnchantments.initEnchantments();
		EnchantmentManager.initEnchantmentData();
		ModPotions.initPotions();
		ModBlocks.initTileEntities();
		NetworkRegistry.INSTANCE.registerGuiHandler(ThaumicTinkerer.instance, new GuiHandler());
		registerPackets();
		FMLCommonHandler.instance().bus().register(new PlayerTracker());

		if (ConfigHandler.enableKami) {
			MinecraftForge.EVENT_BUS.register(new DimensionalShardDropHandler());
			MinecraftForge.EVENT_BUS.register(new KamiDimensionHandler());
			MinecraftForge.EVENT_BUS.register(new SoulHeartHandler());
		}
		if (Loader.isModLoaded("OpenComputers")) {
			initOpenCDrivers();
		}
	}

	protected void registerPackets() {
		ThaumicTinkerer.netHandler.registerMessage(PacketSoulHearts.class, PacketSoulHearts.class, 142 + 0, Side.CLIENT);
		ThaumicTinkerer.netHandler.registerMessage(PacketToggleArmor.class, PacketToggleArmor.class, 142 + 1, Side.CLIENT);
		ThaumicTinkerer.netHandler.registerMessage(PacketToggleArmor.class, PacketToggleArmor.class, 142 + 2, Side.SERVER);
		ThaumicTinkerer.netHandler.registerMessage(PacketWarpGateButton.class, PacketWarpGateButton.class, 142 + 3, Side.SERVER);
		ThaumicTinkerer.netHandler.registerMessage(PacketWarpGateTeleport.class, PacketWarpGateTeleport.class, 142 + 4, Side.SERVER);
		ThaumicTinkerer.netHandler.registerMessage(PacketEnchanterAddEnchant.class, PacketEnchanterAddEnchant.class, 142 + 5, Side.SERVER);
		ThaumicTinkerer.netHandler.registerMessage(PacketEnchanterStartWorking.class, PacketEnchanterStartWorking.class, 142 + 6, Side.SERVER);
		ThaumicTinkerer.netHandler.registerMessage(PacketMobMagnetButton.class, PacketMobMagnetButton.class, 142 + 7, Side.SERVER);
		ThaumicTinkerer.netHandler.registerMessage(PacketTabletButton.class, PacketTabletButton.class, 142 + 8, Side.SERVER);
	}

	public void postInit(FMLPostInitializationEvent event) {
		ModRecipes.initRecipes();
		ModResearch.initResearch();
	}

	protected void initCCPeripherals() {
		/*IPeripheralHandler handler = new PeripheralHandler();

		Class[] peripheralClasses = new Class[] {
				TileAlembic.class, TileCentrifuge.class, TileCrucible.class, TileFunnel.class,
				TileInfusionMatrix.class, TileJarFillable.class, TileJarNode.class, TileNode.class,
				TileRepairer.class, TileTubeFilter.class, TileTransvectorInterface.class, TileWandPedestal.class,
				TileDeconstructionTable.class, TileJarBrain.class, TileSensor.class, TileArcaneBore.class,IEssentiaTransport.class
		};
        // DUMMY CHANGE
        for(Class clazz : peripheralClasses)
			ComputerCraftAPI.registerExternalPeripheral(clazz, handler);
			
		TurtleAPI.registerUpgrade(new FumeTool());*/
	}

	@Optional.Method(modid = "OpenComputers")
	public void initOpenCDrivers() {
		Driver.add(new DriverIAspectContainer());
		Driver.add(new DriverArcaneEar());
		Driver.add(new DriverBrainInAJar());
		Driver.add(new DriverDeconstructor());
		Driver.add(new DriverEssentiaTransport());
		Driver.add(new DriverArcaneBore());

	}

	public boolean isClient() {
		return false;
	}

	public boolean armorStatus(EntityPlayer player) {
		return KamiArmorHandler.getArmorStatus(player);
	}

	public void setArmor(EntityPlayer player, boolean status) {
		KamiArmorHandler.setArmorStatus(player, status);
	}

	public EntityPlayer getClientPlayer() {
		return null;
	}

	public void shadowSparkle(World world, float x, float y, float z, int size) {
		// NO-OP
	}
}
