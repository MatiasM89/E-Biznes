package studia.po.spring

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class Controller {
    private val productList = mutableListOf(
        Product(1, "Phone"),
        Product(2, "Charger"),
        Product(3, "Pen")
    )

    @GetMapping("/products")
    fun getProducts(): List<Product> {
        return productList
    }

    @GetMapping("/products/{id}")
    fun getProduct(@PathVariable id: Int): Product {
        return productList.find { it.id == id }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
    }

    @DeleteMapping("/products/delete/{id}")
    fun deleteProduct(@PathVariable id: Int) {
        val removed = productList.removeIf { it.id == id }
        if (!removed) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
        }
    }

    @PostMapping("/reset")
    fun resetProducts() {
        productList.clear()
        productList.addAll(listOf(
            Product(1, "Phone"),
            Product(2, "Charger"),
            Product(3, "Pen")
        ))
    }
}