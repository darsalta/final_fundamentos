/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hiper;

import hiper.Piece;
import hiper.Point;

/**
 *
 * @author Marcel
 */
class PieceStub extends Piece {

  private int area;

  public PieceStub(int area) {
    super(new Point[]{Point.At(1, 1), Point.At(2, 2), Point.At(3, 3)});

    this.area = area;
  }

  public static Piece WithArea(int area) {
    return new PieceStub(area);
  }
  
  public static Piece Dimensions(int width, int height) {
    return new Piece(
            new Point[] { 
              Point.At(0, 0), Point.At(width, height), 
              Point.At(0, height), Point.At(width, 0)});
  }

  @Override
  public int getArea() {
    return this.area;
  }
}