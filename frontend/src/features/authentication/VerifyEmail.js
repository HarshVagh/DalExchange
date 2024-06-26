import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Layout from './Layout';

const VerifyEmail = () => {
    const [email, setEmail] = useState('');
    const [code, setCode] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/auth/verify', { email, code });
            setMessage('User verified successfully.');
            navigate('/landing-page');
        } catch (error) {
            if (error.response) {
                setMessage(error.response.data);
            } else {
                setMessage('Error verifying user.');
            }
        }
    };

    return (
        <Layout>
            <h2>Verify Email</h2>
            <form onSubmit={handleSubmit}>
                <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                <input type="text" placeholder="Verification Code" value={code} onChange={(e) => setCode(e.target.value)} required />
                <button type="submit">Verify</button>
            </form>
            {message && <p>{message}</p>}
        </Layout>
    );
};

export default VerifyEmail;

