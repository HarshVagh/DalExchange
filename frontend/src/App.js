import { Route, Routes } from 'react-router-dom';
import './App.css';
import ProductList from './features/product_list/ProductList';
import ProfilePage from './features/Profile_page/Profile';
import ProductDetails from './features/product_details/ProductDetails';
import PurchaseHistory from "./features/Profile_page/PurchaseHistory";
import SoldItems from "./features/Profile_page/SoldItems";
import SavedItems from "./features/Profile_page/SavedItems";
import EditProfile from "./features/Profile_page/EditProfile";
import Reviews from './features/Profile_page/Reviews';
import toast, { Toaster } from 'react-hot-toast';


function App() {
  return (
    <div className="App">
      <Routes>
        {/* <Route path="/" element={ <Home/> } /> */}
        <Route path="products" element={ <ProductList/> } />
        <Route path="profile" element={ <ProfilePage/> } />
        <Route path="" element={ <ProductDetails/> } />
        <Route path="/profile/purchase-history" element={<PurchaseHistory />} />
        <Route path="/profile/sold-items" element={<SoldItems />} />
        <Route path="/profile/saved-items" element={<SavedItems />} />
        <Route path="/profile/edit-profile" element={<EditProfile />} />
        <Route path="/profile/reviews" element={<Reviews />} />
      </Routes>
    </div>
  );
}

export default App;
