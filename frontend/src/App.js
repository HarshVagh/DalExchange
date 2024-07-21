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
import AddProduct from './features/add_product/AddProduct';
import UserModeration from "./features/user_moderation/UserModeration";
import ProductModeration from "./features/product_moderation/ProductModeration";
import ProfilePage from './features/profile_page/Profile';
import SoldItems from "./features/profile_page/SoldItems";
import SavedItems from "./features/profile_page/SavedItems";
import EditProfile from "./features/profile_page/EditProfile";
import Reviews from './features/profile_page/Reviews';
import PurchaseHistory from './features/profile_page/PurchaseHistory';
import { SearchFilterProvider } from './context/SearchFilterContext';
import UnauthenticatedRoute from './features/authentication/UnauthenticatedRoute';


function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<UnauthenticatedRoute> <LandingPage /> </UnauthenticatedRoute>} />

        <Route path="/login" element={<UnauthenticatedRoute> <Login /> </UnauthenticatedRoute>} />
        <Route path="/signup" element={<UnauthenticatedRoute> <Signup /> </UnauthenticatedRoute>} />
        <Route path="/forgot-password" element={<UnauthenticatedRoute> <ForgotPassword /> </UnauthenticatedRoute>} />
        <Route path="/verify-email" element={<UnauthenticatedRoute> <VerifyEmail /> </UnauthenticatedRoute>} />
        <Route path="/reset-password" element={<UnauthenticatedRoute> <ResetPassword /> </UnauthenticatedRoute>} />

        <Route path="/products" element={
          <PrivateRoute> 
            <SearchFilterProvider> 
              <ProductList />
            </SearchFilterProvider>
          </PrivateRoute> 
        } />
        <Route path="/products/add-product" element={<PrivateRoute><AddProduct /> </PrivateRoute>}/>
        <Route path="/products/:productId" element={<PrivateRoute> <ProductDetails/> </PrivateRoute> } />
        
        <Route path="/trade_requests" element={<PrivateRoute> <TradeRequests/> </PrivateRoute> } />
        
        <Route path="/admin-moderation/orders" element={<Layout><ViewOrders /></Layout>} />
        <Route path="/admin-moderation/orders/:orderId" element={<Layout><OrderDetails /></Layout>} />
        <Route path="/admin-moderation/users" element={<Layout><UserModeration /></Layout>} />
        <Route path="/admin-moderation/products" element={<Layout><ProductModeration /></Layout>} />
        
        <Route path="/profile/purchase-history" element={<PrivateRoute> <PurchaseHistory /> </PrivateRoute>} />
        <Route path="/profile" element={<PrivateRoute> <ProfilePage /> </PrivateRoute> } />
        <Route path="/profile/sold-items" element={<PrivateRoute> <SoldItems /> </PrivateRoute>} />
        <Route path="/profile/saved-items" element={<PrivateRoute> <SavedItems /> </PrivateRoute>} />
        <Route path="/profile/edit-profile" element={<PrivateRoute> <EditProfile /> </PrivateRoute>} />
        <Route path="/profile/reviews" element={<PrivateRoute> <Reviews /> </PrivateRoute>} />
        
        <Route path="/notifications" element={<PrivateRoute> <Notification/> </PrivateRoute>} /> 
      </Routes>
    </div>
  );
}

export default App;



