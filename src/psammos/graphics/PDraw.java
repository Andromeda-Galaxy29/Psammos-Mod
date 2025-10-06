package psammos.graphics;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;

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
}
