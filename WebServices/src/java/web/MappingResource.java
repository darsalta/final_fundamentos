/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

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
import hiper.PieceList;
import hiper.Piece;
import hiper.Point;
import hiper.Direction;

/**
 *
 * @author marcel
 */
@Stateless
@Path("/mapping")
public class MappingResource {

  private static final Gson gson = new Gson();
  @EJB
  ProblemInstanceLocal problem;

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
          @QueryParam("dimensions") String dimensionsJson) throws Exception {
    Piece[] pieces = gson.fromJson(verticesJson, FourSidedPiece[].class);
    for (Piece piece : pieces) {
      ((FourSidedPiece) piece).update();
    }

    int[] _dimensions = gson.fromJson(dimensionsJson, int[].class);
    this.problem.addPieces(pieces);
    this.problem.setContainerDimensions(_dimensions[0], _dimensions[1]);
  }

  class FourSidedPiece extends Piece {

    public FourSidedPiece() {
      super(Point.At(0, 0),
              Point.At(0, 0),
              Point.At(0, 0),
              Point.At(0, 0));
    }

    public void update() throws Exception {
      this.moveDistance(0, Direction.UP);
    }
  }
}
