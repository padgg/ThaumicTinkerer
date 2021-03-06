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
 * File Created @ [9 Sep 2013, 16:29:28 (GMT)]
 */
package vazkii.tinkerer.common.network;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.client.gui.GuiAnimationTablet;
import vazkii.tinkerer.client.gui.GuiAspectAnalyzer;
import vazkii.tinkerer.client.gui.GuiEnchanting;
import vazkii.tinkerer.client.gui.GuiMobMagnet;
import vazkii.tinkerer.client.gui.kami.GuiIchorPouch;
import vazkii.tinkerer.client.gui.kami.GuiWarpGate;
import vazkii.tinkerer.client.gui.kami.GuiWarpGateDestinations;
import vazkii.tinkerer.common.block.tile.TileAspectAnalyzer;
import vazkii.tinkerer.common.block.tile.TileEnchanter;
import vazkii.tinkerer.common.block.tile.TileMobMagnet;
import vazkii.tinkerer.common.block.tile.container.ContainerAnimationTablet;
import vazkii.tinkerer.common.block.tile.container.ContainerAspectAnalyzer;
import vazkii.tinkerer.common.block.tile.container.ContainerEnchanter;
import vazkii.tinkerer.common.block.tile.container.ContainerMobMagnet;
import vazkii.tinkerer.common.block.tile.container.kami.ContainerIchorPouch;
import vazkii.tinkerer.common.block.tile.container.kami.ContainerWarpGate;
import vazkii.tinkerer.common.block.tile.kami.TileWarpGate;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;
import vazkii.tinkerer.common.lib.LibGuiIDs;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch (ID) {
			case LibGuiIDs.GUI_ID_TABLET:
				return new ContainerAnimationTablet((TileAnimationTablet) tile, player.inventory);

			case LibGuiIDs.GUI_ID_MOB_MAGNET:
				return new ContainerMobMagnet((TileMobMagnet) tile, player.inventory);

			case LibGuiIDs.GUI_ID_ENCHANTER:
				return new ContainerEnchanter((TileEnchanter) tile, player.inventory);

			case LibGuiIDs.GUI_ID_ASPECT_ANALYZER:
				return new ContainerAspectAnalyzer((TileAspectAnalyzer) tile, player.inventory);

			case LibGuiIDs.GUI_ID_ICHOR_POUCH:
				return new ContainerIchorPouch(player);

			case LibGuiIDs.GUI_ID_WARP_GATE:
				return new ContainerWarpGate((TileWarpGate) tile, player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch (ID) {
			case LibGuiIDs.GUI_ID_TABLET:
				return new GuiAnimationTablet((TileAnimationTablet) tile, player.inventory);

			case LibGuiIDs.GUI_ID_MOB_MAGNET:
				return new GuiMobMagnet((TileMobMagnet) tile, player.inventory);

			case LibGuiIDs.GUI_ID_ENCHANTER:
				return new GuiEnchanting((TileEnchanter) tile, player.inventory);

			case LibGuiIDs.GUI_ID_ASPECT_ANALYZER:
				return new GuiAspectAnalyzer((TileAspectAnalyzer) tile, player.inventory);

			case LibGuiIDs.GUI_ID_ICHOR_POUCH:
				return new GuiIchorPouch(new ContainerIchorPouch(player));

			case LibGuiIDs.GUI_ID_WARP_GATE:
				return new GuiWarpGate((TileWarpGate) tile, player.inventory);

			case LibGuiIDs.GUI_ID_WARP_GATE_DESTINATIONS:
				return new GuiWarpGateDestinations((TileWarpGate) tile);
		}
		return null;
	}

}
