import { useState } from 'react';
import { MDBContainer } from 'mdb-react-ui-kit';
import Products from './components/Products';
import Payments from './components/Payments';

function App() {
    const [selectedProduct, setSelectedProduct] = useState(null);

    return (
        <MDBContainer className="py-5">
            <Products selectedProduct={selectedProduct} setSelectedProduct={setSelectedProduct} />
            <Payments selectedProduct={selectedProduct} />
        </MDBContainer>
    );
}

export default App;