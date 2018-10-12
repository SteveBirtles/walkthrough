package server.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Logger;
import server.models.Console;
import server.models.Manufacturer;
import server.models.services.ConsoleService;
import server.models.services.ManufacturerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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


}
