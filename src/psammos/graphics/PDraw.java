package psammos.graphics;

import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Log;
import mindustry.Vars;
import mindustry.graphics.Drawf;

public class PDraw {
    public static void spinLineSprite(TextureRegion region, float x, float y, float r){
        float a = Draw.getColorAlpha();
        r = Mathf.mod(r, 360f);

        Draw.rect(region, x, y, r);

        float alphaMult = Mathf.clamp(-Math.abs(r - 135f) / 90f + 1.5f);
        Draw.alpha(alphaMult * a);
        Draw.scl(1, -1);
        Draw.rect(region, x, y, r);

        Draw.alpha(a);
        Draw.scl(1);
    }

    public static void laser(TextureRegion line, TextureRegion edge, float x, float y, float x2, float y2, float scale, Color c){
        laser(line, edge, edge, x, y, c, x2, y2, c, scale);
    }

    public static void laser(TextureRegion line, TextureRegion edge, float x, float y, Color c, float x2, float y2, Color c2, float scale){
        laser(line, edge, edge, x, y, c, x2, y2, c2, scale);
    }

    public static void laser(TextureRegion line, TextureRegion start, TextureRegion end, float x, float y, Color c, float x2, float y2, Color c2, float scale){
        float scl = 8f * scale * Draw.scl, rot = Mathf.angle(x2 - x, y2 - y);
        float vx = Mathf.cosDeg(rot) * scl, vy = Mathf.sinDeg(rot) * scl;

        Lines.stroke(12f * scale);
        PDraw.tiledLine(line, x + vx, y + vy, c, x2 - vx, y2 - vy, c2);
        Lines.stroke(1f);

        Draw.color(c);
        Draw.rect(start, x, y, start.width * scale * start.scl(), start.height * scale * start.scl(), rot + 180);
        Draw.color(c2);
        Draw.rect(end, x2, y2, end.width * scale * end.scl(), end.height * scale * end.scl(), rot);

        Drawf.light(x, y, x2, y2);

        Draw.color();
    }

    public static void tiledLine(TextureRegion region, float x, float y, Color c, float x2, float y2, Color c2) {
        int segments = Mathf.ceil(Mathf.len(x2 - x, y2 - y) / (region.width / 4f));
        for (int i = 0; i < segments; i++) {
            float xi = Mathf.lerp(x, x2, 1f / segments * i);
            float yi = Mathf.lerp(y, y2, 1f / segments * i);
            float xi2 = Mathf.lerp(x, x2, 1f / segments * (i + 1));
            float yi2 = Mathf.lerp(y, y2, 1f / segments * (i + 1));
            Color ci = c.cpy().lerp(c2, 1f / segments * i);
            Color ci2 = c.cpy().lerp(c2, 1f / segments * (i + 1));

            line(region, xi, yi, ci, xi2, yi2, ci2, false);
        }
    }

    public static void line(TextureRegion region, float x, float y, Color c, float x2, float y2, Color c2, boolean cap){
        float color1 = c.toFloatBits();
        float color2 = c2.toFloatBits();
        float hstroke = Lines.getStroke() / 2.0F;
        float len = Mathf.len(x2 - x, y2 - y);
        float diffx = (x2 - x) / len * hstroke;
        float diffy = (y2 - y) / len * hstroke;
        if (cap) {
            Fill.quad(region, x - diffx - diffy, y - diffy + diffx, color1, x - diffx + diffy, y - diffy - diffx, color1, x2 + diffx + diffy, y2 + diffy - diffx, color2, x2 + diffx - diffy, y2 + diffy + diffx, color2);
        } else {
            Fill.quad(region, x - diffy, y + diffx, color1, x + diffy, y - diffx, color1, x2 + diffy, y2 - diffx, color2, x2 - diffy, y2 + diffx, color2);
        }
    }
}
