import React from 'react';
import { Link } from 'react-router-dom';

const Header = () => {
    return (
        <header className="bg-black text-white py-4">
            <div className="container mx-auto flex justify-between items-center">
                <h1 className="text-lg font-bold">Dal Exchange</h1>
                <nav>
                    <Link to="/about" className="mx-2">About Us</Link>
                    <Link to="/register" className="mx-2">Register</Link>
                </nav>
            </div>
        </header>
    );
};

export default Header;
