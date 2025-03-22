package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.Product
import scala.collection.mutable.ListBuffer

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private var products = ListBuffer (
    Product(1, "battery"),
    Product(2, "charger"),
    Product(3, "phone"),
    Product(4, "case")
  )

  def printProduct(id: Int) = Action { implicit request: Request[AnyContent] =>
    products.find(_.id == id) match {
      case Some(product) =>
        println(product)
        Ok("Product found, output on console")
      case None =>
        println("Product not found")
        NotFound("Product not found")
    }
  }

  def delete(id: Int) = Action { implicit request: Request[AnyContent] =>
    products.find(_.id == id) match {
      case Some(product) =>
        products -= product
        println("Product deleted: " + product)
        Ok("Product deleted")
      case None =>
        println("Product not found")
        NotFound("Product not found")
    }
  }

  def create() = Action { implicit request: Request[AnyContent] =>
    val formData = request.body.asFormUrlEncoded.getOrElse(Map.empty)
    val id = formData.get("id").flatMap(_.headOption).map(_.toInt).getOrElse(-1)
    val content = formData.get("content").flatMap(_.headOption).getOrElse("")

    if (id == -1 || content.isEmpty) {
      BadRequest("Invalid ID or content")
    } else if (products.exists(_.id == id)) {
      Conflict("Product with this ID already exists")
    } else {
      val newProduct = Product(id, content)
      products += newProduct
      println("Product created" + newProduct)
      Ok("Product created, output on console")
    }
  }

  def update() = Action { implicit request: Request[AnyContent] =>
    val formData = request.body.asFormUrlEncoded.getOrElse(Map.empty)
    val id = formData.get("id").flatMap(_.headOption).map(_.toInt).getOrElse(-1)
    val content = formData.get("content").flatMap(_.headOption).getOrElse("")

    products.find(_.id == id) match {
      case Some(product) =>
        products -= product
        products += Product(id, content)
        println("Updated content of product with ID " + id)
        Ok("Product updated, output on console")
      case None =>
        println("Product not found")
        NotFound("Product not found")
    }
  }

  def printProducts() = Action { implicit request: Request[AnyContent] =>
    products.foreach(product => println("ID: " + product.id + " Content: " + product.content))
    Ok("Output on console")
  }
}
