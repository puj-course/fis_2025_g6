import React from 'react';
import { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [userType, setUserType] = useState(localStorage.getItem('userType'));

    useEffect(() => {
        const stored = localStorage.getItem('userType');
        if (stored) setUserType(stored);
    }, []);

    const login = (type) => {
        localStorage.setItem('userType', type);
        setUserType(type);
    };

    const logout = () => {
        localStorage.clear();
        setUserType(null);
    };

    return (
        <AuthContext.Provider value={{ userType, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
