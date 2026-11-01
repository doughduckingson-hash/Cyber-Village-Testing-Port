/*
 * Decompiled with CFR 0.152.
 */
package com.cybervillage.engram;

public enum Engram {
    HACKER,
    NINJA,
    DESTROYER;


    public static Engram byOrdinal(int ordinal) {
        Engram[] values = Engram.values();
        return values[(ordinal % values.length + values.length) % values.length];
    }

    public Engram next() {
        return Engram.byOrdinal(this.ordinal() + 1);
    }

    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}

