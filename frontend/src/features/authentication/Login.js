import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
//import axios from 'axios';
//import Layout from './Layout';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            //const response = await axios.post('http://localhost:8080/auth/login', { email, password });
            setMessage('Login successful.');
            navigate('/landing-page');
        } catch (error) {
            setMessage('Invalid credentials.');
        }
    };

    return (
        /*<Layout>
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
        </Layout>*/
        <div className="flex items-center justify-center min-h-[100dvh] px-4 sm:px-6 lg:px-8">
      <div className="w-full max-w-md space-y-6">
        <div className="text-center">
          <h2 className="text-3xl font-bold tracking-tight">Sign in to your account</h2>
          <p className="mt-2 text-sm text-gray-500 dark:text-gray-400">
            Don&apos;t have an account?{" "}
            <Link href="#" className="font-medium text-gray-900 hover:underline dark:text-gray-50" prefetch={false}>
              Register
            </Link>
          </p>
        </div>
        <form className="space-y-4">
        <div class="mb-6">
    <label for="default-input" class="block mb-2 text-sm font-medium text-gray-900 ">Default input</label>
    <input type="text" id="default-input" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "/>
</div>
          <div>
            <label htmlFor="email">Email address</label>
            <input id="email" type="email" autoComplete="email" required placeholder="name@example.com" />
          </div>
          <div>
            <div className="flex items-center justify-between">
              <label htmlFor="password">Password</label>
              <Link
                href="#"
                className="text-sm font-medium text-gray-900 hover:underline dark:text-gray-50"
                prefetch={false}
              >
                Forgot password?
              </Link>
            </div>
            <input id="password" type="password" autoComplete="current-password" required />
          </div>
          <button type="submit" className="w-full">
            Sign in
          </button>
        </form>
      </div>
    </div>
        
    );
};

export default Login;

