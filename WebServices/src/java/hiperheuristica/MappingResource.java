/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiperheuristica;

import com.google.gson.Gson;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author marcel
 */
@Stateless
@Path("/mapping")
public class MappingResource {

    @EJB
    ProblemInstanceLocal problem;
    private static final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getVertices() {
        final PieceList bestFit = this.problem.getBestFit();
        Piece[] result = new Piece[bestFit.size()];
        int i = 0;
        for (Piece piece : bestFit) {
            result[i++] = piece;
        }

        return gson.toJson(result);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putConfiguration(@QueryParam("vertices") String verticesJson,
            @QueryParam("dimensions") String dimensionsJson) {
        Point[] _vertices = gson.fromJson(verticesJson, Point[].class);
        int[] _dimensions = gson.fromJson(dimensionsJson, int[].class);
        
        this.problem.addFigures(_vertices);        
        this.problem.setContainerDimensions(_dimensions[0], _dimensions[1]);
    }
}
