package psammos.content;

import arc.*;
import arc.graphics.*;
import arc.graphics.gl.*;
import arc.util.*;
import mindustry.graphics.*;

import static mindustry.graphics.Shaders.*;
import static mindustry.Vars.*;

public class PsammosShaders {

    public static PsammosSurfaceShader quicksand;

    public static void load(){
        quicksand = new PsammosSurfaceShader("quicksand");
    }

    public static class PsammosSurfaceShader extends Shader {
        Texture noiseTex;

        public PsammosSurfaceShader(String frag){
            super(getShaderFi("screenspace.vert"), tree.get("shaders/" + frag + ".frag"));
            loadNoise();
        }

        public String textureName(){
            return "noise";
        }

        public void loadNoise(){
            Core.assets.load("sprites/" + textureName() + ".png", Texture.class).loaded = t -> {
                t.setFilter(Texture.TextureFilter.linear);
                t.setWrap(Texture.TextureWrap.repeat);
            };
        }

        @Override
        public void apply(){
            setUniformf("u_campos", Core.camera.position.x - Core.camera.width / 2, Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_resolution", Core.camera.width, Core.camera.height);
            setUniformf("u_time", Time.time);

            if(hasUniform("u_noise")){
                if(noiseTex == null){
                    noiseTex = Core.assets.get("sprites/" + textureName() + ".png", Texture.class);
                }

                noiseTex.bind(1);
                renderer.effectBuffer.getTexture().bind(0);

                setUniformi("u_noise", 1);
            }
        }
    }
}
