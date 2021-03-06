package vazkii.tinkerer.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thaumcraft.api.aspects.Aspect;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.core.helper.NumericAspectHelper;

import java.util.List;

public class ItemMobAspect extends Item {

	//Real value is 16
	//Padding room inclued
	//To prevent corruption
	public static final int aspectCount = 20;

	public ItemMobAspect() {
		super();
		setMaxStackSize(16);
	}

	@Override
	public boolean getHasSubtypes() {
		return true;
	}

	public static IIcon[] aspectIcons = new IIcon[aspectCount];

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);

		for (NumericAspectHelper aspect : NumericAspectHelper.values) {
			aspectIcons[aspect.num] = par1IconRegister.registerIcon(LibResources.PREFIX_MOD + aspect.getAspect().getName().toLowerCase());
		}
	}

	@Override
	public void getSubItems(Item par1Item, CreativeTabs par2CreativeTabs, List par3List) {
		for (NumericAspectHelper aspect : NumericAspectHelper.values) {
			par3List.add(getStackFromAspect(aspect.getAspect()));
		}
	}

	@Override
	public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
		return aspectIcons[par1 % aspectCount];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (isCondensed(stack)) {
			return super.getUnlocalizedName(stack) + ".condensed";
		}
		if (isInfused(stack)) {
			return super.getUnlocalizedName(stack) + ".infused";
		}
		return super.getUnlocalizedName(stack);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
		list.add(getAspect(itemStack).getName());
	}

	public Aspect getAspect(ItemStack item) {
		if (item == null) {
			return null;
		}
		return NumericAspectHelper.getAspect(item.getItemDamage() % aspectCount);
	}

	public static ItemStack getStackFromAspect(Aspect a) {
		ItemStack result = new ItemStack(ModItems.mobAspect);
		result.setItemDamage(NumericAspectHelper.getNumber(a));
		return result;
	}

	public static boolean isCondensed(ItemStack item) {
		return item.getItemDamage() >= aspectCount && item.getItemDamage() < aspectCount * 2;
	}

	public static boolean isInfused(ItemStack item) {
		return item.getItemDamage() >= aspectCount * 2;
	}

}
