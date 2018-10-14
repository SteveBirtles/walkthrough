package server.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Logger;
import server.models.Console;
import server.models.Game;
import server.models.services.AdminService;
import server.models.services.ConsoleService;
import server.models.services.GameService;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

@SuppressWarnings({"unchecked", "Duplicates"})
@Path("game/")
public class GameController {

    @GET
    @Path("list/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listGames(@PathParam("id") int id) {

        Logger.log("/game/list - Getting all games from database");
        String status = GameService.selectAllInto(Game.games);

        if (status.equals("OK")) {

            JSONObject response = new JSONObject();

            Console c = ConsoleService.selectById(id);
            response.put("consoleName", c.getName());

            JSONArray gameList = new JSONArray();
            for (Game g: Game.games) {

                if (g.getConsoleId() == id) {

                    if (g.getImageURL().equals("")) {
                        g.setImageURL("/client/img/none.png");
                    }

                    JSONObject jg = g.toJSON();
                    gameList.add(jg);

                }

            }

            response.put("gamesList", gameList);

            return response.toString();

        } else {
            JSONObject response = new JSONObject();
            response.put("error", status);
            return response.toString();
        }

    }


    @GET
    @Path("get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getGame(@PathParam("id") int id) {

        Logger.log("/game/get/"+ id + " - Getting game details from database");

        Game g = GameService.selectById(id);
        if (g != null) {

            JSONObject cj = g.toJSON();
            return cj.toString();

        } else {

            return "{'error': 'Can't find game with id " + id + "'}";

        }

    }

    @POST
    @Path("save/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String saveGame(  @PathParam("id") int id,
                             @FormParam("consoleId") int consoleId,
                             @FormParam("name") String name,
                             @FormParam("sales") String sales,
                             @FormParam("year") String year,
                             @FormParam("imageURL") String imageURL,
                             @CookieParam("sessionToken") Cookie sessionCookie) {

        String currentUsername = AdminService.validateSessionCookie(sessionCookie);
        if (currentUsername == null) return "Error: Invalid user session token";

        if (id == -1) {
            GameService.selectAllInto(Game.games);
            id = Game.nextId();
            Game newGame = new Game(id, consoleId, name, sales, year, imageURL);
            return GameService.insert(newGame);
        } else {
            Game existingGame = GameService.selectById(id);
            if (existingGame == null) {
                return "That game doesn't appear to exist";
            } else {
                existingGame.setName(name);
                existingGame.setSales(sales);
                existingGame.setYear(year);
                existingGame.setImageURL(imageURL);
                return GameService.update(existingGame);
            }
        }
    }

    @POST
    @Path("delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteGame(@PathParam("id") int id,
                                @CookieParam("sessionToken") Cookie sessionCookie) {

        String currentUsername = AdminService.validateSessionCookie(sessionCookie);
        if (currentUsername == null) return "Error: Invalid user session token";

        Logger.log("/game/delete - Game " + id);
        Game game = GameService.selectById(id);
        if (game == null) {
            return "That game doesn't appear to exist";
        } else {
            return GameService.deleteById(id);
        }
    }

}
