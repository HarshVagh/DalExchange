import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';
import Layout from './Layout';

const ResetPassword = () => {
    const [newPassword, setNewPassword] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const token = params.get('token');
    const email = params.get('email');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
             await axios.post('http://localhost:8080/auth/reset-password',
                new URLSearchParams({ email, token, newPassword }),
                {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    }
                });
            setMessage('Password reset successfully.');
            navigate('/login'); // Redirect to login page
        } catch (error) {
            setMessage('Error resetting password.');
        }
    };

    return (
        <Layout>
            <h2>Enter New Password</h2>
            <form onSubmit={handleSubmit}>
                <input type="password" placeholder="New Password" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} required />
                <button type="submit">Reset Password</button>
            </form>
            {message && <p>{message}</p>}
        </Layout>
    );
};

export default ResetPassword;
