import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../../components/Header';
import AuthenticationApi from '../../services/AuthenticationApi';

const VerifyEmail = () => {
    const [email, setEmail] = useState('');
    const [code, setCode] = useState('');
    const [message, setMessage] = useState('');
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
        try {
            await AuthenticationApi.verify({ email, code });
            setMessage('User verified successfully.');
            navigate('/products');
        } catch (error) {
            setMessage('Error verifying user.');
        }
    };

    return (
        <div className="flex flex-col min-h-screen">
            <Header config={headerConfig}></Header>
            <div className="flex flex-1 items-center justify-center px-4 sm:px-6 lg:px-8">
                <div className="w-full max-w-md space-y-6">
                    <div className="text-center">
                        <h2 className="text-3xl font-bold tracking-tight">Verify Email</h2>
                    </div>
                    <form className="space-y-4" onSubmit={handleSubmit}>
                        <div className="mb-6">
                            <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900">Email</label>
                            <input
                                type="email"
                                id="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                                required
                            />
                        </div>
                        <div className="mb-6">
                            <label htmlFor="code" className="block mb-2 text-sm font-medium text-gray-900">Verification Code</label>
                            <input
                                type="text"
                                id="code"
                                value={code}
                                onChange={(e) => setCode(e.target.value)}
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                                required
                            />
                        </div>
                        <button type="submit" className="w-full bg-black text-white py-2 rounded">
                            Verify Email
                        </button>
                    </form>
                    {message && <p>{message}</p>}
                </div>
            </div>
        </div>
    );
};

export default VerifyEmail;


