package io.github.chobl.common.entities;

import io.github.chobl.common.Countdown;
import io.github.chobl.common.SetBlockArea;
import io.github.chobl.common.network.PacketHandler;
import io.github.chobl.common.network.packets.RecluseCursePacket;
import io.github.chobl.core.init.EffectInit;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecluseEntity extends MonsterEntity {
    private static final Random RAND = new Random();
    private static final DataParameter<Float> TEXTURE_FRAME = EntityDataManager.createKey(RecluseEntity.class, DataSerializers.FLOAT);
    public static final ArrayList<PlayerEntity> targetList = new ArrayList<>(); //try to implement it so players who summon the recluse have the highest attack priority
    private static final int EXPLOSIONS_TO_SPAWN_COOLDOWN = 300;
    private static final int RECLUSE_SUMMON_EXPLOSION_COOLDOWN = 50;
    private static Countdown curseCountdown = new Countdown(100);
    private PlayerEntity cursedPlayer;

    public RecluseEntity(EntityType<? extends RecluseEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute bakeAttributes() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F)
                .createMutableAttribute(Attributes.MAX_HEALTH, 75.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 16.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 0.9D)
                .createMutableAttribute(Attributes.ARMOR_TOUGHNESS, 6)
                .createMutableAttribute(Attributes.ARMOR, 14);
    }

    //method will completely reset the world count and then count all recluse entities within a 50 block range of every player, tallying it up. having this method prevents
    //there being an offset in the count if all players leave or join
    //loot tables wiki https://minecraft.gamepedia.com/Bedrock_Edition_function/loot_tables/trade_tables_documentation
    public static int recountAllRecluse(World worldIn){
        int recluseWorldCount = 0;
        for (PlayerEntity player:worldIn.getPlayers()){
            AxisAlignedBB axis = new AxisAlignedBB(player.getPosX() - 48, player.getPosY() - 48, player.getPosZ() - 48, player.getPosX() + 48, player.getPosY() + 48,player.getPosZ() + 48);
            List<RecluseEntity> reclusesNearPlayer = worldIn.getEntitiesWithinAABB(RecluseEntity.class, axis);
            recluseWorldCount+=reclusesNearPlayer.size();
        }
        System.out.println("/:DEBUG: RECLUSE COUNT AFTER RECOUNT: " + recluseWorldCount);
        return recluseWorldCount;
    }

    public void allSeeingEyeAttack(PlayerEntity player, World worldIn){
        System.out.println("/:DEBUG: initiating all seeing eye attack");
        //create explosion at a random area within a couple blocks of the player
        this.cursedPlayer = player;
        int randomMultiplier = RAND.nextInt(2);
        int randomX = 10 + RAND.nextInt(6); //generates a random number between 10 and 15
        int randomZ = 10 + RAND.nextInt(6);
        int randomY = RAND.nextInt(3);
        if (randomMultiplier == 0){
            randomX*=-1;
            randomZ*=-1;
        }
        int x = (int) (player.getPosX()+randomX);
        int y = (int) (player.getPosY()+randomY);
        int z = (int) (player.getPosZ()+randomZ);
        BlockPos startPos = new BlockPos(x, y, z);
        BlockPos spawnPos = SetBlockArea.setBlockArea(startPos, worldIn, 3, 3, 3, Blocks.AIR);
        worldIn.createExplosion(this, null, null, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 6.0F, true, Explosion.Mode.DESTROY);
        BlockPos platformPos = new BlockPos(x, y-1, z); //create a 3x3 end stone platform under where the recluse will spawn
        SetBlockArea.setBlockArea(platformPos, worldIn, 3, 3, 1, Blocks.END_STONE);
        setPositionAndUpdate(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
        //tick delay between explosion creation and the rest of the attack executing
        //TO CREATE A COOLDOWN OR COUNTDOWN, TO INITIALIZE THE COUNTDOWN, REGISTER A TICKEVENT HANDLER
        MinecraftForge.EVENT_BUS.register(this);
    }

    //https://forums.minecraftforge.net/topic/67236-threadsleep/
    @SubscribeEvent
    public void tickEvent(TickEvent.ServerTickEvent event){
        if (curseCountdown.countdownIncrement()){ //once registered, the countdown should be incremented down every tick
            MinecraftForge.EVENT_BUS.unregister(this); //once countdown is finished, unregister the listener to save on resources
            curse(this.cursedPlayer); //and also call whatever you want to run after the countdown is finished
        }
    }

    public void curse(PlayerEntity player){
        player.addPotionEffect(new EffectInstance(Effects.GLOWING, 500, 0));
        player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 125, 1));
        this.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 125, 9));
        this.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 125, 4));
        PacketHandler.INSTANCE.sendToServer(new RecluseCursePacket()); //just like elder guardian cursing, we have to use packets
        //to send tasks to the client when the only world being passed in is server side. call the packetHandler to create a new
        //RecluseCursePacket instance, which will automatically execute the handle code within the class
        //worldIn.addParticle(ParticleInit.RECLUSE_EYE, player.getPosX(), player.getPosY(), player.getPosZ(), 0D, 0D, 0D);


        this.addPotionEffect(new EffectInstance(Effects.SPEED, 700, 1));
        this.addPotionEffect(new EffectInstance(Effects.STRENGTH, 500, 0));
        this.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 300, 0));
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        int applyEffectRNG = RAND.nextInt(3);
        if (applyEffectRNG == 0){ //33% chance for bleed 2 to be applied
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(EffectInit.BLEEDING, 1000, 1));
        }
        return super.attackEntityAsMob(entityIn);
        //APPLY BLEED EFFECT TO PLAYER HERE
    }

    @Override
    public void livingTick() {
        super.livingTick();
    }

    protected void registerData(){
        super.registerData(); //create a dataManager for this entity instance's data parameter using inherited super method
        this.dataManager.register(TEXTURE_FRAME, 1.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this)); //SwimGoal means that if entity gets stuck in water it will try to swim out
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 15, false, true, null));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
    }

    //echo-locate attack where the recluse stands still, releases a loud, low rumble (echolocation). gives player glowing, recluse movement speed, and after a
    //delay, create an explosion trail like the one made on summoning

    public ArrayList<PlayerEntity> getTargetList(){
        return targetList;
    }

    public void addToTargetList(PlayerEntity player){
        targetList.add(player);
    }

    public void removeFromTargetList(PlayerEntity player){
        targetList.remove(player);
    }

    //SOUNDS
//    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
//        return SoundEvents.ENTITY_SHEEP_HURT;
//    }
//
//    protected SoundEvent getDeathSound() {
//        return SoundEvents.ENTITY_SHEEP_DEATH;
//    }
//
//    protected void playStepSound(BlockPos pos, BlockState blockIn) {
//        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
//    }
}



