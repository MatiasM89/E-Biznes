package main

import (
"fmt"
"strconv"
"github.com/labstack/echo/v4"
)

type Product struct {
id int
content string
}

var products []Product

func main() {
    products = append(products, Product{id: 1, content: "Book"})
    products = append(products, Product{id: 2, content: "Pen"})

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
    id := len(products) + 1
    product := Product{id: id, content: content}
    products = append(products, product)
    fmt.Printf("Created product: ID=%d, Content=%s\n", product.id, product.content)
    return nil
}

func getProducts(c echo.Context) error {
    for _, product := range products {
        fmt.Printf("Product ID: %d, Content: %s\n", product.id, product.content)
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

        for _, p := range products {
            if p.id == id {
                fmt.Printf("Found product: ID=%d, Content=%s\n", p.id, p.content)
                return nil
            }
        }
        fmt.Println("Error: Product not found")
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
        if content == "" {
            fmt.Println("Error: No content provided")
            return nil
        }

        for i, p := range products {
            if p.id == id {
                products[i].content = content
                fmt.Printf("Updated product: ID=%d, Content=%s\n", id, content)
                return nil
            }
        }
        fmt.Println("Error: Product not found")
        return nil
}

func deleteProduct(c echo.Context) error {
    idStr := c.Param("id")
    id, err := strconv.Atoi(idStr)
    if err != nil {
        fmt.Println("Error: Invalid ID")
        return nil
    }

    for i, p := range products {
        if p.id == id {
            products = append(products[:i], products[i+1:]...)
            fmt.Printf("Deleted product: ID=%d\n", id)
            return nil
        }
    }
    fmt.Println("Error: Product not found")
    return nil
}


