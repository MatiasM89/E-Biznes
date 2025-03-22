package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.Product

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val products = List (
    Product(1, "battery"),
    Product(2, "charger"),
    Product(3, "phone"),
    Product(4, "case")
  )

  def printProducts() = Action { implicit request: Request[AnyContent] =>
    products.foreach(product => println("ID: " + product.id + " Content: " + product.content))
    Ok("Output on console")
  }
}
