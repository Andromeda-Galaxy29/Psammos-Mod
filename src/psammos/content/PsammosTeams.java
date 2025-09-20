package psammos.content;

import arc.graphics.Color;
import mindustry.game.Team;

public class PsammosTeams {
    public static Team erimos = Team.blue;

    public static void load(){
        erimos = editTeam(erimos, "erimos", Color.valueOf("7494e5"),
                Color.valueOf("90baed"),
                Color.valueOf("7590d3"),
                Color.valueOf("5557a7")
        );
    }

    public static void init(){
        erimos.emoji = "\uECC3";
    }

    public static Team editTeam(Team team, String name, Color color, Color pal1, Color pal2, Color pal3){
        team.name = name;
        team.setPalette(pal1, pal2, pal3);
        team.color.set(color);

        return team;
    }
}
