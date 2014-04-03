package vazkii.tinkerer.common.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.World;
import vazkii.tinkerer.common.ThaumicTinkerer;

import java.util.Random;

/**
 * Created by pixlepix on 4/3/14.
 */
public class EntityFamiliar extends EntityCreature {

    public static final String name ="entityFamiliar";

    public EntityPlayer owner;

    public EntityFamiliar(World par1World) {
        super(par1World);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
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
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
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
}
