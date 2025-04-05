package main

import (
    "fmt"
    "strconv"
    "github.com/labstack/echo/v4"
    "gorm.io/driver/sqlite"
    "gorm.io/gorm"
)

type Product struct {
    ID      int    `gorm:"primaryKey" json:"id"`
    Content string `json:"content"`
}

var db *gorm.DB

func main() {
    db, _ = gorm.Open(sqlite.Open("products.db"), &gorm.Config{})
    db.AutoMigrate(&Product{})

    db.Create(&Product{ID: 1, Content: "Book"})
    db.Create(&Product{ID: 2, Content: "Pen"})

    e := echo.New()
    e.GET("/products", getProducts)
    e.GET("/products/:id", getProduct)
    e.POST("/create", createProduct)
    e.PUT("/update/:id", updateProduct)
    e.DELETE("/delete/:id", deleteProduct)

    e.Start(":8080")
}

func createProduct(c echo.Context) error {
    content := c.QueryParam("content")
    product := Product{Content: content}
    if err := db.Create(&product).Error; err != nil {
        fmt.Println("Error creating product:", err)
        return nil
    }

    fmt.Printf("Created product: ID=%d, Content=%s\n", product.ID, product.Content)
    return nil
}

func getProducts(c echo.Context) error {
    var products []Product
    if err := db.Find(&products).Error; err != nil {
        fmt.Println("Error fetching products:", err)
        return nil
    }

    for _, product := range products {
        fmt.Printf("Product ID: %d, Content: %s\n", product.ID, product.Content)
    }
    return nil
}

func getProduct(c echo.Context) error {
    idStr := c.Param("id")
    id, err := strconv.Atoi(idStr)
    if err != nil {
        fmt.Println("Error: Invalid ID")
        return nil
    }

    var product Product
    if err := db.First(&product, id).Error; err != nil {
        fmt.Println("Error: Product not found")
        return nil
    }

    fmt.Printf("Found product: ID=%d, Content=%s\n", product.ID, product.Content)
    return nil
}

func updateProduct(c echo.Context) error {
    idStr := c.Param("id")
    id, err := strconv.Atoi(idStr)
    if err != nil {
        fmt.Println("Error: Invalid ID")
        return nil
    }

    content := c.QueryParam("content")
    var product Product
    if err := db.First(&product, id).Error; err != nil {
        fmt.Println("Error: Product not found")
        return nil
    }

    product.Content = content
    if err := db.Save(&product).Error; err != nil {
        fmt.Println("Error updating product:", err)
        return nil
    }

    fmt.Printf("Updated product: ID=%d, Content=%s\n", product.ID, product.Content)
    return nil
}

func deleteProduct(c echo.Context) error {
    idStr := c.Param("id")
    id, err := strconv.Atoi(idStr)
    if err != nil {
        fmt.Println("Error: Invalid ID")
        return nil
    }

    var product Product
    if err := db.First(&product, id).Error; err != nil {
        fmt.Println("Error: Product not found")
        return nil
    }

    if err := db.Delete(&product).Error; err != nil {
        fmt.Println("Error deleting product:", err)
        return nil
    }

    fmt.Printf("Deleted product: ID=%d\n", id)
    return nil
}