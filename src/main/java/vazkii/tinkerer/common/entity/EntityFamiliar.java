package vazkii.tinkerer.common.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.World;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.entity.ai.FamiliarAIFollowOwner;
import vazkii.tinkerer.common.entity.ai.FamiliarOwnerHurtByTarget;

import java.util.Random;

/**
 * Created by pixlepix on 4/3/14.
 */
public class EntityFamiliar extends EntityCreature implements IEntityOwnable {

    public static final String name ="entityFamiliar";

    public EntityPlayer owner;

    private EnumFamiliarType type;

    public boolean isCat(){
        return type==EnumFamiliarType.CAT;
    }

    public boolean isDog(){
        return type==EnumFamiliarType.DOG;
    }

    public boolean isBat(){
        return type==EnumFamiliarType.BAT;
    }

    public void setCat(){
         type=EnumFamiliarType.CAT;
    }

    public void setDog(){
         type=EnumFamiliarType.DOG;
    }

    public void setBat(){
         type=EnumFamiliarType.BAT;
    }

    public EntityFamiliar(World par1World) {
        super(par1World);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0D));

        this.tasks.addTask(4, new FamiliarAIFollowOwner(this, 1.0D, 1.0F, 100.0F));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));

        //this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        //this.targetTasks.addTask(2, new FamiliarOwnerHurtByTarget(this));

    }

    public boolean isSitting(){
        return false;
    }

    @Override
    public String getOwnerName() {
        return owner.getDisplayName();
    }

    public EntityPlayer getOwner(){
        return owner;
    }

    public static void register(){
        //Registration code based on tutorial by sea_bass

        int entityID = EntityRegistry.findGlobalUniqueEntityId();
        long seed = "Familiar".hashCode();
        Random rand = new Random(seed);
        int primaryColor = rand.nextInt() * 16777215;
        int secondaryColor = rand.nextInt() * 16777215;

        EntityRegistry.registerGlobalEntityID(EntityFamiliar.class, name, entityID);
        EntityRegistry.registerModEntity(EntityFamiliar.class, name, entityID, ThaumicTinkerer.instance, 64, 1, true);
        EntityList.entityEggs.put(Integer.valueOf(entityID), new EntityList.EntityEggInfo(entityID, primaryColor, secondaryColor));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    @Override
    public void setHealth(float par1) {
        //TODO: Drain vis on damage
        return;
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    private enum EnumFamiliarType{
        DOG, CAT, BAT;
    }
}
