package psammos.content;

import arc.graphics.Color;
import arc.util.Time;
import mindustry.content.StatusEffects;
import mindustry.gen.Sounds;
import mindustry.type.Weather;
import mindustry.type.weather.ParticleWeather;
import mindustry.world.meta.Attribute;

public class PsammosWeathers {

    public static Weather lightSandstorm, heavySandstorm, smog;

    public static void load(){
        lightSandstorm = new ParticleWeather("light-sandstorm"){{
            color = noiseColor = Color.valueOf("f7cba4");
            particleRegion = "particle";
            drawNoise = true;
            useWindVector = true;
            sizeMax = 140f;
            sizeMin = 70f;
            minAlpha = 0f;
            maxAlpha = 0.2f;
            density = 1200f;
            baseSpeed = 5.4f;
            opacityMultiplier = 0.25f;
            sound = Sounds.wind;
            soundVol = 0.6f;
            duration = 7f * Time.toMinutes;
        }};

        heavySandstorm = new ParticleWeather("heavy-sandstorm"){{
            color = noiseColor = Color.valueOf("f7cba4");
            particleRegion = "circle-small";
            drawNoise = true;
            useWindVector = true;
            sizeMax = 5f;
            sizeMin = 2.5f;
            minAlpha = 0.1f;
            maxAlpha = 0.8f;
            density = 2000f;
            baseSpeed = 10f;
            attrs.set(Attribute.light, -0.2f);
            attrs.set(Attribute.water, -0.2f);
            opacityMultiplier = 0.6f;
            force = 0.2f;
            sound = Sounds.wind;
            soundVol = 0.7f;
            duration = 7f * Time.toMinutes;
        }};

        smog = new ParticleWeather("smog"){{
            color = noiseColor = Color.grays(0.1f);

            drawParticles = true;
            particleRegion = "circle-small";
            sizeMax = 3f;
            sizeMin = 1f;
            minAlpha = 0.1f;
            maxAlpha = 0.8f;
            density = 4000f;

            drawNoise = true;
            noiseLayers = 3;
            noiseLayerAlphaM = 0.7f;
            noiseLayerSpeedM = 3f;
            noiseLayerSclM = 0.5f;
            noiseScale = 1100f;
            noisePath = "fog";
            opacityMultiplier = 0.47f;

            useWindVector = false;
            baseSpeed = 0.05f;
            xspeed = 0.01f;
            yspeed = -0.8f;

            status = StatusEffects.tarred;
            statusAir = true;
            statusGround = false;
            attrs.set(Attribute.light, -0.3f);
            attrs.set(Attribute.oil, 0.05f);

            duration = 15f * Time.toMinutes;
        }};
    }
}
