package sri

import io.scalajs.JSON
import io.scalajs.nodejs.console
import react._

import scala.scalajs.js
import scala.util.Random

object JsObjectConvertibleTest extends BaseSuite {
  private def genTestClass() =
    TestClass(Random.genAlphanumeric(10), Random.nextInt(), () => println("callback!"))

  test("to js object") {
    val testClass = this.genTestClass()
    val json = JSON.stringify(testClass.toJsObject)
    val expectedJson = s"""{"f1":"${testClass.f1}","v1":${testClass.v1}}"""
    expect(json).toBe(expectedJson)
    console.log(JSON.stringify(testClass.toJsObject))
  }

  test("from js object") {
    val testClass = this.genTestClass()
    val obj = js.Dynamic.literal(
      f1 = testClass.f1,
      v1 = testClass.v1,
      callback = testClass.callback
    )
    val parsed = obj.toScalaClass[TestClass]
    expect(parsed).toEqual(testClass)
  }

  test("from js object negatively") {
    val testClass = this.genTestClass()
    val obj = js.Dynamic.literal(
      v1 = testClass.v1
    )
    expectFunc(() => obj.toScalaClass[TestClass])
      .toThrow("Cannot convert js object to scala class, 'f1' is undefined")
  }

  test("transitivity") {
    val testClass = this.genTestClass()
    console.log(testClass.toJsObject)
    expect(testClass.toJsObject.toScalaClass[TestClass]).toEqual(testClass)
  }

  private case class TestClass(f1: String, v1: Int, callback: js.Function0[Unit])
}
