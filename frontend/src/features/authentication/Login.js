import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Header from '../../components/Header';
import AuthenticationApi from '../../services/AuthenticationApi';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const headerConfig = {
        search: false,
        requests: false,
        notifications: false,
        add: false,
        profile: false
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');
        setMessage('');
        try {
            await AuthenticationApi.login(username, password);
            navigate('/products');
        } catch (error) {
            setError('Error logging in. Please check your username and password.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex flex-col min-h-screen">
            <Header config={headerConfig} />
            <div className="flex flex-1 items-center justify-center px-4 sm:px-6 lg:px-8">
                <div className="w-full max-w-md space-y-6">
                    <div className="text-center">
                        <h2 className="text-3xl font-bold tracking-tight">Login</h2>
                    </div>
                    {message && <p className="text-green-500">{message}</p>}
                    {error && <p className="text-red-500">{error}</p>}
                    <form className="space-y-4" onSubmit={handleSubmit}>
                        <div>
                            <label htmlFor="username" className="block mb-2 text-sm font-medium text-gray-900">Username</label>
                            <input
                                type="text"
                                id="username"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                                required
                            />
                        </div>
                        <div>
                            <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900">Password</label>
                            <input
                                type="password"
                                id="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                                required
                            />
                        </div>
                        <div className="text-sm">
                            <Link to="/forgot-password" className="font-medium text-blue-600 hover:underline">
                                Forgot your password?
                            </Link>
                        </div>
                        <button type="submit" className="w-full bg-black text-white py-2 rounded" disabled={loading}>
                            {loading ? 'Logging in...' : 'Login'}
                        </button>
                    </form>
                    <p className="mt-2 text-sm text-gray-500 dark:text-gray-400">
                        Don't have an account?{" "}
                        <Link to="/signup" className="font-medium hover:underline">
                            Sign up
                        </Link>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default Login;
