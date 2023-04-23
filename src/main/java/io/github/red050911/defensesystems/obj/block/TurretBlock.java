package io.github.red050911.defensesystems.obj.block;

import io.github.red050911.defensesystems.obj.blockentity.TurretBlockEntity;
import io.github.red050911.defensesystems.reg.ModBlockEntityTypes;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TurretBlock extends BlockWithEntity {

    public TurretBlock() {
        super(Settings.of(Material.METAL));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return ModBlockEntityTypes.TURRET.instantiate();
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if(placer instanceof PlayerEntity) {
            BlockEntity be = world.getBlockEntity(pos);
            if(be instanceof TurretBlockEntity) ((TurretBlockEntity) be).setOwnerID(placer.getUuid());
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}
