package net.Dripdoom.dripmod.ModThings;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModTiers {
    public static Tier MY_CUSTOM_TIER() {

        return new Tier() {
            @Override
            public int getUses() {
                return 2100;
            }

            @Override
            public float getSpeed() {
                return 9.0F;
            }

            @Override
            public float getAttackDamageBonus() {
                return 4.0F;
            }

            @Override
            public TagKey<Block> getIncorrectBlocksForDrops(){
                return null;
            }

            @Override
            public int getEnchantmentValue() {
                return 4;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return null;
            }
        };

    }
}