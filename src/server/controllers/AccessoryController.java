package server.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Logger;
import server.models.Accessory;
import server.models.Console;
import server.models.Game;
import server.models.Manufacturer;
import server.models.services.AccessoryService;
import server.models.services.ConsoleService;
import server.models.services.GameService;
import server.models.services.ManufacturerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@SuppressWarnings({"unchecked", "Duplicates"})
@Path("accessory/")
public class AccessoryController {

    @GET
    @Path("list/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listGames(@PathParam("id") int id) {

        Logger.log(Integer.toString(id));

        Logger.log("/accessory/list - Getting all accessories from database");
        String status = AccessoryService.selectAllInto(Accessory.accessories);

        if (status.equals("OK")) {

            JSONArray accessoryList = new JSONArray();
            for (Accessory a: Accessory.accessories) {

                if (a.getConsoleId() == id) {

                    if (a.getImageURL() == null) {
                        a.setImageURL("/client/img/none.png");
                    }

                    JSONObject jg = a.toJSON();
                    accessoryList.add(jg);

                }

            }

            return accessoryList.toString();

        } else {
            JSONObject response = new JSONObject();
            response.put("error", status);
            return response.toString();
        }

    }

}
