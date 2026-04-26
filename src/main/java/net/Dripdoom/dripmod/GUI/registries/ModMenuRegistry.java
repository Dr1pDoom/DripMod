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

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, DripMod.MOD_ID);



    public static final RegistryObject<MenuType<DisplayerMenu>> DISPLAYER_MENU =
            MENUS.register("displayermenu",
                    () -> IForgeMenuType.create(DisplayerMenu::new));

    public static void register(IEventBus eventbus){
        MENUS.register(eventbus);
    }
}
