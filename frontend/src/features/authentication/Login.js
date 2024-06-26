import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Layout from './Layout';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [token, setToken] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/auth/login', { email, password });
            setMessage('Login successful.');
            const { token } = response.data;
            setToken(token);
            navigate('/landing-page');
        } catch (error) {
            setMessage('Invalid credentials.');
        }
    };

    return (
        <Layout>
            <h2>Login</h2>
            <form onSubmit={handleSubmit} className="login-form">
                <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                <div className="forgot-password">
                    <Link to="/forgot-password">Forgot Password?</Link>
                </div>
                <button type="submit" className="login-button">Login</button>
            </form>
            {message && <p>{message}</p>}
        </Layout>

    );
};

export default Login;


