package psammos.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.logic.LAccess;
import mindustry.logic.Senseable;
import mindustry.type.Planet;

public class RadiationType extends UnlockableContent implements Senseable {

    public static ContentType ct = ContentType.effect_UNUSED;

    public Color color = Color.black;
    public boolean hidden;
    public TextureRegion beam;
    public TextureRegion beamEnd;

    public RadiationType(String name, Color color) {
        this(name);
        this.color = color;
    }

    public RadiationType(String name) {
        super(name);
        this.localizedName = Core.bundle.get("radiation." + this.name + ".name", this.name);
        this.description = Core.bundle.getOrNull("radiation." + this.name + ".description");
        this.details = Core.bundle.getOrNull("radiation." + this.name + ".details");
        this.credit = Core.bundle.getOrNull("radiation." + this.name + ".credit");
    }

    @Override
    public void load() {
        super.load();
        beam = Core.atlas.find(this.name + "-beam", "psammos-radiation-beam");
        beamEnd = Core.atlas.find(this.name + "-beam-end", "psammos-radiation-beam-end");
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
    public void postInit() {
        super.postInit();
        this.databaseCategory = "radiation";
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
