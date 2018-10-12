package server.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Logger;
import server.models.Console;
import server.models.Game;
import server.models.Manufacturer;
import server.models.services.ConsoleService;
import server.models.services.GameService;
import server.models.services.ManufacturerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@SuppressWarnings({"unchecked", "Duplicates"})
@Path("game/")
public class GameController {

    @GET
    @Path("list/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listGames(@PathParam("id") int id) {

        Logger.log(Integer.toString(id));

        Logger.log("/game/list - Getting all games from database");
        String status = GameService.selectAllInto(Game.games);

        if (status.equals("OK")) {

            JSONArray gameList = new JSONArray();
            for (Game g: Game.games) {

                if (g.getConsoleId() == id) {

                    if (g.getImageURL() == null) {
                        g.setImageURL("/client/img/none.png");
                    }

                    JSONObject jg = g.toJSON();
                    gameList.add(jg);

                }

            }

            return gameList.toString();

        } else {
            JSONObject response = new JSONObject();
            response.put("error", status);
            return response.toString();
        }

    }

}
