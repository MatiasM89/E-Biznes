package studia.po.spring

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.boot.test.context.SpringBootTest
import java.net.HttpURLConnection
import java.net.URL
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ControllerTests {
    private lateinit var driver: WebDriver
    private val baseUrl = "http://localhost:8080"

    /*
    Tests are based on my controller from Projektowanie Obiektowe
     */

    @BeforeEach
    fun setup() {
        WebDriverManager.chromedriver().setup()
        val options = ChromeOptions()
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080")
        driver = ChromeDriver(options)

        val conn = URL("$baseUrl/reset").openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.responseCode
        conn.disconnect()
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    private fun getResponseBody(url: String): String {
        driver.get(url)
        return driver.findElement(By.tagName("body")).text
    }

    private fun makeDeleteRequest(url: String): Pair<Int, String> {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "DELETE"

        val statusCode = connection.responseCode
        val inputStream = if (statusCode >= 400) connection.errorStream else connection.inputStream
        val responseBody = inputStream?.bufferedReader()?.use { it.readText() } ?: ""

        return Pair(statusCode, responseBody)
    }

    private fun jsonContainsProduct(json: String, productName: String): Boolean {
        val pattern = "\"content\"\\s*:\\s*\"$productName\""
        return pattern.toRegex().containsMatchIn(json)
    }

    @Test
    fun `getProducts returns all products`() {
        val response = getResponseBody("$baseUrl/products")
        assertTrue(jsonContainsProduct(response, "Phone"))
        assertTrue(jsonContainsProduct(response, "Charger"))
        assertTrue(jsonContainsProduct(response, "Pen"))
    }

    @Test
    fun `getProducts returns three products`() {
        val response = getResponseBody("$baseUrl/products")
        val products = response.split("},").filter { it.isNotBlank() }
        assertEquals(3, products.size)
    }

    @Test
    fun `getProduct by ID 1 returns Phone`() {
        val response = getResponseBody("$baseUrl/products/1")
        assertTrue(jsonContainsProduct(response, "Phone"))
    }

    @Test
    fun `getProduct by ID 2 returns Charger`() {
        val response = getResponseBody("$baseUrl/products/2")
        assertTrue(jsonContainsProduct(response, "Charger"))
    }

    @Test
    fun `getProduct by ID 3 returns Pen`() {
        val response = getResponseBody("$baseUrl/products/3")
        assertTrue(jsonContainsProduct(response, "Pen"))
    }

    @Test
    fun `getProduct by invalid ID returns 404`() {
        val response = getResponseBody("$baseUrl/products/999")
        assertTrue(response.contains("404") || response.contains("Not Found"))
    }

    @Test
    fun `getProduct by negative ID returns 404`() {
        val response = getResponseBody("$baseUrl/products/-1")
        assertTrue(response.contains("404") || response.contains("Not Found"))
    }

    @Test
    fun `getProduct by zero ID returns 404`() {
        val response = getResponseBody("$baseUrl/products/0")
        assertTrue(response.contains("404") || response.contains("Not Found"))
    }

    @Test
    fun `getProduct by non-numeric ID returns 400`() {
        val response = getResponseBody("$baseUrl/products/abc")
        assertTrue(response.contains("400") || response.contains("Bad Request"))
    }

    @Test
    fun `deleteProduct by ID 1 removes product`() {
        makeDeleteRequest("$baseUrl/products/delete/1")
        val response = getResponseBody("$baseUrl/products")
        assertTrue(!jsonContainsProduct(response, "Phone"))
    }

    @Test
    fun `deleteProduct by ID 2 removes product`() {
        makeDeleteRequest("$baseUrl/products/delete/2")
        val response = getResponseBody("$baseUrl/products")
        assertTrue(!jsonContainsProduct(response, "Charger"))
    }

    @Test
    fun `deleteProduct by ID 3 removes product`() {
        makeDeleteRequest("$baseUrl/products/delete/3")
        val response = getResponseBody("$baseUrl/products")
        assertTrue(!jsonContainsProduct(response, "Pen"))
    }

    @Test
    fun `deleteProduct by invalid ID returns 404`() {
        val (statusCode, responseBody) = makeDeleteRequest("$baseUrl/products/delete/999")
        assertEquals(404, statusCode)
        assertTrue(responseBody.contains("Not Found"))
    }

    @Test
    fun `deleteProduct by negative ID returns 404`() {
        val (statusCode, responseBody) = makeDeleteRequest("$baseUrl/products/delete/-1")
        assertEquals(404, statusCode)
        assertTrue(responseBody.contains("Not Found"))
    }

    @Test
    fun `deleteProduct by zero ID returns 404`() {
        val (statusCode, responseBody) = makeDeleteRequest("$baseUrl/products/delete/0")
        assertEquals(404, statusCode)
        assertTrue(responseBody.contains("Not Found"))
    }

    @Test
    fun `deleteProduct by non-numeric ID returns 400`() {
        val (statusCode, responseBody) = makeDeleteRequest("$baseUrl/products/delete/999")
        assertEquals(404, statusCode)
        assertTrue(responseBody.contains("Not Found"))
    }

    @Test
    fun `getProducts after deleting ID 1 returns two products`() {
        makeDeleteRequest("$baseUrl/products/delete/1")
        val response = getResponseBody("$baseUrl/products")
        val products = response.split("},").filter { it.isNotBlank() }
        assertEquals(2, products.size)
    }


    @Test
    fun `deleteProduct twice returns 404 on second attempt`() {
        makeDeleteRequest("$baseUrl/products/delete/1")
        val (statusCode, _) = makeDeleteRequest("$baseUrl/products/delete/1")
        assertEquals(404, statusCode)
    }

    @Test
    fun `getProduct after deletion returns 404`() {
        makeDeleteRequest("$baseUrl/products/delete/1")
        val response = getResponseBody("$baseUrl/products/1")
        assertTrue(response.contains("404") || response.contains("Not Found"))
    }

    @Test
    fun `getProducts returns empty list after deleting all products`() {
        makeDeleteRequest("$baseUrl/products/delete/1")
        makeDeleteRequest("$baseUrl/products/delete/2")
        makeDeleteRequest("$baseUrl/products/delete/3")
        val response = getResponseBody("$baseUrl/products")
        assertEquals("[]", response.trim())
    }
}