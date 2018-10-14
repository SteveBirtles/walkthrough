package server.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Logger;
import server.models.*;
import server.models.services.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

@SuppressWarnings({"unchecked", "Duplicates"})
@Path("accessory/")
public class AccessoryController {

    @GET
    @Path("list/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listAccessories(@PathParam("id") int id) {

        Logger.log(Integer.toString(id));

        Logger.log("/accessory/list - Getting all accessories from database");
        String status = AccessoryService.selectAllInto(Accessory.accessories);

        if (status.equals("OK")) {

            JSONObject response = new JSONObject();

            Console c = ConsoleService.selectById(id);
            response.put("consoleName", c.getName());

            JSONArray accessoriesList = new JSONArray();
            for (Accessory a: Accessory.accessories) {

                if (a.getConsoleId() == id) {

                    if (a.getImageURL() == null) {
                        a.setImageURL("/client/img/none.png");
                    }

                    JSONObject jg = a.toJSON();
                    accessoriesList.add(jg);

                }

            }

            response.put("accessoriesList", accessoriesList);

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
    public String getAccessory(@PathParam("id") int id) {

        Logger.log("/accessory/get/"+ id + " - Getting accessory details from database");

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

    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteAccessory(@FormParam("id") int id,
                                @CookieParam("sessionToken") Cookie sessionCookie) {

        String currentUsername = AdminService.validateSessionCookie(sessionCookie);
        if (currentUsername == null) return "Error: Invalid user session token";

        Logger.log("/console/delete - Console " + id);
        Accessory accessory = AccessoryService.selectById(id);
        if (accessory == null) {
            return "That accessory doesn't appear to exist";
        } else {

            int categoryCount = 0;
            CategoryService.selectAllInto(Category.categorys);
            for (Category c: Category.categorys) {
                if (c.getId() == accessory.getCategoryId()) {
                    categoryCount++;
                }
            }
            if (categoryCount <= 0) {
                CategoryService.deleteById(accessory.getCategoryId());
            }

            return AccessoryService.deleteById(id);
        }
    }

}
