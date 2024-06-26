/*import React, { useState } from 'react';
import Header from './Header';
import Footer from './Footer';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const ForgotPassword = () => {
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/auth/forgot-password', null, {
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
        <div className="flex flex-col min-h-screen">
            <Header />
            <div className="flex flex-1 items-center justify-center px-4 sm:px-6 lg:px-8">
                <div className="w-full max-w-md space-y-6">
                    <div className="text-center">
                        <h2 className="text-3xl font-bold tracking-tight">Reset Password</h2>
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
                        <button type="submit" className="w-full bg-black text-white py-2 rounded">
                            Reset Password
                        </button>
                    </form>
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default ForgotPassword;
*/
