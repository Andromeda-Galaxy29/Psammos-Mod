package psammos.content;

import arc.Core;
import arc.graphics.Texture;
import arc.graphics.g2d.Font;
import arc.graphics.g2d.TextureRegion;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Scaling;
import mindustry.ui.Fonts;

public class PsammosIcons {

    public static void init(){
        loadIcon(60611, "psammos-team-blue"); // \uECC3
        loadIcon(60612, "psammos-quicksand-slowed"); // \uECC4
        loadIcon(60613, "psammos-infested"); // \uECC5
        loadIcon(60614, "psammos-swarm-protection"); // \uECC6
        loadIcon(60615, "psammos-combustible"); // \uECC7
    }

    public static void loadIcon(int id, String regionName){
        Seq<Font> fonts = Seq.with(Fonts.def, Fonts.outline);
        Texture uitex = Core.atlas.find("logo").texture;
        int size = (int)(Fonts.def.getData().lineHeight/Fonts.def.getData().scaleY);

        TextureRegion region = Core.atlas.find(regionName);

        if(region.texture != uitex){
            return;
        }

        Vec2 out = Scaling.fit.apply(region.width, region.height, size, size);

        Font.Glyph glyph = new Font.Glyph();
        glyph.id = id;
        glyph.srcX = 0;
        glyph.srcY = 0;
        glyph.width = (int)out.x;
        glyph.height = (int)out.y;
        glyph.u = region.u;
        glyph.v = region.v2;
        glyph.u2 = region.u2;
        glyph.v2 = region.v;
        glyph.xoffset = 0;
        glyph.yoffset = -size;
        glyph.xadvance = size;
        glyph.kerning = null;
        glyph.fixedWidth = true;
        glyph.page = 0;
        fonts.each(f -> f.getData().setGlyph(id, glyph));
    }
}