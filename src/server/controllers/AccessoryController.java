package server.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Logger;
import server.models.*;
import server.models.services.*;

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


    @GET
    @Path("get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAccessory(@PathParam("id") int id) {

        Accessory a = AccessoryService.selectById(id);
        if (a != null) {

            Category c = CategoryService.selectById(a.getCategoryId());

            JSONObject cj = a.toJSON();

            cj.put("category", c.getName());

            return cj.toString();

        } else {

            return "{'error': 'Can't find accessory with id " + id + "'}";

        }

    }


}
