package net.Dripdoom.dripmod.components;

import com.mojang.serialization.Codec;
import net.Dripdoom.dripmod.DripMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.swing.text.html.parser.Entity;
import java.util.function.UnaryOperator;
public class ModDataComponents {

// ---------------------- CUSTOM DATA COMPONENTS ----------------------//

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, DripMod.MOD_ID);

    public static final RegistryObject<DataComponentType<Boolean>> CUSTOM_BOOL = register("custom_boolean",
            builder -> builder.persistent(Codec.BOOL));

    public static final RegistryObject<DataComponentType<BlockState>> CUSTOM_STATE = register("custom_state",
            builder -> builder.persistent(BlockState.CODEC));

    public static final RegistryObject<DataComponentType<BlockPos>> CUSTOM_POS = register("custom_pos",
            builder -> builder.persistent(BlockPos.CODEC));

    public static final RegistryObject<DataComponentType<Vec3>> CUSTOM_VECTOR = register("custom_vector",
            builder -> builder.persistent(Vec3.CODEC));


//---------------------- END OF REGISTRATION ------------------------//

    private static <T>RegistryObject<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
