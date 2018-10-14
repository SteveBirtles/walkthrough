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

        Logger.log("/accessory/list - Getting all accessories from database");
        String status = AccessoryService.selectAllInto(Accessory.accessories);

        if (status.equals("OK")) {

            JSONObject response = new JSONObject();

            Console c = ConsoleService.selectById(id);
            response.put("consoleName", c.getName());

            JSONArray accessoriesList = new JSONArray();
            for (Accessory a: Accessory.accessories) {

                if (a.getConsoleId() == id) {

                    if (a.getImageURL().equals("")) {
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
    @Path("save/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String saveAccessory(  @PathParam("id") int id,
                                  @FormParam("category") String category,
                                  @FormParam("consoleId") int consoleId,
                                  @FormParam("description") String description,
                                  @FormParam("quantity") int quantity,
                                  @DefaultValue("false") @FormParam("thirdParty") String thirdParty,
                                  @FormParam("imageURL") String imageURL,
                                  @CookieParam("sessionToken") Cookie sessionCookie) {

        String currentUsername = AdminService.validateSessionCookie(sessionCookie);
        if (currentUsername == null) return "Error: Invalid user session token";

        int categoryId = 0;
        CategoryService.selectAllInto(Category.categories);
        for (Category c : Category.categories) {
            if (c.getName().equals(category)) {
                categoryId = c.getId();
            }
        }
        if (categoryId == 0) {
            int cId = Category.nextId();
            Category newCategory = new Category(cId, category);
            CategoryService.insert(newCategory);
            categoryId = cId;
        }

        if (id == -1) {
            AccessoryService.selectAllInto(Accessory.accessories);
            id = Accessory.nextId();

            Accessory newAccessory = new Accessory(id,
                                                   categoryId,
                                                   consoleId,
                                                   description,
                                                   quantity,
                                                   thirdParty.equals("true"),
                                                   imageURL);

            return AccessoryService.insert(newAccessory);

        } else {

            Accessory existingAccessory = AccessoryService.selectById(id);
            if (existingAccessory == null) {
                return "That accessory doesn't appear to exist";
            } else {

                existingAccessory.setCategoryId(categoryId);
                existingAccessory.setDescription(description);
                existingAccessory.setQuantity(quantity);
                existingAccessory.setThirdParty(thirdParty.equals("true"));
                existingAccessory.setImageURL(imageURL);
                return AccessoryService.update(existingAccessory);
            }
            
        }
    }

    @POST
    @Path("delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteAccessory(@PathParam("id") int id,
                                @CookieParam("sessionToken") Cookie sessionCookie) {

        String currentUsername = AdminService.validateSessionCookie(sessionCookie);
        if (currentUsername == null) return "Error: Invalid user session token";

        Logger.log("/console/delete - Console " + id);
        Accessory accessory = AccessoryService.selectById(id);
        if (accessory == null) {
            return "That accessory doesn't appear to exist";
        } else {

            int categoryCount = 0;
            CategoryService.selectAllInto(Category.categories);
            for (Category c: Category.categories) {
                if (c.getId() == accessory.getCategoryId()) {
                    categoryCount++;
                }
            }
            if (categoryCount <= 1) {
                CategoryService.deleteById(accessory.getCategoryId());
            }

            return AccessoryService.deleteById(id);
        }
    }

}
