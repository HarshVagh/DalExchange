import React from 'react';
import Header from '../../components/Header';

const Layout = ({ children }) => {
    const headerConfig = {
        search: false,
        requests: false,
        notifications: false,
        add: false,
        profile: false
      };

    return (
        <div className="container">
            <Header config={headerConfig}> </Header>
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
