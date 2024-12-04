package org.laba.data;

import lombok.Data;

@Data
public class Pack {
    private int value;
    private int weight;
    private float valuePerWeight;
    private boolean used;

    public Pack(int value, int weight) {
        this.value = value;
        this.weight = weight;
        this.valuePerWeight = value / (float) weight;
        this.used = false;
    }
}