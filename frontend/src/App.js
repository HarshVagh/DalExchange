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
import UserModeration from "./features/user_moderation/UserModeration";
import ProductModeration from "./features/product_moderation/ProductModeration";


import ProfilePage from './features/profile_page/Profile';
import SoldItems from "./features/profile_page/SoldItems";
import SavedItems from "./features/profile_page/SavedItems";
import EditProfile from "./features/profile_page/EditProfile";
import Reviews from './features/profile_page/Reviews';
import PurchaseHistory from './features/profile_page/PurchaseHistory';


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
        <Route path="/admin-moderation/users" element={<Layout><UserModeration /></Layout>} />
        <Route path="/admin-moderation/products" element={<Layout><ProductModeration /></Layout>} />
        <Route path="/profile/purchase-history" element={<PurchaseHistory />} />
        <Route path="/profile" element={<PrivateRoute> <ProfilePage /> </PrivateRoute> } />
        <Route path="/profile/sold-items" element={<PrivateRoute> <SoldItems /> </PrivateRoute>} />
        <Route path="/profile/saved-items" element={<PrivateRoute> <SavedItems /> </PrivateRoute>} />
        <Route path="/profile/edit-profile" element={<PrivateRoute> <EditProfile /> </PrivateRoute>} />
        <Route path="/profile/reviews" element={<PrivateRoute> <Reviews /> </PrivateRoute>} />
        <Route path="/admin-moderation/orders" element={<Layout> <ViewOrders /> </Layout>} />
        <Route path="/admin-moderation/orders/:orderId" element={<Layout> <OrderDetails /> </Layout>} />
      </Routes>
    </div>
  );
}

export default App;



