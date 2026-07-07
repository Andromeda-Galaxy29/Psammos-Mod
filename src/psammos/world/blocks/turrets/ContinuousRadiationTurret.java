package psammos.world.blocks.turrets;

import arc.Core;
import arc.graphics.Color;
import arc.struct.*;
import mindustry.entities.bullet.*;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import psammos.type.RadiationStack;
import psammos.type.RadiationType;
import psammos.world.blocks.radiation.RadiationConsumer;
import psammos.world.meta.PsammosStats;

public class ContinuousRadiationTurret extends ContinuousTurret {
    public ObjectMap<RadiationType, BulletType> ammoTypes = new ObjectMap<>();
    public float radiationRequirement = 10f;
    public float maxRadiationEfficiency = 3f;

    public ContinuousRadiationTurret(String name) {
        super(name);
    }

    public void ammo(Object... objects){
        ammoTypes = ObjectMap.of(objects);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.input, radiationRequirement, PsammosStats.radiationUnits);
        stats.replace(Stat.ammo, StatValues.ammo(ammoTypes));
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("psammos-radiation", (ContinuousRadiationTurretBuild b) -> new Bar(
                () -> b.barRad() == null ? Core.bundle.get("bar.psammos-radiation") :
                        Core.bundle.format("bar.psammos-radiation-percent",
                                b.barRad().type.localizedName,
                                b.barRad().amount,
                                (int) Math.min(b.barRad().amount / radiationRequirement * 100, maxRadiationEfficiency * 100)
                        ),
                () -> b.barRad() == null ? Color.clear : b.barRad().type.color,
                () -> b.barRad() == null ? 0f : b.barRad().amount / radiationRequirement
        ));
    }

    @Override
    public void init() {
        if(targetGround){
            ammoTypes.each((rad, type) -> placeOverlapRange = Math.max(placeOverlapRange, range + type.rangeChange + placeOverlapMargin));
        }

        super.init();
    }

    public class ContinuousRadiationTurretBuild extends ContinuousTurretBuild implements RadiationConsumer {
        public Seq<Building> radiationInputs = new Seq<>();
        public RadiationStack currentRadiation = null;
        boolean activated;

        public RadiationStack barRad(){
            if (currentRadiation == null || currentRadiation.type == null) {
                return null;
            }
            return currentRadiation;
        }

        @Override
        public boolean shouldActiveSound(){
            return wasShooting && enabled;
        }

        @Override
        public void updateTile(){
            super.updateTile();

            currentRadiation = calculateHighestRadiation(this, radiationInputs);

            if (currentRadiation != null) {
                unit.ammo(unit.type().ammoCapacity * currentRadiation.amount / radiationRequirement);

                activated = currentRadiation.amount > 0f;
            }else{
                activated = false;
            }
        }

        @Override
        public void updateEfficiencyMultiplier(){
            super.updateEfficiencyMultiplier();
            if (currentRadiation != null) {
                efficiency *= Math.min(currentRadiation.amount / radiationRequirement, maxRadiationEfficiency);
            }
        }

        @Override
        public boolean canConsume(){
            return currentRadiation != null && currentRadiation.amount > 0f && hasCorrectAmmo() && super.canConsume();
        }

        @Override
        public boolean shouldConsume(){
            return super.shouldConsume() && activated;
        }

        @Override
        public BulletType useAmmo(){
            //does not consume ammo upon firing
            return peekAmmo();
        }

        @Override
        public BulletType peekAmmo(){
            if (currentRadiation == null || currentRadiation.type == null) {
                return null;
            }
            return ammoTypes.get(currentRadiation.type);
        }

        @Override
        public boolean hasAmmo(){
            if (currentRadiation == null || currentRadiation.type == null) {
                return false;
            }
            return hasCorrectAmmo() && ammoTypes.get(currentRadiation.type) != null && currentRadiation.amount > 0f;
        }

        public boolean hasCorrectAmmo(){
            return !bullets.any() || bullets.first().bullet.type == peekAmmo();
        }

        @Override
        public void addRadiationInput(Building build) {
            if (!radiationInputs.contains(build)){
                radiationInputs.add(build);
            }
        }

        @Override
        public boolean acceptsRadiation(RadiationType type, int from) {
            return ammoTypes.containsKey(type);
        }
    }
}
