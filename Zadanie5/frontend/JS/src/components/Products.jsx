import { useState, useEffect } from 'react';
import { MDBRow, MDBCol, MDBCard, MDBCardBody, MDBCardTitle, MDBCardText, MDBRadio } from 'mdb-react-ui-kit';

function Products({ selectedProduct, setSelectedProduct }) {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        fetch('/products')
            .then(response => response.json())
            .then(data => setProducts(data))
            .catch(error => console.error('Error fetching products:', error));
    }, []);

    return (
        <div className="mb-5">
            <h2>Products</h2>
            <MDBRow>
                {products.map(product => (
                    <MDBCol md="4" key={product.id} className="mb-4">
                        <MDBCard>
                            <MDBCardBody>
                                <MDBCardTitle>{product.content}</MDBCardTitle>
                                <MDBCardText>Price: ${product.price.toFixed(2)}</MDBCardText>
                                <MDBRadio
                                    name="productSelection"
                                    id={`product-${product.id}`}
                                    label="Select"
                                    checked={selectedProduct?.id === product.id}
                                    onChange={() => setSelectedProduct(product)}
                                />
                            </MDBCardBody>
                        </MDBCard>
                    </MDBCol>
                ))}
            </MDBRow>
        </div>
    );
}

export default Products;