package com.ajjpj.adiagram.render.shapes.lineend

import javafx.scene.canvas.GraphicsContext
import com.ajjpj.adiagram.geometry.{Angle, APoint}
import com.ajjpj.adiagram.render.base.LineStyle
import com.ajjpj.adiagram.geometry.transform.Translation
import javafx.scene.shape.{StrokeLineJoin, StrokeLineCap}
import com.ajjpj.adiagram.ui.{AScreenPos, Zoom}
import javafx.scene.paint.Color

/**
 * 
 * @author Thomas
 */
class EmptyRoundedTriangleLineEnd(arrowLineLength: Double = 30.0, arrowAngle: Double = Math.PI/6) extends ALineEnd {
  val sinA = Math.sin(arrowAngle)

  override def shortenLengthUnzoomed(style: LineStyle) = 28 // optimal length
  override def width(style: LineStyle, zoom: Zoom) = sinA * arrowLineLength + style.width(zoom)

  override def paint(gc: GraphicsContext, p: APoint, angle: Angle, style: LineStyle, t: Translation, zoom: Zoom) {
    style.applyTo(gc)

    gc.setLineWidth(style.width(zoom))
    gc.setLineCap(StrokeLineCap.ROUND)
    gc.setLineJoin(StrokeLineJoin.ROUND)
    val tipRaw = t(p) + (angle, style.width(Zoom.Identity)/2)
    val tip =  AScreenPos.fromModel(tipRaw, zoom)
    val end0 = AScreenPos.fromModel(tipRaw + (angle + arrowAngle, arrowLineLength), zoom)
    val end1 = AScreenPos.fromModel(tipRaw + (angle - arrowAngle, arrowLineLength), zoom)
    // save old color for later
    val oldColor = gc.getFill
    gc.setFill(Color.WHITE)
    gc.fillPolygon(Array(end0.x, tip.x, end1.x, end0.x), Array(end0.y, tip.y, end1.y, end0.y), 4);
    gc.strokePolyline(Array(end0.x, tip.x, end1.x, end0.x), Array(end0.y, tip.y, end1.y, end0.y), 4)
    gc.setFill(oldColor)
  }
}


