package vazkii.tinkerer.common.item.foci;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.lwjgl.Sys;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.Utils;

/**
 * Created by pixlepix on 4/3/14.
 */
public class ItemFocusDomestication extends ItemModFocus {


    @Override
    public boolean isUseItem() {
        return true;
    }



    @Override
    public ItemStack onFocusRightClick(ItemStack itemStack, World paramWorld, EntityPlayer entityPlayer, MovingObjectPosition paramMovingObjectPosition) {
        System.out.println(paramMovingObjectPosition);
        Entity hit=Utils.getPointedEntity(paramWorld, entityPlayer, 0, 10, .1F);
        if(hit instanceof EntityBat || hit instanceof EntityWolf || hit instanceof EntityOcelot){
            hit.setDead();
        }

        if(paramWorld.isRemote){
            entityPlayer.swingItem();
        }
        return itemStack;
    }

    @Override
    public int getFocusColor() {
        return 0xFFB233;
    }

    @Override
    public AspectList getVisCost() {
        return new AspectList().add(Aspect.ORDER, 15);
    }

    @Override
    public String getSortingHelper(ItemStack paramItemStack) {
        return "DOMESTICATION";
    }
}
