package com.ajjpj.adiagram.geometry

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

/**
 * @author arno
 */
class ARectTest extends FunSuite with ShouldMatchers {
  val eps = .000000001

  test("ARect contains") {
    val r = ARect (APoint(10, 20), 30, 40)

    r contains (15.0, 25.0) should equal (true)

    r contains (10.0, 20.0) should equal (true)
    r contains (10.0, 60.0) should equal (true)
    r contains (40.0, 20.0) should equal (true)
    r contains (40.0, 60.0) should equal (true)

    r contains (  0.0,   0.0) should equal (false)
    r contains (100.0, 100.0) should equal (false)

    r contains ( 9.9, 20.0) should equal (false)
    r contains (10.0, 19.9) should equal (false)
    r contains ( 9.9, 60.0) should equal (false)
    r contains (10.0, 60.1) should equal (false)
    r contains (40.1, 20.0) should equal (false)
    r contains (40.0, 19.9) should equal (false)
    r contains (40.1, 60.0) should equal (false)
    r contains (40.0, 60.1) should equal (false)
  }

  test("ARect intersection") {
    val r = ARect(APoint(1.0, 1.0), ADim(2.0, 2.0))

    r.intersection(APoint(2.0, 2.0), APoint(2.0, 4.0)).x should be (2.0 plusOrMinus eps)
    r.intersection(APoint(2.0, 2.0), APoint(2.0, 4.0)).y should be (3.0 plusOrMinus eps)
                                                                                    
    r.intersection(APoint(2.0, 2.0), APoint(2.0, 0.0)).x should be (2.0 plusOrMinus eps)
    r.intersection(APoint(2.0, 2.0), APoint(2.0, 0.0)).y should be (1.0 plusOrMinus eps)
                                                                                    
    r.intersection(APoint(2.0, 2.0), APoint(0.0, 2.0)).x should be (1.0 plusOrMinus eps)
    r.intersection(APoint(2.0, 2.0), APoint(0.0, 2.0)).y should be (2.0 plusOrMinus eps)

    r.intersection(APoint(2.0, 2.0), APoint(4.0, 2.0)).x should be (3.0 plusOrMinus eps)
    r.intersection(APoint(2.0, 2.0), APoint(4.0, 2.0)).y should be (2.0 plusOrMinus eps)
  }
}
