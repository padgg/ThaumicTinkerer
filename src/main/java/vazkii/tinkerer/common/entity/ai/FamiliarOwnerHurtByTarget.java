package vazkii.tinkerer.common.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;
import vazkii.tinkerer.common.entity.EntityFamiliar;

/**
 * Created by pixlepix on 4/3/14.
 */
public class FamiliarOwnerHurtByTarget extends EntityAITarget{

    //Modified version of EntityAIOwnerHurtByTarget
    EntityFamiliar theDefendingTameable;
    EntityLivingBase theOwnerAttacker;
    private int field_142051_e;
    private static final String __OBFID = "CL_00001624";

    public FamiliarOwnerHurtByTarget(EntityFamiliar par1EntityTameable)
    {
        super(par1EntityTameable, false);
        this.theDefendingTameable = par1EntityTameable;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {

        EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwner();

        if (entitylivingbase == null)
        {
            return false;
        }
        else
        {
            this.theOwnerAttacker = entitylivingbase.getAITarget();
            int i = entitylivingbase.func_142015_aE();
            return i != this.field_142051_e && this.isSuitableTarget(this.theOwnerAttacker, false);
        }

    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.theOwnerAttacker);
        EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwner();

        if (entitylivingbase != null)
        {
            this.field_142051_e = entitylivingbase.func_142015_aE();
        }

        super.startExecuting();
    }
}
