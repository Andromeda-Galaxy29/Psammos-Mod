package psammos.content;

import mindustry.graphics.*;

public class PsammosCacheLayers {
    public static CacheLayer quicksand;

    public static void load(){
        CacheLayer.add(
                quicksand = new CacheLayer.ShaderLayer(PsammosShaders.quicksand)
        );
    }
}
