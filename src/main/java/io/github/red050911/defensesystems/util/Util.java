package io.github.red050911.defensesystems.util;

import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.WorldChunk;

import java.util.*;
import java.util.function.Predicate;

public class Util {

    public static List<BlockEntity> getBlockEntities(World w, Box b, Predicate<BlockEntity> predicate) {
        List<BlockEntity> list = new ArrayList<>();
        int i = MathHelper.floor((b.minX - 2.0) / 16.0);
        int j = MathHelper.ceil((b.maxX + 2.0) / 16.0);
        int k = MathHelper.floor((b.minZ - 2.0) / 16.0);
        int l = MathHelper.ceil((b.maxZ + 2.0) / 16.0);
        ChunkManager chunkManager = w.getChunkManager();
        for (int m = i; m < j; ++m) {
            for (int n = k; n < l; ++n) {
                WorldChunk worldChunk = chunkManager.getWorldChunk(m, n, false);
                if (worldChunk == null) continue;
                Set<Map.Entry<BlockPos, BlockEntity>> blockEntities = worldChunk.getBlockEntities().entrySet();
                for(Map.Entry<BlockPos, BlockEntity> entry : blockEntities) {
                    Box b2 = new Box(entry.getKey());
                    if(b2.intersects(b) && predicate.test(entry.getValue())) {
                        list.add(entry.getValue());
                    }
                }
            }
        }
        return list;
    }

    public static boolean noBlocksInTheWay(Vec3d entityPos, BlockPos ourPos, World w) {
        Vec3d pos = new Vec3d(ourPos.getX(), ourPos.getY(), ourPos.getZ());
        int numIterations = (int) Math.floor(entityPos.distanceTo(pos)) * 4;
        double moveX = (entityPos.x - pos.x) / numIterations;
        double moveY = (entityPos.y - pos.y) / numIterations;
        double moveZ = (entityPos.z - pos.z) / numIterations;
        for(int i = 0; i < numIterations; i++) {
            BlockPos bp = new BlockPos(Math.floor(pos.x), Math.floor(pos.y), Math.floor(pos.z));
            if(w.getBlockState(bp).isOpaque() && !bp.equals(ourPos)) return false;
            pos = pos.add(moveX, moveY, moveZ);
        }
        return true;
    }

    public static void spawnDestroyedOnLandingAnvil(Vec3d pos, World world, int v) {
        FallingBlockEntity entity = new FallingBlockEntity(world, pos.x, pos.y, pos.z, Blocks.ANVIL.getDefaultState());
        entity.destroyedOnLanding = true;
        entity.timeFalling = 1;
        entity.setHurtEntities(true);
        world.spawnEntity(entity);
        float v1 = v * 0.005f;
        entity.addVelocity(0, -v1, 0);
    }

}
