package me.dylangunn.fortuneplus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class FortunePlusListener implements Listener {
  HashSet<Material> pickSet = new HashSet<>();
  HashMap<Material, Material> drops = new HashMap<>();

  public FortunePlusListener() {
    this.pickSet.add(Material.IRON_PICKAXE);
    this.pickSet.add(Material.DIAMOND_PICKAXE);
    this.pickSet.add(Material.NETHERITE_PICKAXE);
    this.drops.put(Material.IRON_ORE, Material.IRON_INGOT);
    this.drops.put(Material.GOLD_ORE, Material.GOLD_INGOT);
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (event.getPlayer() == null)
      return;

    Block brokenBlock = event.getBlock();
    Material block = brokenBlock.getType();

    if (block == Material.IRON_ORE || block == Material.GOLD_ORE) {
      ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
      Material item = itemInHand.getType();

      if (this.pickSet.contains(item) || (item == Material.STONE_PICKAXE && block == Material.IRON_ORE)) {
        if (!event.getPlayer().hasPermission("fortuneplus." + brokenBlock.getType().name().toLowerCase()))
          return;

        Map<Enchantment, Integer> enchantments = itemInHand.getEnchantments();

        if (enchantments.containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
          event.setCancelled(true);

          int numDrops = getNumDrops(((Integer) enchantments.get(Enchantment.LOOT_BONUS_BLOCKS)).intValue());

          brokenBlock.getWorld().dropItemNaturally(brokenBlock.getLocation(),
              new ItemStack(this.drops.get(block), numDrops));
          ((ExperienceOrb) brokenBlock.getWorld().spawn(brokenBlock.getLocation(), ExperienceOrb.class))
              .setExperience(numDrops);
          brokenBlock.setType(Material.AIR);
        }
      }
    }
  }

  private int getNumDrops(int fortuneLevel) {
    Random numGen = new Random();
    int random = numGen.nextInt(99) + 1;
    int baseWeight = 200 / (fortuneLevel + 2);
    int levelWeight = (100 - baseWeight) / fortuneLevel;
    if (random > baseWeight)
      return (random - baseWeight) / levelWeight + 1;
    return 1;
  }
}