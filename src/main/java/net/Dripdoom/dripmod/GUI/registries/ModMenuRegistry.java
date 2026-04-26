package net.Dripdoom.dripmod.GUI.registries;

import net.Dripdoom.dripmod.DripMod;
import net.Dripdoom.dripmod.GUI.menu.DisplayerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuRegistry {
<<<<<<< HEAD

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, DripMod.MOD_ID);

=======
    
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, DripMod.MOD_ID);

    
>>>>>>> 4620073e90a8e415855b40425823e8a320e596c4
    public static final RegistryObject<MenuType<DisplayerMenu>> DISPLAYER_MENU =
            MENUS.register("displayermenu",
                    () -> IForgeMenuType.create(DisplayerMenu::new));

    public static void register(IEventBus eventbus){
        MENUS.register(eventbus);
    }
}
