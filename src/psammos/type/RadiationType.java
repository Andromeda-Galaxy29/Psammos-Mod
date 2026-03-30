package psammos.type;

import arc.graphics.Color;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.logic.LAccess;
import mindustry.logic.Senseable;
import mindustry.type.Planet;

public class RadiationType extends UnlockableContent implements Senseable {

    public static ContentType ct = ContentType.effect_UNUSED;

    public Color color = Color.black;
    public boolean hidden;

    public RadiationType(String name, Color color) {
        super(name);
        this.color = color;
    }

    public RadiationType(String name) {
        super(name);
    }

    @Override
    public ContentType getContentType() {
        return ct;
    }

    @Override
    public boolean isOnPlanet(Planet planet) {
        return super.isOnPlanet(planet) && !this.hidden;
    }

    @Override
    public boolean isHidden() {
        return this.hidden;
    }

    @Override
    public String toString() {
        return this.localizedName;
    }

    @Override
    public double sense(LAccess sensor) {
        if (sensor == LAccess.color) {
            return this.color.toDoubleBits();
        } else {
            return sensor == LAccess.id ? (double)this.getLogicId() : Double.NaN;
        }
    }

    @Override
    public Object senseObject(LAccess sensor) {
        return sensor == LAccess.name ? this.name : noSensed;
    }
}
