import { Route, Routes } from 'react-router-dom';
import './App.css';
import ProductList from './features/product_list/ProductList';
import TradeRequests from './features/trade_requests/TradeRequests';
import ProductDetails from './features/product_details/ProductDetails';
import Login from './features/authentication/Login';
import Signup from './features/authentication/Signup';
import VerifyEmail from './features/authentication/VerifyEmail';
import LandingPage from './features/authentication/LandingPage';
import ForgotPassword from './features/authentication/ForgotPassword';
import ResetPassword from './features/authentication/ResetPassword';
import PrivateRoute from './features/authentication/PrivateRoute';
import ViewOrders from './features/order_moderation/ViewOrders';
import OrderDetails from './features/order_moderation/OrderDetails';
import Layout from './features/order_moderation/Layout'; 
import Notification from './features/notification/Notification';


function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/verify-email" element={<VerifyEmail />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route path="/products" element={<PrivateRoute> <ProductList /> </PrivateRoute> } />
        <Route path="/products/:productId" element={<PrivateRoute> <ProductDetails/> </PrivateRoute> } />
        <Route path="/trade_requests" element={<PrivateRoute> <TradeRequests/> </PrivateRoute> } />
        <Route path="/admin-moderation/orders" element={<Layout><ViewOrders /></Layout>} /> 
        <Route path="/admin-moderation/orders/:orderId" element={<Layout><OrderDetails /></Layout>} /> 
        <Route path="/notifications" element={<PrivateRoute> <Notification/> </PrivateRoute>} /> 
      </Routes>
    </div>
  );
}

export default App;



