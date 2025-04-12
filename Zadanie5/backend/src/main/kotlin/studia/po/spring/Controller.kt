package studia.po.spring

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:5173"])
class Controller {
    @GetMapping("/products")
    fun getProducts(): List<Product> {
        return listOf(
            Product(1, "Phone", 1000.0),
            Product(2, "Charger", 50.0),
            Product(3, "Cable", 10.0)
        )
    }

    @PostMapping("/payments")
    fun makePayment(@RequestBody payment: Payment): ResponseEntity<Void> {
        println("Quantity: ${payment.quantity}, Total Payment: $${payment.amount}")
        return ResponseEntity.noContent().build()
    }

    data class Product(val id: Int, val content: String, val price: Double)
    data class Payment(val amount: String, val quantity: String)
}