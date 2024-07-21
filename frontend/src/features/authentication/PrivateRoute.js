import React, { useEffect } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { useUser } from '../../context/UserContext';
import AuthenticationApi from '../../services/AuthenticationApi';

const PrivateRoute = ({ children }) => {
    const isAuthenticated = !!localStorage.getItem('jwtToken');
    const { user, setUser } = useUser();
    const navigate = useNavigate();

    useEffect(() => {
        const setupUser = async () => {
            const token = localStorage.getItem('jwtToken');
            if (token && !user) {
                try {
                    const userResponse = await AuthenticationApi.currentUser(token);
                    setUser(userResponse.data);
                    navigate('/products');
                } catch (error) {
                    console.error('Failed to fetch current user', error);
                }
            }
        };
        setupUser();
    }, [navigate, user, setUser]);

    return isAuthenticated ? children : <Navigate to="/login" />;
};

export default PrivateRoute;
