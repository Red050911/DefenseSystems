package io.github.red050911.defensesystems.obj.block;

import io.github.red050911.defensesystems.obj.blockentity.CameraBoxBlockEntity;
import io.github.red050911.defensesystems.reg.ModBlockEntityTypes;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CameraBoxBlock extends BlockWithEntity {

    private final boolean telescopic;
    private final boolean seesInvis;
    private final boolean seesInDark;

    public CameraBoxBlock(boolean telescopic, boolean seesInvis, boolean seesInDark) {
        super(Settings.of(Material.METAL));
        this.telescopic = telescopic;
        this.seesInvis = seesInvis;
        this.seesInDark = seesInDark;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        CameraBoxBlockEntity be = ModBlockEntityTypes.CAMERA_BOX.instantiate(pos, state);
        if(be != null) {
            be.setTelescopic(telescopic);
            be.setSeesInvisPlayers(seesInvis);
            be.setSeesInDark(seesInDark);
        }
        return be;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if(placer instanceof PlayerEntity) {
            BlockEntity be = world.getBlockEntity(pos);
            if(be instanceof CameraBoxBlockEntity) ((CameraBoxBlockEntity) be).setOwnerID(placer.getUuid());
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntityTypes.CAMERA_BOX, CameraBoxBlockEntity::tick);
    }

}
