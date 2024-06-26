import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import '../authentication/global.css';

const Layout = ({ children }) => {
    const location = useLocation();

    return (
        <div className="container">
            <header>
                <div className="header-links">
                    <Link to="/about">About Us</Link>
                    {location.pathname === '/login' || location.pathname === '/'? (
                        <Link to="/signup">Register</Link>
                    ) : (
                        <Link to="/login">Login</Link>
                    )}
                </div>
                <h1>Dal Exchange</h1>
            </header>
            <main className="main-content">
                {children}
            </main>
            <footer>
                <p>&copy; 2024 Dal Exchange</p>
            </footer>
        </div>
    );
};

export default Layout;
