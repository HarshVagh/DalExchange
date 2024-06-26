import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './features/authentication/Login'
import Signup from './features/authentication/Signup';
import VerifyEmail from './features/authentication/VerifyEmail';
import LandingPage from './features/authentication/LandingPage';

function App() {

  return (
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/login" element={<Login />} />
          <Route path="/verify-email" element={<VerifyEmail />} />
          <Route path="/landing-page" element={<LandingPage />} />
        </Routes>
  );
}

export default App;


/*import { Route, Routes } from 'react-router-dom';
import './App.css';
import ProductList from './features/priduct_list/ProductList';
import Login from './features/authentication-format/Login'
import Signup from './features/authentication-format/Signup';
import VerifyEmail from './features/authentication-format/VerifyEmail';
import ForgotPassword from './features/authentication-format/ForgotPassword';
import ResetPassword from './features/authentication-format/ResetPassword';
import LandingPage from './features/authentication-format/LandingPage';



function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="login" element={ <Login/> } />
        <Route path="signup" element={ <Signup/> } />
        <Route path="verify-email" element={ <VerifyEmail/> } />
        <Route path="forgot-password" element={ <ForgotPassword/> } />
        <Route path="reset-password" element={ <ResetPassword/> } />
        <Route path="/landing-page" element={<LandingPage />} />


        <Route path="products" element={ <ProductList/> } />
      </Routes>
    </div>
  );
}

export default App;*/
