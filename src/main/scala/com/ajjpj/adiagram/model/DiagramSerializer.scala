package com.ajjpj.adiagram.model

import com.ajjpj.adiagram.ui.presentation.ADiagramController
import com.ajjpj.adiagram.model.style._
import com.ajjpj.adiagram.model.diagram._
import com.ajjpj.adiagram.geometry.APoint


/**
 * @author arno
 */
private[model] class DiagramSerializer(ctrl: ADiagramController) {
  def toXml =
    <diagram-root>
      {styleRepoToXml(ctrl.styleRepository)}
      {selectedStylesToXml(ctrl.selectedStyles)}
      {diagramToXml(ctrl.diagram)}
    </diagram-root>


  //---------------------------------------------------------------

  def diagramToXml(diagram: ADiagram) =
    <diagram>
      {diagram.elements map elementToXml}
    </diagram>

  def elementToXml(el: AShapeSpec) = el match {
    case box: ABoxSpec => boxToXml(box)
    case line: ALineSpec => lineToXml(line)
    case text: ATextSpec => textToXml(text)
  }


  def boxToXml(box: ABoxSpec) = <box id={box.uuid.toString} x={box.pos.x.toString} y={box.pos.y.toString} width={box.dim.width.toString} height={box.dim.height.toString} text={box.text.map(scala.xml.Text(_))}
                                     fill={box.fillStyle.uuid.toString} shadow={box.shadowStyle.uuid.toString} textStyle={box.textStyle.uuid.toString} />
  def textToXml(text: ATextSpec) = <text id={text.uuid.toString} x={text.pos.x.toString} y={text.pos.y.toString} width={text.dim.width.toString} height={text.dim.height.toString} text={text.text} style={text.textStyle.uuid.toString}/>
  def lineToXml(line: ALineSpec) = <line id={line.uuid.toString}
                                         x0={psX(line.p0Source)} y0={psY(line.p0Source)} box0={psBox(line.p0Source)}
                                         x1={psX(line.p1Source)} y1={psY(line.p1Source)} box1={psBox(line.p1Source)}
                                         text={line.text.map (scala.xml.Text(_))} style={line.lineStyle.uuid.toString} textStyle={line.textStyle.uuid.toString} start={line.startLineEnd.uuid.toString} end={line.endLineEnd.uuid.toString}
                                         />

  def psX(ps: PosSource): Option[scala.xml.Text] = ps match {
    case LiteralPosSource(APoint(x, _)) => Some(scala.xml.Text(x.toString))
    case _ => None
  }
  def psY(ps: PosSource): Option[scala.xml.Text] = ps match {
    case LiteralPosSource(APoint(_, y)) => Some(scala.xml.Text(y.toString))
    case _ => None
  }
  def psBox(ps: PosSource): Option[scala.xml.Text] = ps match {
    case BoxPosSource(box) => Some(scala.xml.Text(box.uuid.toString))
    case _ => None
  }

    //---------------------------------------------------------------

  def styleRepoToXml(styleRepository: AStyleRepository) =
    <style-repo>
      {styleRepository.colors map colorToXml}
      {styleRepository.fillStyles map fillStyleToXml}
      {styleRepository.lineStyles map lineStyleToXml}
      {styleRepository.textStyles map textStyleToXml}
    </style-repo>


  def colorToXml(colorSpec: ColorSpec) = <color id={colorSpec.uuid.toString} name={colorSpec.name} red={colorSpec.color.getRed.toString} green={colorSpec.color.getGreen.toString} blue={colorSpec.color.getBlue.toString} alpha={colorSpec.color.getOpacity.toString} />

  def fillStyleToXml(fillStyle: FillStyleSpec) = fillStyle match {
    case solid: SolidFillSpec => solidFillStyleToXml(solid)
    case linear: SimpleLinearGradientSpec => linearFillStyleToXml(linear)
  }

  def solidFillStyleToXml(solid: SolidFillSpec) = <fill-solid id={solid.uuid.toString} name={solid.name} color={solid.colorSpec.uuid.toString} />
  def linearFillStyleToXml(linear: SimpleLinearGradientSpec) = <fill-linear id={linear.uuid.toString} name={linear.name} color0={linear.colorSpec0.uuid.toString} color1={linear.colorSpec1.uuid.toString}/>

  def lineStyleToXml(lineStyle: LineStyleSpec) = <line-style id={lineStyle.uuid.toString} width={lineStyle.width.toString} color={lineStyle.colorSpec.uuid.toString} />

  def textStyleToXml(textStyle: TextStyleSpec) = textStyle match {
    case simple: SimpleTextStyleSpec => simpleTextStyleToXml(simple)
  }
  def simpleTextStyleToXml(textStyle: SimpleTextStyleSpec) = <simple-text-style id={textStyle.uuid.toString} name={textStyle.name} font-size={textStyle.fontSizePixels.toString}  />

  //---------------------------------------------------------------

  def selectedStylesToXml(sel: SelectedStyles) = <selected-styles fill={sel.fillStyle.uuid.toString} shadow={sel.shadowStyle.uuid.toString} text={sel.textStyle.uuid.toString} line={sel.lineStyle.uuid.toString} lineText={sel.lineTextStyle.uuid.toString}
                                                                  startEnd={sel.startLineEnd.uuid.toString} endEnd={sel.endLineEnd.uuid.toString} />
}
