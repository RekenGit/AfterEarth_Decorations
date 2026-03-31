package online.reken.afterearth.deco.block.custom.enums;

import net.minecraft.util.StringIdentifiable;

public enum VaultDoorHinge implements StringIdentifiable {
    LEFT("left"),
    RIGHT("right");

    private final String name;

    VaultDoorHinge(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }
}