import { Route, Routes } from 'react-router-dom';
import './App.css';
import ProductList from './features/product_list/ProductList';
import TradeRequests from './features/trade_requests/TradeRequests';
import ProductDetails from './features/product_details/ProductDetails';

function App() {
  return (
    <div className="App">
      <Routes>
        {/* <Route path="/" element={ <Home/> } /> */}
        <Route path="products" element={ <ProductList/> } />
        <Route path="products/:productId" element={ <ProductDetails/> } />
        <Route path="trade_requests" element={ <TradeRequests/> } />
      </Routes>
    </div>
  );
}

export default App;
