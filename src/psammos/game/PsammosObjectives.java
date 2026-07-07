package psammos.game;

import arc.Core;
import mindustry.game.Objectives;

public class PsammosObjectives {
    public static class FutureUpdate implements Objectives.Objective {

        public FutureUpdate() {

        }

        public boolean complete() {
            return false;
        }

        public String display() {
            return Core.bundle.get("requirement.psammos-future-update");
        }

        public String toString() {
            return "future update";
        }
    }
}
