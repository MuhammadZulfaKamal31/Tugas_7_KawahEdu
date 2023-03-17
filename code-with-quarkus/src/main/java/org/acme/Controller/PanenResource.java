package org.acme.Controller;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.acme.Model.Kebun;
import org.acme.Service.PanenService;

@Path("panen")
public class PanenResource {

    @Inject
    PanenService panenService;

    @GET
    public List<Kebun> listPanen() {
        return panenService.getKebun();
    }

    @GET
    @Path("/{id}")
    public Kebun get(UUID id) {
        return panenService.getKomoditasByUUID(id);
    }

    @POST
    public List<Kebun> postPanent(Kebun kebun) {
        return panenService.createKebun(kebun);
    }

    @PUT
    @Path("{id}")
    public Kebun editPanen(@PathParam("id") UUID id, Kebun kebun) {
        return panenService.updateNamaKebun(id, kebun);
    }

    @DELETE
    @Path("{id}")
    public List<Kebun> deletePanen(@PathParam("id") UUID id) {
        return panenService.deleteKebun(id);
    }
}