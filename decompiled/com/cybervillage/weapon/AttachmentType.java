/*
 * Decompiled with CFR 0.152.
 */
package com.cybervillage.weapon;

public enum AttachmentType {
    SIGHT,
    BARREL,
    RECEIVER,
    FIRMWARE;


    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}

