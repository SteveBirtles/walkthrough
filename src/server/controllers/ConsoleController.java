package server.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Logger;
import server.models.Console;
import server.models.Manufacturer;
import server.models.services.AdminService;
import server.models.services.ConsoleService;
import server.models.services.ManufacturerService;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

@SuppressWarnings("unchecked")
@Path("console/")
public class ConsoleController {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listConsoles() {

        Logger.log("/console/list - Getting all consoles from database");
        String status = ConsoleService.selectAllInto(Console.consoles);

        if (status.equals("OK")) {

            ManufacturerService.selectAllInto(Manufacturer.manufacturers);

            JSONArray consoleList = new JSONArray();
            for (Console c: Console.consoles) {

                JSONObject jc = c.toJSON();

                for (Manufacturer m : Manufacturer.manufacturers) {
                    if (m.getId() == c.getManufacturerid()) {
                        jc.put("manufacturer", m.getName());
                        break;
                    }
                }

                if (c.getImageURL().equals("")) {
                    c.setImageURL("/client/img/none.png");
                }

                consoleList.add(jc);

            }

            return consoleList.toString();

        } else {
            JSONObject response = new JSONObject();
            response.put("error", status);
            return response.toString();
        }

    }

    @GET
    @Path("get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getConsole(@PathParam("id") int id) {

        Logger.log("/console/get/"+ id + " - Getting console details from database");

        Console c = ConsoleService.selectById(id);
        if (c != null) {

            Manufacturer m = ManufacturerService.selectById(c.getManufacturerid());

            JSONObject cj = c.toJSON();

            cj.put("manufacturer", m.getName());

            return cj.toString();

        } else {

            return "{'error': 'Can't find console with id " + id + "'}";

        }

    }


    @POST
    @Path("save/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String saveConsole(  @PathParam("id") int id,
                             @FormParam("name") String name,
                             @FormParam("manufacturer") String manufacturer,
                             @FormParam("mediaType") String mediaType,
                             @FormParam("year") String year,
                             @FormParam("sales") String sales,
                             @DefaultValue("false") @FormParam("handheld") String handheld,
                             @FormParam("imageURL") String imageURL,
                             @FormParam("notes") String notes,
                             @CookieParam("sessionToken") Cookie sessionCookie) {

        String currentUsername = AdminService.validateSessionCookie(sessionCookie);
        if (currentUsername == null) return "Error: Invalid user session token";

        int manufacturerId = 0;
        ManufacturerService.selectAllInto(Manufacturer.manufacturers);
        for (Manufacturer m : Manufacturer.manufacturers) {
            if (m.getName().equals(manufacturer)) {
                manufacturerId = m.getId();
            }
        }
        if (manufacturerId == 0) {
            int mId = Manufacturer.nextId();
            Manufacturer newManufacturer = new Manufacturer(mId, manufacturer);
            ManufacturerService.insert(newManufacturer);
            manufacturerId = mId;
        }

        if (id == -1) {
            ConsoleService.selectAllInto(Console.consoles);
            id = Console.nextId();
            Console newConsole = new Console(id, manufacturerId, name, mediaType, year, sales, handheld.equals("true"), imageURL, notes);

            return ConsoleService.insert(newConsole);
        } else {
            Console existingConsole = ConsoleService.selectById(id);
            if (existingConsole == null) {
                return "That console doesn't appear to exist";
            } else {

                existingConsole.setName(name);
                existingConsole.setMediaType(mediaType);
                existingConsole.setManufacturerid(manufacturerId);
                existingConsole.setYear(year);
                existingConsole.setSales(sales);
                existingConsole.setHandheld(handheld.equals("true"));
                existingConsole.setImageURL(imageURL);
                existingConsole.setNotes(notes);
                return ConsoleService.update(existingConsole);
            }
        }
    }

    @POST
    @Path("delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteConsole(@PathParam("id") int id,
                                @CookieParam("sessionToken") Cookie sessionCookie) {

        String currentUsername = AdminService.validateSessionCookie(sessionCookie);
        if (currentUsername == null) return "Error: Invalid user session token";

        Logger.log("/console/delete - Console " + id);
        Console console = ConsoleService.selectById(id);
        if (console == null) {
            return "That console doesn't appear to exist";
        } else {

            int manufacturerCount = 0;
            ManufacturerService.selectAllInto(Manufacturer.manufacturers);
            for (Manufacturer m: Manufacturer.manufacturers) {
                if (m.getId() == console.getManufacturerid()) {
                    manufacturerCount++;
                }
            }
            if (manufacturerCount <= 1) {
                ManufacturerService.deleteById(console.getManufacturerid());
            }

            return ConsoleService.deleteById(id);
        }
    }

}
