package sri.react.testutils

import sri.react.{BaseComponent, ReactElement}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("react-dom/test-utils", JSImport.Namespace)
private object ReactTestUtilsJS extends js.Object {
  def renderIntoDocument(element: ReactElement): ReactElement = js.native

  @deprecated("We recommend using shallow rendering or jest.mock() instead", "react")
  def mockComponent(componentClass: js.Any, mockTagName: String): ReactElement = js.native

  def isElement(element: ReactElement): Boolean = js.native

  def isElementOfType(element: ReactElement,
                      componentClass: js.Any): Boolean = js.native

  def isDOMComponent(instance: js.Object): Boolean = js.native

  def isCompositeComponent(instance: js.Object): Boolean = js.native

  val Simulate: Simulate = js.native
}

object ReactTestUtils {
  def renderIntoDocument(element: ReactElement): ReactElement =
    ReactTestUtilsJS.renderIntoDocument(element)

  def isElement(element: ReactElement): Boolean = ReactTestUtilsJS.isElement(element)

  def isElementOfType[C <: BaseComponent](element: ReactElement,
                                      componentClass: Class[C]): Boolean = {
    val ctor = js.constructorOf[C#InnerComponent[C]]
    //Fixed in 1.0.0-M2. No need to provide clazz flag.
    ReactTestUtilsJS.isElementOfType(element, ctor) && element.props.clazz.contains(componentClass)
  }

  def isDOMComponent(instance: js.Object): Boolean = ReactTestUtilsJS.isDOMComponent(instance)

  val Simulate: Simulate = ReactTestUtilsJS.Simulate
}