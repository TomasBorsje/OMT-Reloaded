package io.tomasborsje.omtreloaded.containers;

import io.tomasborsje.omtreloaded.core.AbstractTurretBaseEntity;
import io.tomasborsje.omtreloaded.registration.ModBlocks;
import io.tomasborsje.omtreloaded.registration.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class SimpleTurretBaseContainer extends AbstractContainerMenu {

    private final BlockPos pos;
    private final static int SLOT_COUNT = 4;
    private final static int SLOT_INPUT = 0;
    private AbstractTurretBaseEntity entity;


    public SimpleTurretBaseContainer(int windowId, Player player, BlockPos pos) {
        super(ModMenuTypes.SIMPLE_TURRET_BASE_CONTAINER.get(), windowId);
        this.pos = pos;
        if (player.level().getBlockEntity(pos) instanceof AbstractTurretBaseEntity turretBase) {
            addSlot(new SlotItemHandler(turretBase.getItemHandler(), SLOT_INPUT, 108, 24));
            addSlot(new SlotItemHandler(turretBase.getItemHandler(), SLOT_INPUT + 1, 126, 24));
            addSlot(new SlotItemHandler(turretBase.getItemHandler(), SLOT_INPUT + 2, 144, 24));
            addSlot(new SlotItemHandler(turretBase.getItemHandler(), SLOT_INPUT + 3, 108, 42));
            addSlot(new SlotItemHandler(turretBase.getItemHandler(), SLOT_INPUT + 4, 126, 42));
            addSlot(new SlotItemHandler(turretBase.getItemHandler(), SLOT_INPUT + 5, 144, 42));
            entity = turretBase;
        }
        layoutPlayerInventorySlots(player.getInventory(), 10, 70);
    }

    private int addSlotRange(Container playerInventory, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(playerInventory, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(Container playerInventory, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(playerInventory, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(Container playerInventory, int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index < SLOT_COUNT) {
                if (!this.moveItemStackTo(stack, SLOT_COUNT, Inventory.INVENTORY_SIZE + SLOT_COUNT, true)) {
                    return ItemStack.EMPTY;
                }
            }
            if (!this.moveItemStackTo(stack, SLOT_INPUT, SLOT_INPUT + 1, false)) {
                if (index < 27 + SLOT_COUNT) {
                    if (!this.moveItemStackTo(stack, 27 + SLOT_COUNT, 36 + SLOT_COUNT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < Inventory.INVENTORY_SIZE + SLOT_COUNT && !this.moveItemStackTo(stack, SLOT_COUNT, 27 + SLOT_COUNT, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(player.level(), pos), player, ModBlocks.SIMPLE_TURRET_BASE.get());
    }

    public @Nullable AbstractTurretBaseEntity getEntity() {
        return entity;
    }
}
