import { Route, Routes } from 'react-router-dom';
import './App.css';
import ProductList from './features/priduct_list/ProductList';

function App() {
  return (
    <div className="App">
      <Routes>
        {/* <Route path="/" element={ <Home/> } /> */}
        <Route path="products" element={ <ProductList/> } />
      </Routes>
    </div>
  );
}

export default App;
