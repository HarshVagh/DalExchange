import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './features/authentication/Login';
import Signup from './features/authentication/Signup';
import VerifyEmail from './features/authentication/VerifyEmail';
import LandingPage from './features/authentication/LandingPage';
import ForgotPassword from './features/authentication/ForgotPassword';
import ResetPassword from './features/authentication/ResetPassword';
import PrivateRoute from './features/authentication/PrivateRoute';
import './features/authentication/global.css';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/signup" element={<Signup />} />
                <Route path="/login" element={<Login />} />
                <Route path="/forgot-password" element={<ForgotPassword />} />
                <Route path="/verify-email" element={<VerifyEmail />} />
                <Route path="/reset-password" element={<ResetPassword />} />
                <Route
                    path="/landing-page"
                    element={
                        <PrivateRoute>
                            <LandingPage />
                        </PrivateRoute>
                    }
                />
            </Routes>
        </Router>
    );
}

export default App;
