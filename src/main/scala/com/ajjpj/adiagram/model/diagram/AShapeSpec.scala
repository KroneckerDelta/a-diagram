package com.ajjpj.adiagram.model.diagram

import com.ajjpj.adiagram.render.shapes.AShape
import com.ajjpj.adiagram.geometry.{ADim, APoint}
import javafx.scene.canvas.Canvas
import com.ajjpj.adiagram.ui.fw.{JavaFxHelper, Digest}
import com.ajjpj.adiagram.render.base.{PartialImage, PartialImageWithShadow}
import com.ajjpj.adiagram.ui.presentation.{ZOrdered, DiagramRootContainer}
import com.ajjpj.adiagram.ui.Zoom
import java.util.UUID


/**
 * @author arno
 */
trait AShapeSpec extends ZOrdered {
  private var _changeCounter = 0
  def changeCounter = _changeCounter
  var z = 0
  var uuid = UUID.randomUUID

  //TODO Node.pickOnBounds (ignore transparent pixels for purposes of mouse event routing) --> does not appear to work?!

  def atomicUpdate(code: => Unit) = { //TODO add implicit parameter of a protected type to all update methods in subclasses --> prevent them from being called outside 'atomicUpdate'
    code
    _changeCounter += 1
  }

  def snapshot: ShapeSpecReRenderSnapshot
  def contains(p: APoint): Boolean = shape.bounds contains p //TODO shapeCanvas.contains(p.x - shapeCanvas.getLayoutX, p.y - shapeCanvas.getLayoutY) //TODO special JavaFX API to ignore transparent parts?

  def pos: APoint
  def pos_= (p: APoint)
  def shape: AShape

  /**
   * dimension must be compatible with 'resizeTo'
   */
  def boundsForResizing = shape.bounds
  def resizeBy(delta: ADim): Unit
  final def moveBy(delta: APoint) {
    doMoveBy(delta)
  }

  protected def doMoveBy(delta: APoint): Unit
}

trait ShapeSpecReRenderSnapshot
