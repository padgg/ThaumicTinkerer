package vazkii.tinkerer.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import vazkii.tinkerer.common.entity.EntityFamiliar;

/**
 * Created by pixlepix on 4/4/14.
 */
public class RenderFamiliar extends RenderLiving {
    public RenderFamiliar(ModelBase par1ModelBase, float par2) {
        super(par1ModelBase, par2);
    }

    @Override
    public void doRender(EntityLiving e, double par2, double par4, double par6, float par8, float par9) {
        if(e instanceof EntityFamiliar){
            EntityFamiliar f= (EntityFamiliar) e;
            Entity clone=new EntityWolf(e.worldObj);
            if(f.isBat()){
                clone=new EntityBat(e.worldObj);
            }
            if(f.isCat()){
                clone=new EntityOcelot(e.worldObj);
            }
            if(f.isDog()){
                clone=new EntityWolf(e.worldObj);
            }
            clone.setPositionAndRotation(((EntityFamiliar) e).posX, ((EntityFamiliar) e).posY, ((EntityFamiliar) e).posZ, ((EntityFamiliar) e).rotationYaw, ((EntityFamiliar) e).rotationPitch);
            //Copied code from RenderModDisplay
            Render renderer = RenderManager.instance.getEntityRenderObject(clone);
            if (renderer != null && renderer.getFontRendererFromRenderManager() != null) {
                GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                //if (renderWithLighting) RenderUtils.enableLightmap();
                renderer.doRender(clone, par2, par4, par6, par8, par9);
                GL11.glPopAttrib();
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity e) {

        return null;
    }
}
