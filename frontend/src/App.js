import { Route, Routes } from 'react-router-dom';
import './App.css';
import ProductList from './features/product_list/ProductList';
import ProductDetails from './features/product_details/ProductDetails';

function App() {
  return (
    <div className="App">
      <Routes>
        {/* <Route path="/" element={ <Home/> } /> */}
        <Route path="products" element={ <ProductList/> } />
        <Route path="product_details" element={ <ProductDetails/> } />
      </Routes>
    </div>
  );
}

export default App;
