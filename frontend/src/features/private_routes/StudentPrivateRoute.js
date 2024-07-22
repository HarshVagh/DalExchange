import React, { useEffect } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { useUser } from '../../context/UserContext';
import AuthenticationApi from '../../services/AuthenticationApi';

const StudentPrivateRoute = ({ children }) => {
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
                } catch (error) {
                    console.error('Failed to fetch current user', error);
                }
            }
        };
        setupUser();
    }, [navigate, user, setUser]);

    if (!isAuthenticated) return <Navigate to="/login" />;

    if (user && user.role !== 'student') return <Navigate to="/not-authorized" />;

    return children;
};

export default StudentPrivateRoute;
