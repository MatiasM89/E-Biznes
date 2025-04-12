import { useState } from 'react';
import { MDBInput, MDBBtn, MDBCardText } from 'mdb-react-ui-kit';

function Payments({ selectedProduct }) {
    const [quantity, setQuantity] = useState('1');

    const handleQuantityChange = e => {
        const value = e.target.value;
        if (value === '' || (/^\d+$/.test(value) && parseInt(value, 10) >= 1)) {
            setQuantity(value);
        }
    };

    const totalPrice = selectedProduct && quantity ? (parseInt(quantity, 10) * selectedProduct.price).toFixed(2) : '0.00';

    const handleSubmit = e => {
        e.preventDefault();
        if (!selectedProduct) {
            alert('Please select a product');
            return;
        }
        if (!quantity || parseInt(quantity, 10) < 1) {
            alert('Please enter a valid quantity');
            return;
        }
        const paymentData = {
            amount: totalPrice,
            quantity: quantity
        };
        fetch('/payments', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(paymentData),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Payment failed');
                }
            })
            .catch(error => console.error('Payment error:', error));
    };

    return (
        <div>
            <h2>Payments</h2>
            {selectedProduct ? (
                <div className="d-flex flex-column" style={{ maxWidth: '400px' }}>
                    <MDBInput
                        label="Quantity"
                        name="quantity"
                        value={quantity}
                        onChange={handleQuantityChange}
                        type="text"
                        className="mb-3"
                    />
                    <MDBCardText className="mb-3">Total Price: ${totalPrice}</MDBCardText>
                    <MDBBtn onClick={handleSubmit}>Buy</MDBBtn>
                </div>
            ) : (
                <p>Please select a product</p>
            )}
        </div>
    );
}

export default Payments;