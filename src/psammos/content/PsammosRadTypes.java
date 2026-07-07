package psammos.content;

import arc.graphics.Color;
import mindustry.graphics.Pal;
import psammos.type.RadiationType;

public class PsammosRadTypes {
    public static RadiationType
            radio, IR, light, UV, xRays, gamma;

    public static void load() {
        radio = new RadiationType("radio-waves", Color.valueOf("ab9fbc"));
        IR = new RadiationType("infrared-light", Color.valueOf("ff5740"));
        light = new RadiationType("visible-light", Pal.accent.cpy());
        UV = new RadiationType("ultraviolet-light", Color.valueOf("a53cf0"));
        xRays = new RadiationType("x-rays", Color.valueOf("6aaaf6"));
        gamma = new RadiationType("gamma-rays", Color.valueOf("ea833e"));
    }
}
