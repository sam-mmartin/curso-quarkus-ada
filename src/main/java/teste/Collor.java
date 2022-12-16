package teste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import teste.dto.CollorDto;

@Tag(name = "Collors")
@Path("/collors")
@Consumes(MediaType.APPLICATION_JSON)
public class Collor {

   private final Map<Integer, CollorDto> mapCollors = new HashMap<>();
   private static final Logger log = LoggerFactory.getLogger(Collor.class);

   @GET
   public Response listingCollors() {
      log.info("Listing collor");

      List<CollorDto> collotDtoList = new ArrayList<>(mapCollors.values());
      return Response.ok(collotDtoList).build();
   }

   @GET
   @Path("/{id}")
   public Response getCollor(@PathParam("id") int id) {
      log.info("Get a collor");

      CollorDto collor = mapCollors.get(id);

      if (Objects.isNull(collor)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(collor).build();
   }

   @POST
   public Response saveCollor(CollorDto collor) {
      log.info("Inserted a new collor {}");
      mapCollors.put(collor.getId(), collor);

      return Response.status(Response.Status.CREATED).build();
   }

   @PUT
   @Path("/{id}")
   public Response updateCollor(@PathParam("id") int id, CollorDto collor) {
      log.info("Updated a collor {}");

      if (!mapCollors.containsKey(id)) {

         return Response.status(Response.Status.NOT_FOUND).build();
      }

      mapCollors.put(id, collor);
      return Response.status(Response.Status.OK).build();
   }

   @DELETE
   @Path("/{id}")
   public Response removeCollor(@PathParam("id") int id) {
      log.info("Removed a collor {}");

      if (!mapCollors.containsKey(id)) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      mapCollors.remove(id);
      return Response.status(Response.Status.NO_CONTENT).build();
   }
}
