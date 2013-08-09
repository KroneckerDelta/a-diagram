package com.ajjpj.adiagram.model.style

import javafx.scene.paint._
import com.ajjpj.adiagram.render.base.{TextStyle, LineStyle, ShadowStyle, FillStyle}
import javafx.scene.effect.BlurType
import javafx.geometry.VPos
import javafx.scene.text.TextAlignment
import com.ajjpj.adiagram.util.WithUuid
import java.util.UUID


/**
 * @author arno
 */
class ColorSpec extends WithUuid {
  def this(name: String, color: Color) = {this(); this.name = name; this.color = color}
  var name: String = _
  var color: Color = _
}


trait FillStyleSpec extends WithUuid {
  def name: String
  def style = new FillStyle(paint)
  def paint: Paint
}

class SolidFillSpec extends FillStyleSpec {
  override def name = "Solid " + colorSpec.name
  var colorSpec: ColorSpec = _
  override def paint = colorSpec.color
}

class SimpleLinearGradientSpec extends FillStyleSpec {
  def this(color0: ColorSpec, color1: ColorSpec) = { this(); colorSpec0 = color0; colorSpec1 = color1 }

  var colorSpec0: ColorSpec = _
  var colorSpec1: ColorSpec = _

  override def name = "Linear " + colorSpec0.name + " to " + colorSpec1.name
  override def paint = new LinearGradient(0.3, 0, .7, 1, true, CycleMethod.NO_CYCLE, new Stop(0, colorSpec0.color), new Stop(1, colorSpec1.color))
}


trait ShadowStyleSpec extends WithUuid {
  def name: String
  def style: ShadowStyle
}

object SimpleShadowSpec extends ShadowStyleSpec {
  override def uuid = UUID.fromString("c8845d04-d210-4f7e-889d-e5c0eb7defc6")

  override def name = "Simple Shadow"
  override def style = ShadowStyle(6, 6, 16, BlurType.GAUSSIAN, Color.color(.5, .5, .5))
}

object NoShadowSpec extends ShadowStyleSpec {
  override def uuid = UUID.fromString("c8845d04-d210-4f7e-889d-e5c0eb7defc7")

  override def name = "No Shadow"
  override def style = ShadowStyle(0, 0, 0, BlurType.ONE_PASS_BOX, Color.TRANSPARENT)
}


class LineStyleSpec extends WithUuid {
  def this(colorSpec: ColorSpec, width: Double) = {this(); this.colorSpec = colorSpec; this.width = width}
  var colorSpec: ColorSpec = _
  var width: Double = _

  def name = "Width " + width + ", " + colorSpec.name
  def style = LineStyle(colorSpec.color, width)
}


trait TextStyleSpec extends WithUuid {
  def name: String
  def style: TextStyle
}

class SimpleTextStyleSpec extends TextStyleSpec {
  def this(name: String, fontSize: Int) = {this(); this.name = name; this.fontSizePixels = fontSize}

  var name: String = _
  var fontSizePixels = 72
  var textAlignment = TextAlignment.CENTER
  var vPos = VPos.CENTER

  def style = TextStyle(fontSizePixels, textAlignment, vPos)
}
