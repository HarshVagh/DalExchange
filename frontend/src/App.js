import { Route, Routes } from 'react-router-dom';
import './App.css';
import ProductList from './features/priduct_list/ProductList';
import Login from './features/authentication/Login'
import Signup from './features/authentication/Signup';

function App() {
  return (
    <div className="App">
      <Routes>
        {/* <Route path="/" element={ <Home/> } /> */}
        <Route path="login" element={ <Login/> } />
        <Route path="signup" element={ <Signup/> } />
        <Route path="products" element={ <ProductList/> } />
      </Routes>
    </div>
  );
}

export default App;
