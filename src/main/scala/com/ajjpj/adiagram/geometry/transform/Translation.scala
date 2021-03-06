package com.ajjpj.adiagram.geometry.transform

import com.ajjpj.adiagram.geometry.APoint


/**
 * @author arno
 */
class Translation(tr: APoint) extends AffineTransformation(tr, Matrix.UNIT)

object Translation {
  def apply(translation: APoint): Translation = new Translation(translation)
  def apply(x: Double, y: Double): Translation = new Translation(APoint(x, y))
}

