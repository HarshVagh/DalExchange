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
import ProfilePage from './features/Profile_page/Profile';
import SoldItems from "./features/Profile_page/SoldItems";
import SavedItems from "./features/Profile_page/SavedItems";
import EditProfile from "./features/Profile_page/EditProfile";
import Reviews from './features/Profile_page/Reviews';
import toast, { Toaster } from 'react-hot-toast';


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
        <Route path="/profile/purchase-history" element={<PurchaseHistory />} />
        <Route path="profile" element={ <ProfilePage/> } />
        <Route path="/profile/sold-items" element={<SoldItems />} />
        <Route path="/profile/saved-items" element={<SavedItems />} />
        <Route path="/profile/edit-profile" element={<EditProfile />} />
        <Route path="/profile/reviews" element={<Reviews />} />
      </Routes>
    </div>
  );
}

export default App;



