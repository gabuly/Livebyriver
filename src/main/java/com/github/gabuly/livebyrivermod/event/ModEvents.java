package com.github.gabuly.livebyrivermod.event;

import com.github.gabuly.livebyrivermod.Goals.FollowLeaderGoal;
import com.github.gabuly.livebyrivermod.Goals.SpawnFollowerOnceGoal;
import com.github.gabuly.livebyrivermod.leaders.LeaderSheep;
import com.github.gabuly.livebyrivermod.livebyrivermod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.github.gabuly.livebyrivermod.RegisterEntity.LEADERSHEEP;
import static com.mojang.text2speech.Narrator.LOGGER;

@Mod.EventBusSubscriber(modid = livebyrivermod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static final String LEADER_TAG = "leader_entity";
    private static MinecraftServer server;
    private static final Random RANDOM = new Random();
    private static ServerLevel overworld;




    //嵌入喝水+追随 AI
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        // Check if the entity is an instance of any of the specified animal classes
        if (entity instanceof Animal){
            if(entity instanceof Sheep && !(entity instanceof LeaderSheep)){
//        if (entity instanceof Sheep || entity instanceof Cow || entity instanceof Pig ||
//                entity instanceof Chicken || entity instanceof Horse || entity instanceof Donkey ||
//                entity instanceof Mule || entity instanceof Llama || entity instanceof Wolf ||
//                entity instanceof Ocelot || entity instanceof Cat ||
//                entity instanceof Fox || entity instanceof Panda) {
            PathfinderMob animal = (PathfinderMob) entity;
             animal.goalSelector.addGoal(4, new FollowLeaderGoal(animal));
            }

            //生成追随者
//            if(entity instanceof LeaderSheep){
//                Level level =event.getLevel();
//                BlockPos leaderPos = entity.blockPosition();
//                //生成追随
//                for (int i = 0; i < 10; i++) {
//                    EntityType entityType = EntityType.SHEEP;
//                    LOGGER.info(" Time to spawn follower" );//额外生成数量;
//                    Entity followerEntity = entityType.create(level);
//                    // 随机生成范围,准备检测
//                    int randomX = RANDOM.nextInt(14) - 7; // Random value between -15 and 15 for X
//                    int randomZ = RANDOM.nextInt(14) - 7;
//                    int X =leaderPos.getX();
//                    int Y =leaderPos.getY();
//                    int Z =leaderPos.getZ();
//                    BlockPos spawnPos = new BlockPos( X+ randomX, Y, Z + randomZ);
//                    BlockPos groundPos = spawnPos.below();
//
//                    LOGGER.info("spawn =" +spawnPos);
//                    LOGGER.info("ground =" +groundPos);
//                    BlockState spawnPosState = level.getBlockState(spawnPos);
//                    BlockState groundPosState = level.getBlockState(groundPos);
//                    LOGGER.info("before spawnLead" );
//                    //脚底坐标是空气的话，下降到不是空气为止
//                    while(groundPosState.isAir()&&groundPos.getY()>-120){
//                        LOGGER.info("Air ground !!!!"+groundPosState+"----- "+ groundPos);
//                        groundPos = groundPos.below(); //脚底坐标Y--
//                        spawnPos= groundPos.above();   //同步生成坐标
//                        groundPosState= level.getBlockState(groundPos);   //同步脚底坐标
//                    }
//
//                    //脚底是实心的话，检测生成坐标是不是实心，否则上升到空气为止
//                    while(!spawnPosState.isAir()&&spawnPos.getY()<256){
//                        LOGGER.info("stuck"+  spawnPosState+"==== "+   spawnPos);
//                        spawnPos= spawnPos.above();     //生成坐标Y++
//                        spawnPosState= level.getBlockState(spawnPos);   //同步生成坐标
//                    }
//                    followerEntity.setPos(spawnPos.getX(),spawnPos.getY(),spawnPos.getZ());
//                    level.addFreshEntity(followerEntity);
//                }
//            }
        }
    }



    //放大头羊
    @SubscribeEvent
    public static void onEntityRender(RenderLivingEvent.Pre event) {
        EntityType<?> entityType = event.getEntity().getType();
        if (entityType == LEADERSHEEP.get()
//                entityType == EntityType.CHICKEN ||
//                entityType == EntityType.WOLF ||
//                entityType == EntityType.SHEEP ||
//                entityType == EntityType.PIG ||
//                entityType == EntityType.HORSE
               ) {

//            if(entityNBT.contains("isLeader") || entityNBT.getBoolean("isLeader")){
            PoseStack poseStack = event.getPoseStack();
//       poseStack.pushPose();
            poseStack.scale(1.3F, 1.3F, 1.3F);

        }
    }



    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        server = event.getServer();
        overworld = server.getLevel(Level.OVERWORLD);
        //LOGGER.info("Original Spawn Position: " + SpawnPos);
    }

    //头羊动物列表
    private static final List<EntityType<? extends Mob>> LEADERABLE_ANIMALS = Arrays.asList(
            EntityType.COW,
            EntityType.CHICKEN,
            EntityType.WOLF,
            EntityType.SHEEP,
            EntityType.PIG,
            EntityType.HORSE
    );


    //Mark头羊  +   召唤群体
    @SubscribeEvent
    public static void onEntitySpawn(MobSpawnEvent.FinalizeSpawn  event) {

        Entity entity = event.getEntity();
        EntityType<?> entityType = entity.getType();


        if (entityType == EntityType.COW ||
                entityType == EntityType.CHICKEN ||
                entityType == EntityType.WOLF ||
                entityType == EntityType.SHEEP ||
                entityType == EntityType.PIG ||
                entityType == EntityType.HORSE ) {
            Level level = event.getLevel().getLevel();
            ServerLevel serverLevel =event.getLevel().getLevel();

            //生成leader  取消常规生成
            BlockPos leaderPos = entity.blockPosition();
            event.setSpawnCancelled(true);
            PathfinderMob leader = LEADERSHEEP.get().create(level);
            //生成追随者nbt
            assert leader != null;
            CompoundTag nbtData = leader.getPersistentData();
            nbtData.putBoolean("FollowersSpawned", false);

            //召唤追随者goal手动插入，避免重进世界重复召唤
            leader.moveTo(leaderPos.getX(), leaderPos.getY(), leaderPos.getZ(), 0.0F, 0.0F);
            leader.goalSelector.addGoal(1, new SpawnFollowerOnceGoal(leader));
            level.addFreshEntity(leader);

            //LOGGER.info("==============spawned"+leaderPos );

//                //生成追随
//                for (int i = 0; i < 11; i++) {
//                    ;
//                   // LOGGER.info(" Time to spawn follower" );//额外生成数量;
//                    Entity followerEntity = entityType.create(level);
//                    // 随机生成范围,准备检测
//                    int randomX = RANDOM.nextInt(5) - 2; // Random value between -15 and 15 for X
//                    int randomZ = RANDOM.nextInt(5) - 2;
//
//                    int X =leaderPos.getX();
//                    int Y =leaderPos.getY();
//                    int Z =leaderPos.getZ();
//                    BlockPos randomPos = new BlockPos( X+ randomX, Y, Z + randomZ);
//                   // BlockPos groundPos = spawnPos.below();
//                    followerEntity.setPos(leaderPos.getX(),leaderPos.getY()+44,leaderPos.getZ());
//                    level.addFreshEntity(followerEntity);
//                }
                    //开局跑几步,让堆叠的散开
                   // Mob mob = (Mob) followerEntity;
                   // mob.getNavigation().moveTo(randomPos.getX(),randomPos.getY(),randomPos.getZ(),1.5);
//
//                    if(followerEntity.isInWall()){
//                        LOGGER.info("wallllllllllllllllllllll=" +spawnPos);
//                    }
//                    BlockState spawnPosState = level.getBlockState(spawnPos);
//                    BlockState groundPosState = level.getBlockState(groundPos);


//
//                    //脚底坐标是空气的话，下降到不是空气为止
//                    while(groundPosState.isAir()&&groundPos.getY()>-120){
//                        LOGGER.info("Air ground !!!!"+groundPosState+"----- "+ groundPos);
//                        groundPos = groundPos.below(); //脚底坐标Y--
//                        spawnPos= groundPos.above();   //同步生成坐标
//                        groundPosState= level.getBlockState(groundPos);   //同步脚底坐标
//                    }
//
//                    //脚底是实心的话，检测生成坐标是不是实心，否则上升到空气为止
//                    while(!spawnPosState.isAir()&&spawnPos.getY()<256){
//                        LOGGER.info("stuck"+  spawnPosState+"==== "+   spawnPos);
//                        spawnPos= spawnPos.above();     //生成坐标Y++
//                        spawnPosState= level.getBlockState(spawnPos);   //同步生成坐标
//                    }
//                    followerEntity.setPos(spawnPos.getX(),spawnPos.getY(),spawnPos.getZ());
//                    level.addFreshEntity(followerEntity);


    }
    }}





//    @Nullable
//    protected static boolean checkAir( Level level,BlockPos testPos) {
//        BlockState blockState = level.getBlockState(testPos);
//        return blockState.isAir();
//    }
//
//    @Nullable
//    protected static boolean checkSolid( Level level, BlockPos testPos) {
//        BlockState blockState = level .getBlockState(testPos);
//        return blockState.isSolidRender(level ,testPos);
//    }
//
//    public static BlockPos findValidSpawnPos(Level level, BlockPos pos) {
//        BlockPos validPos = pos;
//
//        // Check for ground being air (too high)
//        while (level.getBlockState(validPos.below()).isAir() && validPos.getY() > level.getMinBuildHeight()) {
//            validPos = validPos.below();
//        }
//
//        // Check for position being solid (too low)
//        while (!level.getBlockState(validPos).isAir() && validPos.getY() < level.getMaxBuildHeight()) {
//            validPos = validPos.above();
//        }
//
//        // Final check: ensure ground is solid and position is air
//        BlockPos groundPos = validPos.below();
//        BlockState groundState = level.getBlockState(groundPos);
//        BlockState airState = level.getBlockState(validPos);
//
//        if (groundState.isSolidRender(level, groundPos) && airState.isAir()) {
//            return validPos;
//        }
//
//        // If no valid position is found
//        return null;
//    }
//
//    public static boolean canSpawn(Level level, BlockPos pos) {
//        BlockPos validPos = findValidSpawnPos(level, pos);
//        return validPos != null;
//    }



