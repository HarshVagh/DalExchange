import React, { useState } from 'react';
import axios from 'axios';
import Layout from './Layout';

const ForgotPassword = () => {
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8080/auth/forgot-password', null, {
                params: {
                    email: email
                }
            });
            setMessage('Password reset email sent.');
        } catch (error) {
            setMessage('Error sending password reset email.');
        }
    };

    return (
        <Layout>
            <h2>Reset Password</h2>
            <form onSubmit={handleSubmit}>
                <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                <button type="submit">Reset Password</button>
            </form>
            {message && <p>{message}</p>}
        </Layout>
    );
};

export default ForgotPassword;
